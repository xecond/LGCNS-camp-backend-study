import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ItemManagement {
	// 아이템 목록
    private List<Item> items = new ArrayList<>();
    // 종류별 아이템 목록 - 아이템 이름, 능력치
    private Map<String, Integer> weaponItems = new HashMap<>();
    private Map<String, Integer> armorItems = new HashMap<>();
    private Map<String, Integer> foodItems = new HashMap<>();

    // 이름 목록
    private Set<String> nameSet = new HashSet<>(); 
    
    // 등급
    private String rarityKeywords = "CRLM";
    
    private Map<String, String> rarityMap = new HashMap<>();
    {
    	rarityMap.put("C", "COMMON");
        rarityMap.put("R", "RARE");
        rarityMap.put("L", "LEGENDARY");
        rarityMap.put("M", "MYTHICAL");
    }
    
    // 종류
    private Set<String> typeSet = new HashSet<>();
    {
    	typeSet.add("무기");
    	typeSet.add("방어구");
    	typeSet.add("식품");
    }
    
    // 아이템 등록
    public void addItem(Scanner scanner) {
        System.out.print("아이템 이름: ");
        String name = scanner.nextLine();
        
        if (nameSet.contains(name)) {
        	// 이름 같은 아이템 찾아서 수량 하나 증가
        	for (Item existingItem : items) {
                if (existingItem.getName().trim().toLowerCase().contains(name)) {
                	existingItem.setQuantity(existingItem.getQuantity() + 1);
                	System.out.println("정상적으로 추가되었습니다.");
                	System.out.printf("아이템 이름: %s | 등급: %s | 종류: %s | 수량: %d | 능력치: %d\n", existingItem.getName(), existingItem.getRarity(), existingItem.getType(), existingItem.getQuantity(), existingItem.getAbilityValue());
                	break;
                }
            }
        	return;
        }
        
        System.out.print("아이템 등급(C, R, L, M 중 택1): ");
        String rarityKeyword = scanner.nextLine();
        String rarity = new String("");
        
        if (rarityKeywords.contains(rarityKeyword)) {
        	rarity = rarityMap.get(rarityKeyword);
        } else {
        	System.out.println("존재하지 않는 아이템 등급을 입력하였습니다.");
        	return;
        }
        
        System.out.print("아이템 종류(무기, 방어구, 식품 중 택1): ");
        String type = scanner.nextLine();
        if (!typeSet.contains(type)) {
        	System.out.println("존재하지 않는 아이템 종류를 입력하였습니다.");
        	return;
        }
        
        System.out.print("능력치: ");
        int abilityValue = scanner.nextInt();
        scanner.nextLine();
        
        Item item = new Item(name, rarity, type, 1, abilityValue);
        items.add(item);
        switch (item.getType()) {
        case "무기":
        	weaponItems.put(item.getName(), item.getAbilityValue());
        	break;
        case "방어구":
        	armorItems.put(item.getName(), item.getAbilityValue());
        	break;
        case "식품":
        	foodItems.put(item.getName(), item.getAbilityValue());
        	break;
        }
        nameSet.add(item.getName());
        System.out.println("정상적으로 등록되었습니다.");
        System.out.printf("아이템 이름: %s | 등급: %s | 종류: %s | 수량: %d | 능력치: %d\n", item.getName(), item.getRarity(), item.getType(), item.getQuantity(), item.getAbilityValue());

    }
    
    // 보유 아이템 목록 조회
    public void listItems() {
        if (items.isEmpty()) {
            System.out.println("보유 중인 아이템이 없습니다.");
            return;
        } 
        
        //items.forEach(System.out::println);
        for (Item item : items) {
        	System.out.printf("아이템 이름: %s | 등급: %s | 종류: %s | 수량: %d | 능력치: %d\n", item.getName(), item.getRarity(), item.getType(), item.getQuantity(), item.getAbilityValue());
        }
    }
    
    // 아이템 검색 => 아이템 이름에 검색어가 포함되어 있는지 검색
    public void searchItems(Scanner scanner) {
        System.out.print("검색어를 입력하세요: ");
        String keyword = scanner.nextLine();        
        keyword = keyword.trim().toLowerCase();
        
        boolean found = false;
        for (Item item : items) {
        	if (item.getName().trim().toLowerCase().contains(keyword)) {
        		System.out.printf("아이템 이름: %s | 등급: %s | 종류: %s | 수량: %d | 능력치: %d\n", item.getName(), item.getRarity(), item.getType(), item.getQuantity(), item.getAbilityValue());
        		found = true;
        	}
        }
        
        if (!found) {
            System.out.printf("'%s'를 포함하는 아이템이 없습니다.\n", keyword);
        }
    }
    
    // 아이템 버리기
    public void deleteItem(Scanner scanner) {
        System.out.print("버릴 아이템의 이름을 입력하세요: ");
        String name = scanner.nextLine();
        
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
        	Item item = iterator.next();
        	if (item.getName().equals(name)) {
        		// 수량이 한 개인 경우: 삭제
        		if (item.getQuantity() == 1) {
        			switch (item.getType()) {
        	        case "무기":
        	        	weaponItems.remove(item.getName());
        	        	break;
        	        case "방어구":
        	        	armorItems.remove(item.getName());
        	        	break;
        	        case "식품":
        	        	foodItems.remove(item.getName());
        	        	break;
        	        }
        			iterator.remove();
        			nameSet.remove(name);
        			System.out.println("아이템을 버렸습니다. 해당 아이템이 인벤토리에서 완전히 삭제되었습니다.");
        			return;
        		}
        		// 수량이 한 개 이상인 경우: 수량 하나 감소
        		else {
        			item.setQuantity(item.getQuantity() - 1);
        			System.out.printf("아이템을 버렸습니다. 해당 아이템의 남은 수량은 %d입니다.", item.getQuantity());
        			return;
        		}
        	}
        }
        
        System.out.printf("아이템 이름이 '%s'와 일치하는 아이템을 찾을 수 없습니다.\n", name);
    }
    
    // 등급별 아이템 조회
    public void filterItemsByRarity(Scanner scanner) {
    	System.out.print("조회할 아이템 등급을 입력해주세요(C, R, L, M 중 택1): ");
    	String rarityKeyword = scanner.nextLine();
    	
    	if (rarityKeywords.contains(rarityKeyword)) {
    		boolean found = false;
            for (Item item : items) {
            	if (item.getRarity().charAt(0) == rarityKeyword.charAt(0)) {
            		System.out.printf("아이템 이름: %s | 등급: %s | 종류: %s | 수량: %d | 능력치: %d\n", item.getName(), item.getRarity(), item.getType(), item.getQuantity(), item.getAbilityValue());
            		found = true;
            	}
            }
            
            if (!found) {
                System.out.println("해당 등급의 아이템을 보유하고 있지 않습니다.");
            }
    	} else {
    		System.out.println("해당 등급은 존재하지 않습니다. C, R, L, M 중 하나를 입력해주세요.");
    	}
    	
    	
    }
    
    // 종류별 최고 능력치를 가진 아이템을 찾는 함수
    public String findBestItem(Map<String, Integer> typeItems) {
        if (typeItems.isEmpty()) {
            return "없음";
        }

        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : typeItems.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        
        String result = maxEntry != null ? maxEntry.getKey() + " (능력치: " + maxEntry.getValue() + ")" : "없음";

        return result;
    }
    
    // 최강 아이템 세트
    public void findBestItemCombination() {
    	System.out.println("현재 보유 아이템 목록을 기반으로 한 최강의 조합은 다음과 같습니다.");
    	String bestWeapon = findBestItem(weaponItems);
    	String bestArmor = findBestItem(armorItems);
    	String bestFood = findBestItem(foodItems);

    	System.out.printf("[무기] %s\n", bestWeapon);
    	System.out.printf("[방어구] %s\n", bestArmor);
    	System.out.printf("[식품] %s\n", bestFood);
    }

}
