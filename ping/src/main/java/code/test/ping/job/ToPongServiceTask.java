package code.test.ping.job;

import code.test.ping.mongo.PingProcess;
import code.test.ping.mongo.PingProcessRepository;
import code.test.ping.service.ToPongService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ToPongServiceTask {

    private static final String LOCK_FILE_PATH = "process.lock";

    private static final int MAX_PER_SECOND = 2;

    private static final Logger logger = LoggerFactory.getLogger(ToPongServiceTask.class);


    @Autowired
    private ToPongService toPongService;

    @Autowired
    private PingProcessRepository pingProcessRepository;

    // 每1秒执行一次任务
    @Scheduled(cron = "* * * * * *")
    public void ToPongServiceTask() throws IOException{

        setMDC();

        // 创建或打开文件
        File lockFile = new File(LOCK_FILE_PATH);
        RandomAccessFile raf = new RandomAccessFile(lockFile, "rw");
        FileChannel channel = raf.getChannel();

        // 尝试获取锁
        FileLock lock = channel.tryLock();

        //use file lock to control multipal jvm process limit 2qps
        if (lock!= null) {

            Long currentSecond = System.currentTimeMillis() / 1000;
            boolean limited = false;
            try {
                PingProcess pingProcess = getCountFromDB(currentSecond);
                Integer countInSecond = 0;
                String idParam = null;
                if (null != pingProcess){

                    countInSecond = pingProcess.getCount();
                    idParam = pingProcess.getId();
                }

                if (countInSecond < MAX_PER_SECOND) {

                    limited = true;

                    updateCountInDB(idParam, countInSecond + 1, currentSecond);
                } else {

                    //打印限流日志
                    logger.info("Be rate - limited, thread name is {}", Thread.currentThread().getName()) ;
                }

            }finally {
                lock.release();
                channel.close();
                raf.close();
            }

            if (limited == true){

                //请求流水号
                String requestId = UUID.randomUUID().toString();
                String content = "hello";
                toPongService.helloWorld(requestId, content);
            }


        }

    }

    @Transactional
    private void updateCountInDB(String id, Integer count, Long currentSecond) {

        if (null == id || "".equals(id)){

            pingProcessRepository.deleteAll();
            id = UUID.randomUUID().toString();
        }

        PingProcess pingProcess = new PingProcess();
        pingProcess.setId(id);
        pingProcess.setCount(count);
        pingProcess.setLastSecond(currentSecond);
        pingProcessRepository.save(pingProcess);
    }

    private PingProcess getCountFromDB(Long currentSecond) {


        List<PingProcess> pingProcesses = pingProcessRepository.findByLastSecond(currentSecond);
        if (null != pingProcesses && pingProcesses.size() > 0){

            PingProcess pingProcess = pingProcesses.get(0);
            return pingProcess;
        }

        return null;
    }

    private void setMDC(){

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String processName = runtimeMXBean.getName();
        MDC.put("processName-date", "ping-service-"+processName + "-" + java.time.LocalDate.now().toString());

    }
}