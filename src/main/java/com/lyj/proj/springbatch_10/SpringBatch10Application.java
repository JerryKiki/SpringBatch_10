package com.lyj.proj.springbatch_10;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing //너는 배치니까 관련 스키마 알아서 입혀! 라고 알려주는 것
public class SpringBatch10Application {

    public static void main(String[] args) {
        //Batch를 추가하면, 실행만 했을 뿐인데 테이블이 생긴다! => SQLYOG 가서 확인해보기
        SpringApplication.run(SpringBatch10Application.class, args);
    }

}
