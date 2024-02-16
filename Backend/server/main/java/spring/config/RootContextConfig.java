package spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.util.bean.ApplicationContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 현 프로젝트의 Global 자원 관련 Bean 설정 Class
 *
 * @author 하석형
 * @since 2023.08.14
 */
@Configuration
public class RootContextConfig {
    /**
     * Spring Application Context 객체 Bean 설정
     *
     * @author 하석형
     * @since 2023.08.24
     */
    @Bean(name = "applicationContextProvider")
    public ApplicationContextProvider getApplicationContextProvider () {
        ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        return applicationContextProvider;
    }

    /**
     * JSON Parser 라이브러리 Class Bean 설정
     *
     * @author 하석형
     * @since 2023.08.24
     */
    @Bean(name = "objectMapper")
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        //기본 날짜 포맷 비활성화
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //새로운 날짜 포맷 세팅
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return mapper;
    }
}