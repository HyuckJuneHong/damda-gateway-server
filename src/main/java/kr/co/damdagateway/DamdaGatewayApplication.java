package kr.co.damdagateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DamdaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DamdaGatewayApplication.class, args);
    }

}
