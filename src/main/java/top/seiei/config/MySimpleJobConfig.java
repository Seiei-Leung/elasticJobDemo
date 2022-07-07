package top.seiei.config;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.seiei.simpleJob.MySimpleJob;
import top.seiei.simpleJob.MySimpleJonListener;

import javax.annotation.Resource;
import javax.sql.DataSource;

/*
  JobScheduler(启动作业):
    ZookeeperRegistryCenter(Zookeeper 配置)
    LiteJobConfiguration(Lite作业根配置) -> JobCoreConfiguration(作业核心配置) -> SimpleJobConfiguration(SIMPLE类型配置) -> SimpleJob(作业开发)
*/
@Configuration
public class MySimpleJobConfig {

    // ZookeeperRegistryCenter(Zookeeper 配置)
    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    // 数据源，从 application
    @Resource
    private DataSource elasticJobDataSource;

    // JobScheduler 配置
    @Bean
    public JobScheduler mySimpleJobScheduler(
            @Value("${simpleJob.jobName}") final String jobName,
            @Value("${simpleJob.cron}") final String cron,
            @Value("${simpleJob.shardingTotalCount}") final int shardingTotalCount,
            @Value("${simpleJob.shardingItemParameters}") final String shardingItemParameters,
            @Value("${simpleJob.failover}") final boolean failover,
            @Value("${simpleJob.maxTimeDiffSeconds}") final int maxTimeDiffSeconds
    ) {

        // JobCoreConfiguration(作业核心配置)
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                                                .shardingItemParameters(shardingItemParameters)
                                                .failover(failover)
                                                .build();

        // SimpleJobConfiguration(SIMPLE类型配置)
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MySimpleJob.class.getCanonicalName());

        // LiteJobConfiguration(Lite作业根配置)
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig)
                                                    .maxTimeDiffSeconds(maxTimeDiffSeconds)
                                                    .overwrite(false)
                                                    .build();

        // JobScheduler 配置
        JobScheduler jobScheduler = new JobScheduler(
                registryCenter, // Zookeeper 配置
                simpleJobRootConfig, // LiteJobConfiguration(Lite作业根配置)
                new JobEventRdbConfiguration(elasticJobDataSource), // 日志数据库事件溯源配置
                new MySimpleJonListener() // 作业事件监听
        );
        jobScheduler.init();
        return jobScheduler;
    }
}
