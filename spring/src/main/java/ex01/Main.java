package ex01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String args[]) {
		/* Greeter 클래스의 인스턴스를 직접 생성해서 사용 */
		{
			Greeter greeter = new Greeter();
			greeter.setFormat("Hello, %s!!!");
			String message = greeter.greet("Spring");
			System.out.println(message);
		}
		/* 스프링 컨테이너를 사용하도록 수정 */
		{
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
			
			Greeter greeter = ctx.getBean("greeter", Greeter.class);
			greeter.setFormat("안녕, %s!!!");
			String message = greeter.greet("Spring");
			System.out.println(message);
		}
	}
}
