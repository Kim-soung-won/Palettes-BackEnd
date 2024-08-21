package com.palette.palettepetsback.Article.articleView.controller;

import com.palette.palettepetsback.Article.Article;
import com.palette.palettepetsback.Article.articleView.DTO.response.ArticleResponseDTO;
import com.palette.palettepetsback.Article.articleView.DTO.PageableDTO;
import com.palette.palettepetsback.Article.articleView.service.ArticleKomoranService;
import com.palette.palettepetsback.Article.articleView.service.ArticleService;
import com.palette.palettepetsback.Article.articleWrite.response.Response;
import com.palette.palettepetsback.Article.articleWrite.service.ArticleWriteService;
import com.palette.palettepetsback.Article.redis.ViewArticleRedis;
import com.palette.palettepetsback.Article.redis.service.ArticleViewRedisService;
import com.palette.palettepetsback.config.SingleTon.ViewerLimit;
import com.palette.palettepetsback.config.jwt.AuthInfoDto;
import com.palette.palettepetsback.config.jwt.jwtAnnotation.JwtAuth;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleWriteService articleWriteService;
    private final RedisTemplate<String, String> redisTemplate;

    private final ArticleViewRedisService articleViewRedisService;

    private final ViewerLimit viewerLimit;

    @GetMapping("/articles/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public Response findArticle(@PathVariable final Long articleId,
                                HttpServletRequest request) {
        //조회수 증가 처리율 제한 추가

        String sessionId = request.getSession().getId();
        String key = "session_id_" + sessionId;
        if (!redisTemplate.hasKey(key)) {
            articleWriteService.updateCountViews(articleId);
            redisTemplate.opsForValue().set(key, sessionId, 600, TimeUnit.SECONDS);
        }
        ////단건 응답

        if (viewerLimit.viewLimit(request)) {
            articleWriteService.updateCountViews(articleId);
        }

        ViewArticleRedis articleRedis = articleViewRedisService.updateViewerCount(articleId);

        //단건 응답

        return Response.success(articleWriteService.findArticle(articleId));
    }

    @GetMapping("/article/list")
    public ResponseEntity<List<ArticleResponseDTO>> tagSearch(@ModelAttribute PageableDTO pd) {
        List<ArticleResponseDTO> articles = articleService.searchList(pd);

        return ResponseEntity.ok().body(articles);
    }
}
