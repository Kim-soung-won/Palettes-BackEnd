package com.palette.palettepetsback.Article.redis.repository;

import com.palette.palettepetsback.Article.redis.ViewArticleRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleViewRedisRepository extends CrudRepository<ViewArticleRedis, Long> {
    ViewArticleRedis findViewByArticleId(Long articleId);
}
