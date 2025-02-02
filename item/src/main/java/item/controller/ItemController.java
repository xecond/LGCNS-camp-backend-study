package item.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import item.dto.ItemDto;
import item.dto.ItemFileDto;
import item.service.ItemService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ItemController {
	@Autowired
    private ItemService itemService;
    
    @GetMapping("/item/openItemList.do")
    public ModelAndView openItemList(
    		@RequestParam(name = "rarity", required = false) String rarity,
            @RequestParam(name = "itemType", required = false) String itemType) throws Exception {
        
    	ModelAndView mv = new ModelAndView("/item/itemList");
        
        List<ItemDto> list = itemService.selectItemList(rarity, itemType);
        mv.addObject("list", list);
        
        return mv;
    }
    
    // 아이템 등록 화면 요청 처리
    @GetMapping("/item/openItemWrite.do")
    public String openItemWrite() throws Exception {
        return "/item/itemWrite";
    }
    
    // 아이템 저장 요청 처리
    @PostMapping("/item/insertItem.do")
    public String insertItem(ItemDto itemDto, MultipartHttpServletRequest request) throws Exception {
        itemService.insertItem(itemDto, request);
        return "redirect:/item/openItemList.do";
    }

    // 상세 조회 요청 처리
    @GetMapping("/item/openItemDetail.do")
    public ModelAndView openItemDetail(@RequestParam("itemId") int itemId) throws Exception {
        ItemDto itemDto = itemService.selectItemDetail(itemId);
        
        ModelAndView mv = new ModelAndView("/item/itemDetail");
        mv.addObject("item", itemDto);
        return mv;
    }
    
    // 수정 요청 처리
    @PostMapping("/item/updateItem.do")
    public String updateItem(ItemDto itemDto) throws Exception {
        itemService.updateItem(itemDto);
        return "redirect:/item/openItemList.do";
    }
    
    // 삭제 요청 처리
    @PostMapping("/item/deleteItem.do")
    public String deleteItem(@RequestParam("itemId") int itemId) throws Exception {
        itemService.deleteItem(itemId);
        return "redirect:/item/openItemList.do";
    }
    
    // 파일 다운로드 요청 처리
    @GetMapping("item/downloadItemFile.do")
    public void downloadItemFile(@RequestParam("fileId") int fileId, @RequestParam("itemId") int itemId, HttpServletResponse response) throws Exception {
    	ItemFileDto itemFileDto = itemService.selectItemFileInfo(fileId, itemId);
    	if (ObjectUtils.isEmpty(itemFileDto)) {
    		return;
    	}
    	
    	Path path = Paths.get(itemFileDto.getStoredFilePath());
        byte[] file = Files.readAllBytes(path);
        
        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(itemFileDto.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

}
