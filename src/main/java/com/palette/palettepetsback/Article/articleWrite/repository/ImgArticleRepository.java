package com.palette.palettepetsback.Article.articleWrite.repository;

import com.palette.palettepetsback.Article.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImgArticleRepository extends JpaRepository<ArticleImage,Long> {

    Optional<ArticleImage> findByImgUrl(String imgUrl);
}
