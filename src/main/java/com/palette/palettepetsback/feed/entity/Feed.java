package com.palette.palettepetsback.feed.entity;

import com.palette.palettepetsback.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "feed")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long feedId;
                                                            //이걸 삭제하면 매핑된 이미지도 같이 삭제
    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FeedImg> feedImageList = new ArrayList<>();

    @Column(name = "feed_content")
    private String feedContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @CreationTimestamp
    private LocalDateTime time;

}
