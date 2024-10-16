package com.lyj.proj.springbatch_10.job.HelloWorld;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    //위에꺼랑 아래꺼랑 매칭시키기!
    //아래 코드가 기본꼴이다. ==> 하나의 단위로서, 전부 순차적으로 실행이 되어야함. 트랜젝션과 비슷

    //Job : 여러가지의 Step들로 구성
    //Job은 여러개일 수 있고, Step도 Job에 속하나 개수제한이 없으므로 여러개일 수 있음
    //어떤 Job을 수행시키면 해당 Job에 해당되는 Step만 실행되도록 한다

    @Bean // 프로그램 시작되면 만들어지는 애들 => 일단 만들어서 객체 사용 공간에 올려놓는다
    public Job helloWorldJob() {
        return jobBuilderFactory.get("helloWorldJob")
                .incrementer(new RunIdIncrementer()) // 매번 다른 ID를 실행할 때 강제로 파라미터로 부여 (auto_increment 강제부여)
                .start(helloWorldStep1())
                .next(helloWorldStep2())
                .build();
    }

    @Bean
    @JobScope //생명주기 설정 : Step이므로, 상위 Job이 실행되었을 때 만들어지고 종료되었을 때 사라진다
    public Step helloWorldStep1() {
        return stepBuilderFactory
                .get("helloWorldStep1")
                .tasklet(helloWorldStep1Tasklet())
                .build();
    }

    @Bean
    @StepScope //생명주기 설정 : 해당하는 Step이 실행되었을 때 만들어지고 종료되었을 때 사라진다
    public Tasklet helloWorldStep1Tasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("헬로월드!!!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @JobScope //생명주기 설정 : Step이므로, 상위 Job이 실행되었을 때 만들어지고 종료되었을 때 사라진다
    public Step helloWorldStep2() {
        return stepBuilderFactory
                .get("helloWorldStep2")
                .tasklet(helloWorldStep2Tasklet())
                .build();
    }

    @Bean
    @StepScope //생명주기 설정 : 해당하는 Step이 실행되었을 때 만들어지고 종료되었을 때 사라진다
    public Tasklet helloWorldStep2Tasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("헬로월드2222222222");

            //테스트용으로 강제로 실패시켜보자
            if(true) { //이것 또한 무조건 실행이지만, 이게 없으면 분리가 안되어있어 return이 unreachable이 되므로 실행이 안됨.
                //unreachable인건 똑같긴 한데 어쨌든 분리되어있으니 막아주는것
                throw new Exception("실패 : 헬로월드 태스클릿 2 실패");
            }

            return RepeatStatus.FINISHED;
        };
    }

    //스프링 객체 생명주기
    // - 싱글톤 -> 스프링부트 앱이 꺼지기 전까지 살아있음
    // - 세션 -> 브라우저당 객체가 1개씩
    // - 리퀘스트 -> 요청당 객체가 1개씩 (해당 요청이 시작되고 끝날때 까지만 살아있음)
}

