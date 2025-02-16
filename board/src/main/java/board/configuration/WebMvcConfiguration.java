package board.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/api/**")
			.allowedOrigins("http://localhost:5173")
			.allowedMethods("GET", "POST", "PUT", "DELETE");
		registry
			.addMapping("/loginProc")
			.exposedHeaders("token") // 커스텀 헤더의 값을 사용할 수 있도록 허용
			.allowedOrigins("http://localhost:5173")
			.allowedMethods("POST");
	}
}
