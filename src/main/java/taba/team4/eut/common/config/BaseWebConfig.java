package taba.team4.eut.common.config;

import taba.team4.eut.common.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public abstract class BaseWebConfig implements WebMvcConfigurer {


    /**
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //-------------------------------------------------------------------------------------
        // 1. Sorting에 대한 설정 수정
        //-------------------------------------------------------------------------------------
        SortHandlerMethodArgumentResolver sortArgumentResolver = new SortHandlerMethodArgumentResolver();
        // Default는 sort => sortBy 로 수정
        sortArgumentResolver.setSortParameter("sortBy");
        //  Default는 ',' => '-' 로 수정
        sortArgumentResolver.setPropertyDelimiter("-");

        //-------------------------------------------------------------------------------------
        // 2. Paging에 대한 설정 수정
        // ==> PageEntity 개체와 동일하게 사용
        //-------------------------------------------------------------------------------------
        PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver(sortArgumentResolver);
        // page 기본값을 1로 설정 (default = 0)
        pageableArgumentResolver.setOneIndexedParameters(true);
        // 최대 요청 가능한 size를 설정
        pageableArgumentResolver.setMaxPageSize(500);
        // 페이지 요청이 없는 경우 기본적으로 요청되는 페이징 정보를 설정. Default는 page 0, size 20 입니다.
        pageableArgumentResolver.setFallbackPageable(PageRequest.of(0,100));
        // page 파라미터명 수정
        pageableArgumentResolver.setPageParameterName("pageNo");
        // size 파라미터명을 수정
        pageableArgumentResolver.setSizeParameterName("pagePerCnt");

        argumentResolvers.add(pageableArgumentResolver);
    }

    /**
     * 크로스 도메인 허용
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE");;
    }

//    @Value("${spring.profiles.active}")
//    private String activeProfile;

    @Autowired
    private ConfigurableEnvironment env;

    @Bean
    public PropertyUtil profileUtil() {
       return new PropertyUtil(env);
    }

}
