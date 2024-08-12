package com.palette.palettepetsback.feed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "feed_img")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedImg {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_img_id")
    private Long feedImgId;

    @Column(name = "feed_img")
    private String feedImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;
}
