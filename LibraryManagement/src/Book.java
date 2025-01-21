import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Book {
	private String isbn;
	private String title;
	private String author;
	private int year;
}
