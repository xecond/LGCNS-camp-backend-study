package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppConf1;
import config.AppConf2;
import config.AppCtx;
import ex01.ChangePasswordService;
import ex01.DuplicateMemberException;
import ex01.MemberInfoPrinter;
import ex01.MemberListPrinter;
import ex01.MemberRegisterService;
import ex01.RegisterRequest;
import ex01.VersionPrinter;

public class MainForSpring {
	
	// Assembler 클래스 대신 스프링 설정 클래스(AppCtx, Appconf1 등)를 이용하도록 수정
	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {
		
		// 스프링 설정 정보를 하나로 작성 시
		ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		// 스프링 설정 정보를 두 개로 쪼갰을 시
		//ctx = new AnnotationConfigApplicationContext(AppConf1.class, AppConf2.class);
		// @Import를 이용하여 한 설정 클래스에 함께 사용할 다른 설정 클래스를 지정했을 시
		//ctx = new AnnotationConfigApplicationContext(AppConf1.class);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("명령어를 입력하세요. ");
			String command = reader.readLine();
			if (command.startsWith("exit")) {
				System.out.println("종료합니다.");
                break;
			}
			if (command.startsWith("new")) {
				processNewCommand(command.split(" "));
				continue;
			}
			if (command.startsWith("change")) {
				processChangeCommand(command.split(" "));
				continue;
			}
			if (command.startsWith("list")) {
                processListCommand(command.split(" "));
                continue;
            }
			if (command.startsWith("info")) {
				processInfoCommand(command.split(" "));
				continue;
			}
			if (command.startsWith("version")) {
                processVersionCommand(command.split(" "));
                continue;
            }
			printHelp();
		}
	}

	private static void printHelp() {
    	System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("new email name password confirmPassword");
        System.out.println("change email currentPassword newPassword");
        System.out.println();
    }
    
    private static void processNewCommand(String[] args) {
    	
    	if (args.length != 5) {
    		printHelp();
    		return;
    	}
    	
    	//MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
    	MemberRegisterService regSvc = ctx.getBean(MemberRegisterService.class);
    	RegisterRequest reg = new RegisterRequest();
    	reg.setEmail(args[1]);
    	reg.setName(args[2]);
        reg.setPassword(args[3]);
        reg.setConfirmPassword(args[4]);
        
        if (!reg.isPasswordEqualToConfirmPassword()) {
        	System.out.println("패스워드와 패스워드 확인이 일치하지 않습니다.");
        	return;
        }
        
        try {
        	regSvc.regist(reg);
        	System.out.println("등록되었습니다.");
        } catch(DuplicateMemberException e) {
        	System.out.println("이미 존재하는 이메일입니다.");
        }
    }
    
    private static void processChangeCommand(String[] args) {
    	
    	if (args.length != 4) {
    		printHelp();
    		return;
    	}
    	
    	//ChangePasswordService pwdSvc = ctx.getBean("changePwdSvc", ChangePasswordService.class);
    	ChangePasswordService pwdSvc = ctx.getBean(ChangePasswordService.class);
    	try {
        	pwdSvc.changePassword(args[1], args[2], args[3]);
        	System.out.println("패스워드를 변경하였습니다.");
        } catch(RuntimeException e) {
        	System.out.println(e.getMessage());
        }
    }
    
    private static void processListCommand(String[] args) {
    	//MemberListPrinter memberListPrinter = ctx.getBean("memberListPrinter", MemberListPrinter.class);
    	MemberListPrinter memberListPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
    	memberListPrinter.printAll();
    }
    
    private static void processInfoCommand(String[] args) {
    	
    	if (args.length != 2) {
    		printHelp();
    		return;
    	}
    	
    	//MemberInfoPrinter memberInfoPrinter = ctx.getBean("memberInfoPrinter", MemberInfoPrinter.class);
    	MemberInfoPrinter memberInfoPrinter = ctx.getBean("infoPrinter", MemberInfoPrinter.class);
    	memberInfoPrinter.printMemberInfo(args[1]);
    }
    
    private static void processVersionCommand(String[] args) {
		VersionPrinter versionPrinter = ctx.getBean("versionPrinter", VersionPrinter.class);
		versionPrinter.print();
	}

}
