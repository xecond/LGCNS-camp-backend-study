package item.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import item.common.FileUtils;
import item.dto.ItemDto;
import item.dto.ItemFileDto;
import item.mapper.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
    private FileUtils fileUtils;

	@Override
	public List<ItemDto> selectItemList(String rarity, String itemType) {
		return itemMapper.selectItemList(rarity, itemType);
	}

	@Override
	public void insertItem(ItemDto itemDto, MultipartHttpServletRequest request) {
		itemMapper.insertItem(itemDto);
		
		try {
            List<ItemFileDto> fileInfoList = fileUtils.parseFileInfo(itemDto.getItemId(), request);
            
            if (!CollectionUtils.isEmpty(fileInfoList)) {
                itemMapper.insertItemFileList(fileInfoList);
            }

        } catch(Exception e) {
            
        }
	}

	@Override
	public ItemDto selectItemDetail(int itemId) {
		
		ItemDto itemDto = itemMapper.selectItemDetail(itemId);
		List<ItemFileDto> itemFileInfoList = itemMapper.selectItemFileList(itemId);
		itemDto.setFileInfoList(itemFileInfoList);
		
		return itemDto;
	}

	@Override
	public void updateItem(ItemDto itemDto) {
		itemMapper.updateItem(itemDto);
	}

	@Override
	public void deleteItem(int itemId) {
		ItemDto itemDto = new ItemDto();
		itemDto.setItemId(itemId);
		itemMapper.deleteItem(itemDto);
	}

	@Override
	public ItemFileDto selectItemFileInfo(int fileId, int itemId) {
		return itemMapper.selectItemFileInfo(fileId, itemId);
	}

}
