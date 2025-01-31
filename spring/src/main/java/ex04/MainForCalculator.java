package ex04;

public class MainForCalculator {

	public static void main(String[] args) {
		
		final long num = 20L;
		
		/* 실행 클래스에서 메서드 실행 전후에 시각을 구해 실행 시간을 계산하는 방식 */
//		IterCalculator iter = new IterCalculator();
//		long start1 = System.nanoTime();
//		iter.factorial(num);
//		long end1 = System.nanoTime();
//		System.out.printf("IterCalculator.factorial(%d) 실행시간 = %d\n", num, (end1-start1));
//		
//		RecCalculator rec = new RecCalculator();
//		long start2 = System.nanoTime();
//		rec.factorial(num);
//		long end2 = System.nanoTime();
//		System.out.printf("RecCalculator.factorial(%d) 실행시간 = %d\n", num, (end1-start1));
		
		
		/* 프록시 객체를 이용하여 실행 시간을 계산하는 방식 */
		Calculator iter = new ExeTimeCalculator(new IterCalculator());
		System.out.println(iter.factorial(num));
		
		Calculator rec = new ExeTimeCalculator(new RecCalculator());
		System.out.println(rec.factorial(num));
	}

}
