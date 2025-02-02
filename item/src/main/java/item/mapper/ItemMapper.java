package item.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import item.dto.ItemDto;
import item.dto.ItemFileDto;

@Mapper
public interface ItemMapper {
	List<ItemDto> selectItemList(@Param("rarity") String rarity, @Param("itemType") String itemType);
	void insertItem(ItemDto itemDto);
	ItemDto selectItemDetail(int itemId);
	void updateItem(ItemDto itemDto);
	void deleteItem(ItemDto itemDto);
	void insertItemFileList(List<ItemFileDto> fileInfoList);
	List<ItemFileDto> selectItemFileList(int itemId);
	ItemFileDto selectItemFileInfo(@Param("fileId") int fileId, @Param("itemId") int itemId);
}
