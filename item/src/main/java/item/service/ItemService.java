package item.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import item.dto.ItemDto;
import item.dto.ItemFileDto;

public interface ItemService {
	List<ItemDto> selectItemList(String rarity, String itemType);
	void insertItem(ItemDto itemDto, MultipartHttpServletRequest request);
	ItemDto selectItemDetail(int itemId);
	void updateItem(ItemDto itemDto);
	void deleteItem(int itemId);
	ItemFileDto selectItemFileInfo(int fileId, int itemId);
}
