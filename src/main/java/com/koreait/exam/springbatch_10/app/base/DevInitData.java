package com.koreait.exam.springbatch_10.app.base;

import com.koreait.exam.springbatch_10.app.entity.Member;
import com.koreait.exam.springbatch_10.app.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//자동으로 테이블 생성하고 자동으로 집어넣는 것을 해준다
//JPA는 자바 진영에서 ORM(Object-Relational Mapping) 기술 표준으로 사용되는 인터페이스의 모음이다. 그 말은 즉, 실제적으로 구현된것이 아니라 구현된 클래스와 매핑을 해주기 위해 사용되는 프레임워크이다.
//JPA를 구현한 대표적인 오픈소스로는 Hibernate가 있다.
@Configuration
@Profile("dev")
public class DevInitData {

    @Bean
    public CommandLineRunner initData(MemberService memberService){
        return args -> {
            String password = "{noop}1234";
            Member member1 = memberService.join("user1",password,"user1@test.com");
            Member member2 = memberService.join("user2",password,"user2@test.com");
            Member member3 = memberService.join("user3",password,"user3@test.com");
            Member member4 = memberService.join("user4",password,"user4@test.com");
        };
    }
}