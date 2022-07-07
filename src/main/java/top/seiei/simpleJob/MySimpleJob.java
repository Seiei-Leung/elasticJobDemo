package top.seiei.simpleJob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MySimpleJob implements SimpleJob {

    final static Logger logger = LoggerFactory.getLogger(MySimpleJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("------------------------- 计划执行 ------------------------------");
        switch (shardingContext.getShardingItem()) {
            case 0:
                logger.info("这里执行分片 1 的作业");
                break;
            case 1:
                logger.info("这里执行分片 2 的作业");
                break;
            case 2:
                logger.info("这里执行分片 3 的作业");
                break;
        }
    }
}
