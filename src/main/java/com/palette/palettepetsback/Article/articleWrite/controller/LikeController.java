package com.palette.palettepetsback.Article.articleWrite.controller;

import com.amazonaws.Response;
import com.palette.palettepetsback.Article.ArticleLike;
import com.palette.palettepetsback.Article.articleWrite.dto.request.ArticleLikeRequestDto;
import com.palette.palettepetsback.Article.articleWrite.dto.response.ArticleLikeResponseDto;
import com.palette.palettepetsback.Article.articleWrite.service.ArticleLikeService;
import com.palette.palettepetsback.config.jwt.AuthInfoDto;
import com.palette.palettepetsback.config.jwt.JWTUtil;
import com.palette.palettepetsback.config.jwt.jwtAnnotation.JwtAuth;
import com.palette.palettepetsback.member.dto.UserDTO;
import com.palette.palettepetsback.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin //리액트에서 넘어올때 포트가 다르면 오류가 생기는걸 해결해줌
@Log4j2
@RequiredArgsConstructor
public class LikeController {
    private final ArticleLikeService articleLikeService;

    //좋아요 등록
    //좋아요 클릭시 countLove + 1
    @PostMapping("/like")
    public ResponseEntity<String>likeArticle(@RequestBody ArticleLikeRequestDto dto,
                                           @JwtAuth AuthInfoDto authInfoDto){

        if(authInfoDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        return ResponseEntity.ok().body(articleLikeService.likeArticle(dto.getArticleId(),authInfoDto.getMemberId()));
    }

    //좋아요 눌렀는지 안눌렀는지
    @PostMapping("/Post/isLike/{articleId}")
    public ResponseEntity<Map<String, Object>> isLikeArticle(@PathVariable final Long articleId
            , @JwtAuth final AuthInfoDto authInfoDto){

        if(authInfoDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(articleLikeService.isLikeArticle(articleId,authInfoDto.getMemberId()));
    }

    //좋아요 취소
    @DeleteMapping("/like/{articleId}")
    public ResponseEntity<Void> unlikeArticle(@PathVariable Long articleId,
                                              @JwtAuth AuthInfoDto authInfoDto) {
        if (authInfoDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        articleLikeService.unlikeArticle(articleId, authInfoDto.getMemberId());

        return ResponseEntity.noContent().build();
    }

    //좋아요 목록 조회
    @GetMapping("/like/{articleId}")
    public ResponseEntity<List<ArticleLikeResponseDto>> getArticleLikes(@PathVariable Long articleId){
        List<ArticleLike>articleLikes = articleLikeService.getArticleLikes(articleId);
        List<ArticleLikeResponseDto>articleLikeResponseDtos = articleLikes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleLikeResponseDtos);
    }
    //좋아요 개수 조회
    @GetMapping("/like/count/{articleId}")
    public ResponseEntity<Long> getArticleLikeCount(@PathVariable Long articleId) {
        long likeCount = articleLikeService.getArticleLikeCount(articleId);
        return ResponseEntity.ok(likeCount);
    }


    private ArticleLikeResponseDto mapToDto(ArticleLike articleLike) {
        return ArticleLikeResponseDto.builder()
                .id((long) articleLike.getId().hashCode())
                .articleId(articleLike.getArticle().getArticleId())
                .memberId(articleLike.getMember().getMemberId())
                .createdAt(articleLike.getCreatedAt())
                .build();
    }



}





