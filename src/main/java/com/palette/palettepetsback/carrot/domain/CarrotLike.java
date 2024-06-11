package com.palette.palettepetsback.carrot.domain;

import com.palette.palettepetsback.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "carrot_like")
public class CarrotLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrot_like_id")
    private Long carrotLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrot_id")
    private Carrot carrotId;

    @CreationTimestamp
    private LocalDateTime time;
}
