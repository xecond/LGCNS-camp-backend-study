package item.dto;

import java.util.List;

import lombok.Data;

@Data
public class ItemDto {
	private int itemId;
	private String itemName;
	private String rarity;
	private String itemType;
	private int abilityValue;
	private String contents;
	private String createdDt;
	private String updatedDt;
	
	// 첨부 파일 정보를 저장할 필드를 추가
	private List<ItemFileDto> fileInfoList;
}
