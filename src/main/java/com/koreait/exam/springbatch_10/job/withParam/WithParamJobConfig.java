package com.koreait.exam.springbatch_10.job.withParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WithParamJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job WithParamJob(Step WithParamStep1) {
        return jobBuilderFactory.get("withParamJob")
                .incrementer(new RunIdIncrementer()) // 강제로 매번 다른 ID를 실행할 때 파라미터로 부여
                .start(WithParamStep1)
                .build();
    }

    @Bean
    @JobScope
    public Step WithParamStep1(Tasklet WithParamStep1Tasklet) {
        return stepBuilderFactory
                .get("WithParamStep1")
                .tasklet(WithParamStep1Tasklet)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet WithParamStep1Tasklet(
            @Value("#{jobParameters['name']}") String name,
            @Value("#{jobParameters['age']}") Long age
    ) {
        return (stepContribution, chunkContext) -> {
            log.debug("name : {} age : {}", name, age);
            System.out.println("withParam 111111111111111!!!");
            System.out.printf("%s, %d\n", name, age);
            return RepeatStatus.FINISHED;
        };
    }
}

//이제 배운 사용법들을 활용해 정산 프로그램을 만들어보자.
/*
그 전에... 정산이란? : 회계 용어로, 계산을 통하여 정리한다는 것을 말함

ex) 정산의 예시
지마켓 등의 유통플랫폼의 경우...
구매자가 상품을 구입하면... 우선 돈이 지마켓으로 간다
실제 판매자에게 세금처리, 수수료 등을 정리하고서, 판매자 별로 올바른 금액을 보내주기 위해 계산을 하는 과정

우리는 온라인 쇼핑몰을 가정하여 한번 정산을 만들어보자.
tip) 소재 관련 이야기
ebook : 실물이 없어 데이터 구축이 '비교적' 쉽다 => 상품(번호, 제목, 가격, 출판사, 작가, ...) => 품절이 될 리가 없다
의류 : 실물이 있어 데이터 구축이 어렵다
=> 상품(번호, 이름, 옵션(RED/95, RED/100, RED/105, ... BLACK/95...), 창고, 재고, 품절, 하자(반품), 교환, 배송, 송장번호, 발송, 수신날짜

==> 그러니 우리는 의류로 해보자
회원 상품 옵션 // 장바구니 주문 주문품목
*/