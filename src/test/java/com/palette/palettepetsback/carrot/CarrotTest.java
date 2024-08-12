package com.palette.palettepetsback.carrot;

import com.palette.palettepetsback.carrot.dto.CarrotRequestDTO;
import com.palette.palettepetsback.carrot.repository.CarrotImageRepository;
import com.palette.palettepetsback.carrot.repository.CarrotRepository;
import com.palette.palettepetsback.config.jwt.JWTUtil;
import com.palette.palettepetsback.member.dto.Role;
import com.palette.palettepetsback.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CarrotTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${jwt.secret}")
    private String secretKey;
    private JWTUtil jwtUtil;
    private Map<String, Object> claims = new HashMap<>();
    private String token;

    @BeforeEach
    void setUp() {
        // 테스트를 위한 초기 데이터 설정
        claims.put("memberId", 1L);
        claims.put("email", "test@naver.com");
        claims.put("role", Role.USER);
        claims.put("memberNickname", "soungwon");

        jwtUtil = new JWTUtil(secretKey);

        token = jwtUtil.generateToken("access", claims, 60 * 60 * 1000L);
    }


    @Test
    void carrotSave() throws Exception {
        CarrotRequestDTO dto = new CarrotRequestDTO(
                "테스트 제목", "테스트 내용", 7777, "거래"
        );


        MockMultipartFile file = new MockMultipartFile("files", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test data".getBytes());
        MockMultipartFile[] files = new MockMultipartFile[]{file};
        // 거래글 쓰기 테스트
        mockMvc.perform(multipart("/carrot/post")
                        .file(files[0])
                        .param("carrotTitle", dto.getCarrotTitle())
                        .param("carrotContent", dto.getCarrotContent())
                        .param("carrotPrice", String.valueOf(dto.getCarrotPrice()))
                        .param("carrotTag", dto.getCarrotTag())
                        .header("Authorization", "Bearer " + token)) // JWT 토큰 추가
                .andExpect(status().isOk()); // ok(200) 상태 코드 예상
    }

    @Test
    void carrotDelete() throws Exception {
        mockMvc.perform(delete("/carrot/delete/{id}", 1L)
                .header("Authorization", "Bearer " + token)) // JWT 토큰 추가
                .andExpect(status().isOk()); // ok(200) 상태 코드 예상
    }
}
