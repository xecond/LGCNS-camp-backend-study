import java.util.Scanner;

public class Main {
	
	public static void printMenu() {
		System.out.println("\n도서 관리: ");
		System.out.println("1. 도서 등록");
		System.out.println("2. 전체 도서 출력");
		System.out.println("3. 도서 검색");
		System.out.println("4. 도서 삭제");
		System.out.println("5. 인기 도서 조회");
		System.out.println("6. 종료");
		System.out.print("메뉴를 선택하세요: ");
	}

	public static void main(String[] args) {
		LibraryManagement library = new LibraryManagement();
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			printMenu();
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch(choice) {
			case 1:
				library.addBook(scanner);
				break;
			case 2:
				library.listBooks();
				break;
			case 3:
				library.searchBooks(scanner);
				break;
			case 4:
				library.deleteBook(scanner);
				break;
			case 5:
				library.mostViewBooks();
				break;
			case 6:
				System.out.println("프로그램을 종료합니다.");
				scanner.close();
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}

		}

	}

}
