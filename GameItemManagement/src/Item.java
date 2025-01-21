import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    
    private String name;      // 이름
    private String rarity;    // 등급: Common, Rare, Legendary, Mythical
    private String type;      // 종류: 무기, 방어구, 식품
    private int quantity;     // 수량
    private int abilityValue; // 능력치 (공격력, 방어력, 회복량)
}
