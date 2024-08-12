package com.palette.palettepetsback.feed.service;

import com.palette.palettepetsback.config.SingleTon.Singleton;
import com.palette.palettepetsback.config.Storage.NCPObjectStorageService;
import com.palette.palettepetsback.feed.dto.FeedListResponse;
import com.palette.palettepetsback.feed.dto.FeedResponse;
import com.palette.palettepetsback.feed.repository.FeedImgRepository;
import com.palette.palettepetsback.feed.repository.FeedRepository;
import com.palette.palettepetsback.member.entity.Member;
import com.palette.palettepetsback.member.repository.MemberRepository;
import com.palette.palettepetsback.feed.entity.Feed;
import com.palette.palettepetsback.feed.entity.FeedImg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final NCPObjectStorageService objectStorageService;
    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;
    private final FeedImgRepository feedImgRepository;


    public String fileUpload(MultipartFile file, String dirPath) {
        return objectStorageService.uploadFile(Singleton.S3_BUCKET_NAME, dirPath, file);
    }

    @Transactional
    public Feed saveFeed(String feedContent, Long memberId, MultipartFile[] files) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            Feed feed = Feed.builder()
                    .feedContent(feedContent)
                    .memberId(member.get())
                    .build();

            // 파일 업로드 및 FeedImg 저장
            for (MultipartFile file : files) {
                String imgUrl = fileUpload(file, "feed/img");
                saveFeedImg(imgUrl, feed);
            }

            return feedRepository.save(feed);
        } else {
            throw new IllegalArgumentException("Member not found with id: " + memberId);
        }
    }

    @Transactional
    public FeedImg saveFeedImg(String imgUrl, Feed feed) {
        FeedImg feedImg = FeedImg.builder()
                .feedImg(imgUrl)
                .feed(feed)
                .build();
        return feedImgRepository.save(feedImg);
    }



    //피드리스트
    @Transactional(readOnly = true)
    public List<FeedListResponse> getFeedList(String nickname) {
        Optional<Member> member = memberRepository.findByMemberNickname(nickname);
        if (member.isPresent()) {

            List<Feed> feedList = feedRepository.findByMemberId(member.get());

            List<FeedListResponse> list = new ArrayList<>();

            for (Feed f : feedList) {
                String img = f.getFeedImageList().get(0).getFeedImg(); //한 피드에 여러장이면 맨처음 올린 사진만보여줌
                list.add(new FeedListResponse(f.getFeedId(),img));
            }

            return list;
        } else {
            // 멤버를 찾을 수 없는 경우 빈 리스트를 반환하거나 예외를 던질 수 있습니다.
            return List.of();
        }
    }

    //피드상세
    @Transactional(readOnly = true)
    public FeedResponse getFeedDetail(Long feedId , Long memberId) {
        Feed feed = feedRepository.findByFeedId(feedId);

        if (feed!=null) {
            FeedResponse feedResponses = new FeedResponse();

            feedResponses.setText(feed.getFeedContent());
            List<String> imgs = new ArrayList<>();
            for (FeedImg img : feed.getFeedImageList()) {
                imgs.add(img.getFeedImg());
            }
            feedResponses.setImg(imgs);
            if(feed.getMemberId().getMemberId().equals(memberId)){ //피드를 적은 사람과 피드를 열어본 사람이 같은 사람인지 판별
                feedResponses.setWriter(true);
            }
            return feedResponses;
        }else{
            return null;
        }
    }
    //피드삭제
    public void deleteFeed(Long feedId, Long memberId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 피드입니다."));
        if(feed.getMemberId().getMemberId().equals(memberId)){ //피드를 적은 사람과 피드를 열어본 사람이 같은 사람인지 판별
            feedRepository.delete(feed);
        }else{
            throw new IllegalArgumentException("피드 작성자가 아닌 사용자가 피드를 삭제하려고 합니다.");
        }

    }

}
