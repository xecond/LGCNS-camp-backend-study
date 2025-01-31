package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ex01.ChangePasswordService;
import ex01.MemberDAO;
import ex01.MemberInfoPrinter;
import ex01.MemberListPrinter;
import ex01.MemberPrinter;
import ex01.MemberRegisterService;
import ex01.MemberSummaryPrinter;
import ex01.VersionPrinter;
import ex03.Client;
import ex03.Client2;

@Configuration
@ComponentScan(basePackages = { "ex01", "ex02" })
public class AppCtx {
	
	/* 컴포넌트 스캔을 통해 자동으로 등록되는 클래스들 관련 코드 제거 */
	
//	@Bean
//	public MemberDAO memberDAO() {
//		return new MemberDAO();
//	}
//	
//	@Bean
//    public MemberRegisterService memberRegSvc() {
//        return new MemberRegisterService(memberDAO());
//    }
//    
//    @Bean
//    public ChangePasswordService changePwdSvc() {
//        //return new ChangePasswordService(memberDAO());
//        return new ChangePasswordService();
//    }
    
    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }
    
    @Bean
    @Qualifier("summaryPrinter")
    public MemberSummaryPrinter memberPrinter2() {
    	return new MemberSummaryPrinter();
    }
    
//    @Bean
//    public MemberListPrinter memberListPrinter() {
//        //return new MemberListPrinter(memberDAO(), memberPrinter());
//    	return new MemberListPrinter();
//    }
//
//    @Bean
//    public MemberInfoPrinter memberInfoPrinter() {
//    	MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
//    	//infoPrinter.setMemberDAO(memberDAO());
//        infoPrinter.setMemberPrinter(memberPrinter2());
//        return infoPrinter;
//    }
    
    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(6);
        versionPrinter.setMinorVersion(2);
        return versionPrinter;
    }
    
    /* --------- */
    
    //@Bean
    public Client client() {
    	Client client = new Client();
    	client.setHost("www.test.com");
    	return client;
    }
    
    @Bean(initMethod = "connect", destroyMethod = "close")
    //@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Client2 client2() {
    	Client2 client = new Client2();
    	client.setHost("www.test.com");
    	return client;
    }
}
