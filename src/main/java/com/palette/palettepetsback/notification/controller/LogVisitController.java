package com.palette.palettepetsback.notification.controller;

import com.palette.palettepetsback.member.entity.Member;
import com.palette.palettepetsback.member.repository.MemberRepository;
import com.palette.palettepetsback.member.service.MemberService;
import com.palette.palettepetsback.notification.domain.MemberIssue;
import com.palette.palettepetsback.notification.repository.MemberIssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class LogVisitController {

    private final MemberRepository memberRepository;
    private final MemberIssueRepository memberIssueRepository;

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        Member member = memberRepository.findByMemberId(1L).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));
        MemberIssue memberIssue = MemberIssue.builder()
                .receiver(member)
                .issueContent(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .issueCode(100)
                .build();
        memberIssueRepository.save(memberIssue);
        return ResponseEntity.ok().body("hello");
    }
}
