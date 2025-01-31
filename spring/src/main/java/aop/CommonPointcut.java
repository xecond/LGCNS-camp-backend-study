package aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {
	
	// 여러 Aspect에서 공통으로 사용하는 Pointcut
	@Pointcut("execution(public * ex04..*(..))")
	public void commonTarget() {
		
	}
}
