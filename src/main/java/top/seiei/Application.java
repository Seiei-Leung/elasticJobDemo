package top.seiei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
// 使用 xml 文件配置 elasticJob
//@ImportResource({ "classpath:config/*.xml" })
public class Application {
    public static void main(String[] args) {
        // 使用 SpringApplication 中的 run 方法，它是一个静态的助手类方法，它用于执行一个 SpringApplication 实例
        SpringApplication.run(Application.class, args);
    }
}