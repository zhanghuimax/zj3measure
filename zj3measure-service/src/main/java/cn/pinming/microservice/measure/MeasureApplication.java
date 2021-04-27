package cn.pinming.microservice.measure;

import cn.pinming.core.actuator.probe.ProbeServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author jin
 * @date 2020-04-06
 */
@SpringBootApplication(scanBasePackages =  {"cn.pinming.microservice.measure","cn.pinming.synshowdoc"})
@ServletComponentScan(basePackageClasses = ProbeServlet.class)
@MapperScan(basePackages = {"cn.pinming.microservice.measure.biz.mapper"})
@ImportResource({
        "classpath:/configuration/gateway-v2.xml"
})
public class MeasureApplication {

    public static void main(String[] args) {
        System.setProperty("dubbo.application.logger", "slf4j");
        SpringApplication.run(MeasureApplication.class, args);
    }

}
