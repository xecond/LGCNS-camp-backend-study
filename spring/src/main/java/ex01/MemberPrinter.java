package ex01;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberPrinter {
	
	/* 자동 주입할 대상이 필수가 아닌 경우 */
	/* 방법1. required 필드를 이용해 선택적으로 주입 */
	@Autowired(required=false)
	private DateTimeFormatter dateTimeFormatter;
	
	@Autowired(required=false)
	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}
	
	/* 방법2. setter 메서드를 Optional로 구현 */
//	private DateTimeFormatter dateTimeFormatter;
//	
//	@Autowired
//	public void setDateTimeFormatter(Optional<DateTimeFormatter> dateTimeFormatterOptional) {
//		if (dateTimeFormatterOptional.isPresent()) {
//			this.dateTimeFormatter = dateTimeFormatterOptional.get();
//		} else {
//			this.dateTimeFormatter = null;
//		}
//	}
	
	/* 방법3. 필드에 Optional로 구현 (이 경우 setter 삭제 후 print 메서드에 코드 한 줄 추가) */
//	@Autowired
//    private Optional<DateTimeFormatter> dateTimeFormatterOptional;
	
	/* 방법4. @Nullable 이용 (이 경우 setter 삭제. 아니면 필드가 아닌 setter에 @Nullable을 이용하는 방법도 가능.) */
//	@Autowired
//    @Nullable
//    private DateTimeFormatter dateTimeFormatter;
	
	public void print(Member member) {
		
		// 방법3의 경우에 추가
//		DateTimeFormatter dateTimeFormatter = dateTimeFormatterOptional.orElse(null);
		
		if (dateTimeFormatter == null) {
			System.out.printf("회원정보: ID=%s, 이메일=%s, 이름=%s, 등록일=%tF\n",
					member.getId(), member.getEmail(), member.getName(),
					member.getRegisterDateTime());
		} else {
			System.out.printf("회원정보: ID=%s, 이메일=%s, 이름=%s, 등록일=%tF\n",
					member.getId(), member.getEmail(), member.getName(),
					dateTimeFormatter.format(member.getRegisterDateTime()));
		}
	}
}
