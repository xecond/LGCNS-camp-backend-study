package aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
public class ExeTimeAspect {

	// 공통 기능을 적용할 대상을 설정
	// ex04 패키지와 그 하위 패키지에 위치한 public 메서드를 Poincut으로 설정
	@Pointcut("execution(public * ex04..*(..))")
	public void publicTarget() { // 다른 클래스에서 사용할 수 있도록 public으로 변경 => 포인트컷 재사용 가능
		
	}
	
	// Around Advice
	// publicTarget() 메서드에 정의한 Pointcut에 공통 기능을 적용
	// measure: 공통 기능을 구현한 사용자 정의 메서드 - 팩토리얼 계산 수행 시간 계산
	// ProceedingJoinPoint: 프록시 대상 객체의 메서드를 호출할 때 사용
	@Around("publicTarget()")
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		try {
			Object result = joinPoint.proceed();
			return result;
		} finally {
			long end = System.nanoTime();
			
			System.out.printf("%s.%s(%s) 실행결과 = %d \n",
					joinPoint.getTarget().getClass().getSimpleName(),
					joinPoint.getSignature().getName(),
					Arrays.toString(joinPoint.getArgs()),
					(end - start));
		}
	}
}
