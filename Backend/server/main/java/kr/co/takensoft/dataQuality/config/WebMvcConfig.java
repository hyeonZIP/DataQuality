package kr.co.takensoft.dataQuality.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Web MVC 구성 설정
 *
 * @author 김준식
 * @since 2024.01.16
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * SessionInterceptor 세션 관리를 담당하는 인터셉터 등록
     *
     * @author 김준식
     * @since 2024.01.16
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor());
    }
}
