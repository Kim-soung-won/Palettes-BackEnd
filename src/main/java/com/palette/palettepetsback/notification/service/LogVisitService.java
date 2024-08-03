package com.palette.palettepetsback.notification.service;

import com.palette.palettepetsback.member.entity.Member;
import com.palette.palettepetsback.member.repository.MemberRepository;
import com.palette.palettepetsback.notification.domain.MemberIssue;
import com.palette.palettepetsback.notification.repository.MemberIssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LogVisitService {

    private final MemberRepository memberRepository;
    private final MemberIssueRepository memberIssueRepository;

    @Transactional
    public void logVisit() {
        Member member = memberRepository.findByMemberId(1L).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));
        MemberIssue memberIssue = MemberIssue.builder()
                .receiver(member)
                .issueContent(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .issueCode(100)
                .build();
        memberIssueRepository.save(memberIssue);
    }
}
