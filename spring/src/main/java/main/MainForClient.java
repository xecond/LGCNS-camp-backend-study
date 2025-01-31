package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import config.AppCtx;
import ex03.Client;
import ex03.Client2;


public class MainForClient {
	
	public static void main(String[] args) {
		
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		
		
		/* lab 1 */
//		Client c = ctx.getBean(Client.class);
//		c.send();
//		
//      ctx.close();

		
        /* lab 2 */
//		Client2 c = ctx.getBean(Client2.class);
//		c.send();
//		
//		System.out.println("before ctx.close()");
//		ctx.close();
//		System.out.println("after ctx.close()");
		
		
		/* lab 3 */
		Client2 c1 = ctx.getBean(Client2.class);
		Client2 c2 = ctx.getBean(Client2.class);
		
		// Scope가 싱글톤이면 true, 프로토타입이면 false
		// 프로토타입일 경우 close() 메서드를 호출하지 X
		System.out.println("c1 == c2 : " + (c1 == c2));
		
		ctx.close();
	}
}
