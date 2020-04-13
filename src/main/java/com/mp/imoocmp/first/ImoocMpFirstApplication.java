package com.mp.imoocmp.first;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mp.imoocmp.first.dao")
public class ImoocMpFirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocMpFirstApplication.class, args);
    }

}
