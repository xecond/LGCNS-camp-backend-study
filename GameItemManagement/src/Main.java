import java.util.Scanner;

public class Main {
	
	public static void printMenu() {
        System.out.println("\n===== 아이템 관리 ===== ");
        System.out.println("1. 아이템 등록");
        System.out.println("2. 보유 아이템 목록");
        System.out.println("3. 아이템 검색");
        System.out.println("4. 아이템 버리기");
        System.out.println("5. 등급별 아이템 조회");
        System.out.println("6. 최강 아이템 조합");
        System.out.println("7. 종료");
        System.out.print("메뉴를 선택하세요: ");
    }


	public static void main(String[] args) {
		ItemManagement itemManager = new ItemManagement();
        Scanner scanner = new Scanner(System.in);
        
        while(true) {
            printMenu();
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch(choice) {
            case 1: 
            	itemManager.addItem(scanner);
                break;
            case 2:
            	itemManager.listItems();
                break;
            case 3:
            	itemManager.searchItems(scanner);
                break;
            case 4:
            	itemManager.deleteItem(scanner);
                break;
            case 5: 
            	itemManager.filterItemsByRarity(scanner);
                break;
            case 6: 
            	itemManager.findBestItemCombination();
                break;
            case 7:
                System.out.println("프로그램을 종료합니다.");
                scanner.close();
                return;
            default:
                System.out.println("잘못된 입력입니다.");
            }
        }

	}

}
