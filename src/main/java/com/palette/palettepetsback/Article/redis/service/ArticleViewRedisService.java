package com.palette.palettepetsback.Article.redis.service;

import com.palette.palettepetsback.Article.redis.ViewArticleRedis;
import com.palette.palettepetsback.Article.redis.repository.ArticleViewRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ArticleViewRedisService {

    private final ArticleViewRedisRepository articleViewRedisRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 조회수 단기 캐싱
     */
    public ViewArticleRedis updateViewerCount(Long articleId){
        ViewArticleRedis article = articleViewRedisRepository.findViewById(articleId);
        if(article==null) {
            return articleViewRedisRepository.save(
                    ViewArticleRedis.builder()
                            .articleId(articleId)
                            .count(1)
                            .build()
            );
        }else {
            Long count = redisTemplate.opsForValue().increment(articleId.toString(), 1);
        }
        return article;
    }
}
