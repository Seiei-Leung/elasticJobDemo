package top.seiei.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
  JobScheduler(启动作业):
    ZookeeperRegistryCenter(Zookeeper 配置)
    LiteJobConfiguration(Lite作业根配置) -> JobCoreConfiguration(作业核心配置) -> SimpleJobConfiguration(SIMPLE类型配置) -> SimpleJob(作业开发)
*/
// ZookeeperRegistryCenter 配置
@Configuration
// 只在 application.properties 和 application.yml 读取相应键值对
// 只有在上述两个配置文件中设置 zookepper.addres 属性，才会配置该设置类
// 多个 zookepper 地址使用逗号间隔
@ConditionalOnExpression("'${zookeeper.address}'.length() > 0")
public class ZookeeperRegistryCenterConfig {

    /**
     * 把注册中心 ZookeeperRegistryCenter 注入到 spring 容器
     * @param address
     * @param namespace
     * @param connnectTimeout
     * @param sessionTimeout
     * @param maxRetries
     * @return
     */
    @Bean
    public ZookeeperRegistryCenter registryCenter(
            @Value("${zookeeper.address}") final String address,
            @Value("${zookeeper.namespace}") final String namespace,
            @Value("${zookeeper.connnectTimeout}") final int connnectTimeout,
            @Value("${zookeeper.sessionTimeout}") final int sessionTimeout,
            @Value("${zookeeper.maxRetries}") final int maxRetries)
    {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(address, namespace);
        zookeeperConfiguration.setMaxRetries(maxRetries);
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(connnectTimeout);
        zookeeperConfiguration.setSessionTimeoutMilliseconds(sessionTimeout);
        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        zookeeperRegistryCenter.init();
        return zookeeperRegistryCenter;
    }
}
