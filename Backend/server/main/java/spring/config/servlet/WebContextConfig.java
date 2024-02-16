package spring.config.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.util.bean.ApplicationContextProvider;
import common.util.bean.BeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * Servlet 관련 설정 Class
 *
 * @author 하석형
 * @since 2023.08.14
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.co.takensoft"})
public class WebContextConfig implements WebMvcConfigurer {

    /**
     * 1. 특정 URL(Path)에 대한 HTTP Request 처리를 DispatcherServlet이 담당하지 않도록 만들어 주는 설정 (html, js, css, file 등..)
     * 2. Client가 접근하지 못하는 WEB-INF 폴더안에 위치해 있는 정적 자원에 Clien가 접근할 수 있도록 만들어 주는 설정
     *
     * @author 하석형
     * @since 2023.08.14
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/views/**").addResourceLocations("/views/");
    }

    /**
     * HTTP Request Param Converter
     * ex) JSON text => JAVA Object, text => String, Binary text => byte[] 등... 여러 데이터 타입에 대한 설정 가능
     * 현재 설정에서는 Spring의 기존 JSON Converter(MappingJackson2HttpMessageConverter)를 새로 덮어씌워 움
     *  => 새로 덮어씌우는 MappingJackson2HttpMessageConverter에 프로젝트에서 설정한 ObjectMapper를 생성자로 넣어줌
     * @author 하석형
     * @since 2023.08.21
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter((ObjectMapper) BeanUtil.getBean("objectMapper")));
    }

    /**
     * Custom Bean Class ViewResolver를 제공해 주기 위한 Bean (파일 다운로드, JSON 등..)
     *
     * @author 하석형
     * @since 2023.08.14
     * @return BeanNameViewResolver
     */
    @Bean(name="beanView")
    public BeanNameViewResolver getBeanView () {
        BeanNameViewResolver beanView = new BeanNameViewResolver();
        beanView.setOrder(0);
        return beanView;
    }

    /**
     * JSP ViewResolver를 제공해 주기 위한 Bean
     *
     * @author 하석형
     * @since 2023.08.14
     * @return InternalResourceViewResolver
     */
    @Bean(name="jspView")
    public ViewResolver getJspView() {
        InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
        jspViewResolver.setPrefix("/WEB-INF");
        jspViewResolver.setSuffix(".jsp");
        jspViewResolver.setOrder(1);
        return jspViewResolver;
    }

    /**
     * JSON ViewResolver를 제공해 주기 위한 Bean
     *
     * @author 하석형
     * @since 2023.08.21
     * @return MappingJackson2JsonView
     */
    @Bean(name="jsonView")
    public MappingJackson2JsonView getJsonView () {
        ObjectMapper objectMapper = (ObjectMapper) BeanUtil.getBean("objectMapper");
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView(objectMapper);
        jsonView.setExtractValueFromSingleKeyModel(true);
        return jsonView;
    }

    /**
     * Spring Application Context 객체 Bean 설정
     *
     * @author 하석형
     * @since 2023.08.14
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
     * @since 2023.08.21
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