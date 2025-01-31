package aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class CacheAspect {

	// 키: 입력 숫자, 값: 팩토리얼 결과
	private Map<Long, Object> cache = new HashMap<>();
	
	/*
	@Pointcut("execution(public * ex04..*(*))")
	public void cacheTarget() {
		
	}
	*/
	
	// execute: 공통 기능을 구현한 사용자 정의 메서드 - 팩토리얼 계산 결과 캐싱
	//@Around("cacheTarget()") 						 // 자기 Pointcut 만들어 사용
	//@Around("aop.ExeTimeAspect.publicTarget()")    // 다른 Aspect에서 사용한 Pointcut 재사용
	@Around("aop.CommonPointcut.commonTarget()")     // 공통 Pointcut 재사용
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		long num = (Long)joinPoint.getArgs()[0];
		if (cache.containsKey(num)) {
			System.out.printf("CacheAspect: cache에서 가져옴 [%d]\n", num);
			return cache.get(num);
		}
		
		Object result = joinPoint.proceed();
		cache.put(num, result);
		System.out.printf("CacheAspect: cache에 추가 [%d]\n", num);
		return result;
	}
}
