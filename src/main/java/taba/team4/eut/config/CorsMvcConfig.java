package taba.team4.eut.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**") // 모든 엔드포인트에 대해서
                .allowedOrigins("*") // 허용할 오리진
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // 허용할 HTTP 메서드 지정
    }

}
