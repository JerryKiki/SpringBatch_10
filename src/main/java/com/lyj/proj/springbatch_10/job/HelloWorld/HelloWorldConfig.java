package com.lyj.proj.springbatch_10.job.HelloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HelloWorldConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    //위에꺼랑 아래꺼랑 매칭시키기!
    //아래 코드가 기본꼴이다. ==> 하나의 단위로서, 전부 순차적으로 실행이 되어야함. 트랜젝션과 비슷
    //Job : 여러가지의 Step들로 구성
    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory.get("HelloWorldJob").start(helloWorldStep1()).build();
    }

    @Bean
    public Step helloWorldStep1() {
        return stepBuilderFactory.get("HelloWorldStep1").tasklet(helloWorldTasklet()).build();
    }

    @Bean
    public Tasklet helloWorldTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("HelloWorld!!!");
            return RepeatStatus.FINISHED;
        };
    }


}
