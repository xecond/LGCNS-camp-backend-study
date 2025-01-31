package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import aop.CacheAspect;
import aop.ExeTimeAspect;
import ex04.Calculator;
import ex04.IterCalculator;
import ex04.RecCalculator;

@Configuration
@EnableAspectJAutoProxy
public class AppCtxAspect {

	@Bean
	public Calculator iterCalculator() {
		return new IterCalculator();
	}
	
	@Bean
	public Calculator recCalculator() {
		return new RecCalculator();
	}
	
	@Bean
	public ExeTimeAspect exeTimeAspect() {
		return new ExeTimeAspect();
	}
	
	@Bean
	public CacheAspect cacheAspect() {
		return new CacheAspect();
	}
}
