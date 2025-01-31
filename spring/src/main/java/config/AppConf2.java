package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ex01.ChangePasswordService;
import ex01.MemberDAO;
import ex01.MemberInfoPrinter;
import ex01.MemberListPrinter;
import ex01.MemberPrinter;
import ex01.MemberRegisterService;
import ex01.VersionPrinter;

@Configuration
public class AppConf2 {
	
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private MemberPrinter memberPrinter;
	
	@Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService(memberDAO);
    }
    
    @Bean
    public ChangePasswordService changePwdSvc() {
        //return new ChangePasswordService(memberDAO);
    	return new ChangePasswordService();
    }
    
    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }
    
    @Bean
    public MemberListPrinter memberListPrinter() {
        return new MemberListPrinter(memberDAO, memberPrinter);
    }

    @Bean
    public MemberInfoPrinter memberInfoPrinter() {
    	MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
    	// 의존 주입 대상(MemberInfoPrinter 클래스)에서 @Autowired를 사용하여
    	// 스프링 설정 정보(AppConf2 클래스)의 @Bean 메서드에서의 의존 주입 코드는 불필요해짐
    	//infoPrinter.setMemberDAO(memberDAO);
        //infoPrinter.setMemberPrinter(memberPrinter);
        return infoPrinter;
    }
    
    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(6);
        versionPrinter.setMinorVersion(2);
        return versionPrinter;
    }
}
