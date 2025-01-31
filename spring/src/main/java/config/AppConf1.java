package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ex01.MemberDAO;
import ex01.MemberPrinter;

@Configuration
@Import(AppConf2.class)
public class AppConf1 {
	@Bean
	public MemberDAO memberDAO() {
		return new MemberDAO();
	}
	
	@Bean
	public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

}
