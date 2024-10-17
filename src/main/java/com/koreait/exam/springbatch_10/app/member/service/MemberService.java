package com.koreait.exam.springbatch_10.app.member.service;

import com.koreait.exam.springbatch_10.app.member.entity.Member;
import com.koreait.exam.springbatch_10.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //메서드 체이닝으로 빌더 패턴을 만들어서 복잡한 객체의 생성을 단순화
    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email).build();

        memberRepository.save(member);

        return member;
    }
}
