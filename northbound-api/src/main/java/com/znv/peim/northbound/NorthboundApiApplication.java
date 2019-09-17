package com.znv.peim.northbound;

import com.znv.peim.northbound.common.util.CommonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NorthboundApiApplication {

    public static void main(String[] args) {
        System.setProperty("boot.home", CommonUtils.localLogPath());
        SpringApplication.run(NorthboundApiApplication.class, args);
    }

}
