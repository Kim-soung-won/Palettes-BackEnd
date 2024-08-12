package com.palette.palettepetsback.carrot.domain;

import com.palette.palettepetsback.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "carrot")
@NoArgsConstructor
@Getter
public class Carrot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrot_id")
    private Long carrotId;

    @Column(name = "carrot_title")
    private String carrotTitle;

    @Column(columnDefinition = "TEXT", name = "carrot_content")
    private String carrotContent;

    @Column(name = "carrot_price")
    private Integer carrotPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "carrot_created_at")
    private LocalDateTime carrotCreatedAt;

    @Column(name = "carrot_tag")
    private String carrotTag;


    @Column(name = "carrot_state", columnDefinition = "TINYINT")
    private int carrotState;

    @Column(name = "carrot_like")
    private Integer carrotLike;

    @Column(name = "carrot_view")
    private Integer carrotView;

    @Column(name = "carrot_image")
    private String carrotImage;


    //저장되기 전 실행 메서드(default 값 지정)
    @PrePersist
    public void prePersist() {
        this.carrotCreatedAt = LocalDateTime.now();
        this.carrotLike = 0;
        this.carrotView = 0;
    }

    //수정 되기 전 실행 메서드
    @PreUpdate
    public void preUpdate() {
        this.carrotCreatedAt = LocalDateTime.now();
    }

    public void like(int sum){
        this.carrotLike=this.carrotLike+sum;
    }

}
