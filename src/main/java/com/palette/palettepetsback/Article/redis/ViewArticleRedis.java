package com.palette.palettepetsback.Article.redis;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash(value = "ArticleViewRedis")
@Builder
@ToString
public class ViewArticleRedis {
    @Id
    @Indexed
    Long articleId;

    int count;
}
