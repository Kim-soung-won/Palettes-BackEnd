package com.palette.palettepetsback.Article.articleWrite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palette.palettepetsback.Article.articleWrite.dto.request.ArticleLikeRequestDto;
import com.palette.palettepetsback.Article.articleWrite.service.ArticleLikeService;
import com.palette.palettepetsback.config.jwt.JWTUtil;
import com.palette.palettepetsback.member.dto.Role;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // 전체 Context 로드
@AutoConfigureMockMvc // MockMvc 자동 구성
@Transactional // 테스트 종료 후 롤백
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc; // Spring MVC의 동작을 모방하기 위해 사용합니다.

    @MockBean
    private ArticleLikeService articleLikeService; // 실제 서비스 대신 mock 객체를 주입받습니다.

    @Autowired
    private ObjectMapper objectMapper; // 객체를 JSON으로 변환하기 위해 사용합니다.

    private ArticleLikeRequestDto articleLikeRequestDto;
    @Value("${jwt.secret}")
    private String secretKey;
    private JWTUtil jwtUtil;
    private Map<String, Object> claims = new HashMap<>();
    private String token;



    @BeforeEach
    void setUp() {
        // 테스트를 위한 초기 데이터 설정
        articleLikeRequestDto = new ArticleLikeRequestDto(1L);
        claims.put("memberId", 1L);
        claims.put("email", "test@naver.com");
        claims.put("role", Role.USER);
        claims.put("memberNickname", "soungwon");

        jwtUtil = new JWTUtil(secretKey);

        token = jwtUtil.generateToken("access", claims, 60 * 60 * 1000L);
    }

    @Test
    void testLikeArticle() throws Exception {
        // 게시글 좋아요 테스트
        mockMvc.perform(post("/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleLikeRequestDto))
                        .header("Authorization", "Bearer " + token)) // JWT 토큰 추가
                .andExpect(status().isOk()); // ok(200) 상태 코드 예상
    }

    @Test
    void testUnlikeArticle() throws Exception {
        // 게시글 좋아요 취소 테스트
        mockMvc.perform(delete("/like/{articleId}", 1L)
                        .header("Authorization", "Bearer " + token)) // JWT 토큰 추가
                .andExpect(status().isNoContent()); // 내용 없음(204) 상태 코드 예상
    }

    @Test
    void testGetArticleLikes() throws Exception {
        Mockito.when(articleLikeService.getArticleLikes(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/like/{articleId}", 1L))
                .andExpect(status().isOk()) // 정상(200) 상태 코드 예상
                .andExpect(jsonPath("$").isArray()); // JSON 배열 반환 예상
    }

    @Test
    void testGetArticleLikeCount() throws Exception {
        Mockito.when(articleLikeService.getArticleLikeCount(1L)).thenReturn(0L);

        mockMvc.perform(get("/like/count/{articleId}", 1L))
                .andExpect(status().isOk()) // 정상(200) 상태 코드 예상
                .andExpect(content().string("0")); // "0" 문자열 반환 예상
    }
}
