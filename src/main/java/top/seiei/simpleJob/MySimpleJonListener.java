package top.seiei.simpleJob;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySimpleJonListener implements ElasticJobListener {

    final static Logger logger = LoggerFactory.getLogger(MySimpleJonListener.class);

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        logger.info("--------------------- 开始任务 ----------------------");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        logger.info("--------------------- 结束任务 ----------------------");
    }
}
