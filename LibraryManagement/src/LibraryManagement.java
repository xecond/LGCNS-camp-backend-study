import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class LibraryManagement {
	// 도서 정보 
	private List<Book> books = new ArrayList<>();
	
	// ISBN => 도서 등록 시 동일 도서가 존재하는지 확인
	private Set<String> isbnSet = new HashSet<>();
	
	// 조회수 => ISBN, 조회수 정보를 관리
	private Map<String, Integer> bookViews = new HashMap<>();
	
	// 도서 등록
	public void addBook(Scanner scanner) {
		System.out.print("ISBN: ");
		String isbn = scanner.nextLine();
		
		if (isbnSet.contains(isbn)) {
			System.out.printf("ISBN %s과 동일한 도서가 이미 등록되어 있습니다\n", isbn);
			return;
		}
		
		System.out.print("제목: ");
		String title = scanner.nextLine();
		
		System.out.print("저자: ");
		String author = scanner.nextLine();
		
		System.out.print("년도: ");
		int year = scanner.nextInt();
		scanner.nextLine();
		
		Book book = new Book(isbn, title, author, year);
		books.add(book);
		isbnSet.add(book.getIsbn());
		bookViews.put(book.getIsbn(), 0);
		System.out.println("정상적으로 등록되었습니다." + book);
	}
	
	// 전체 도서 목록 조회
	public void listBooks() {
		if (books.isEmpty()) {
			System.out.println("등록된 도서가 없습니다.");
			return;
		}
		
		books.forEach(System.out::println);
	}
	
	// 도서 검색 => 도서명과 저자 항목에 검색어가 포함되어 있는지 검색
	public void searchBooks(Scanner scanner) {
		System.out.println("검색어를 입력하세요: ");
		String keyword = scanner.nextLine();
		keyword = keyword.trim().toLowerCase();
		
		boolean found = false;
		for (Book book : books) {
			if (book.getTitle().trim().toLowerCase().contains(keyword) ||
					book.getAuthor().trim().toLowerCase().contains(keyword)) {
				System.out.println(book);
				bookViews.put(book.getIsbn(), bookViews.get(book.getIsbn()) + 1);
				found = true;
			}
		}
		
		if (!found) {
			System.out.printf("%s를 포함하는 도서가 없습니다.\n", keyword);
		}
	}
	
	// 도서 삭제
	public void deleteBook(Scanner scanner) {
		System.out.print("삭제할 도서의 ISBN을 입력하세요: ");
		String isbn = scanner.nextLine();
		
		Iterator<Book> iterator = books.iterator();
		while (iterator.hasNext()) {
			Book book = iterator.next();
			if (book.getIsbn().equals(isbn)) {
				iterator.remove();
				isbnSet.remove(isbn);
				bookViews.remove(isbn);
				System.out.printf("ISBN %s 책을 삭제했습니다.\n", isbn);
				return;
			}
		}
		
		System.out.printf("ISBN %s와 일치하는 도서를 찾을 수 없습니다.\n", isbn);
	}
	
	// 인기 도서 조회
	public void mostViewBooks() {
		int maxViews = Collections.max(bookViews.values());
		
		if (bookViews.isEmpty() || maxViews == 0) {
			System.out.println("등록된 인기 도서가 없습니다.");
			return;
		}
		
		for (Map.Entry<String, Integer> entry : bookViews.entrySet()) {
			if (entry.getValue() == maxViews) {
				books.stream()
					.filter(book -> book.getIsbn().equals(entry.getKey()))
					.forEach(System.out::println);
			}
		}
	}
}
