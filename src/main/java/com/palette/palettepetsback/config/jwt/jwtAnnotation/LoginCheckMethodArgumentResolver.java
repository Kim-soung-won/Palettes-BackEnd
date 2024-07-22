package com.palette.palettepetsback.config.jwt.jwtAnnotation;

import com.palette.palettepetsback.config.jwt.AuthInfoDto;
import com.palette.palettepetsback.config.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCheckMethodArgumentResolver implements HandlerMethodArgumentResolver {
// HandlerMethodArgumentResolver : 컨트롤러 메서드의 매개변수 처리 방법을 정의한다.
// HTTP 헤더, 세션, 쿼리 파라미터를 기반으로 매개변수를 설정할 수 있다.

    @Override
    //JwtAuth 어노테이션이 존재하는지 확인한다.
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtAuth.class);
    }

    @Override
    // JWT에서 인증정보를 가져오고 이를 AuthInfoDto로 변환하여 반환한다.
    public AuthInfoDto resolveArgument(MethodParameter parameter,
                                       ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest,
                                       WebDataBinderFactory binderFactory) throws Exception {
        return JWTUtil.getMemberInfo();
    }
}
