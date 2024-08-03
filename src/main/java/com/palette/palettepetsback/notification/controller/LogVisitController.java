package com.palette.palettepetsback.notification.controller;

import com.palette.palettepetsback.member.entity.Member;
import com.palette.palettepetsback.member.repository.MemberRepository;
import com.palette.palettepetsback.member.service.MemberService;
import com.palette.palettepetsback.notification.domain.MemberIssue;
import com.palette.palettepetsback.notification.repository.MemberIssueRepository;
import com.palette.palettepetsback.notification.service.LogVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class LogVisitController {

    private final LogVisitService logVisitService;

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        logVisitService.logVisit();
        return ResponseEntity.ok().body("hello");
    }
}
