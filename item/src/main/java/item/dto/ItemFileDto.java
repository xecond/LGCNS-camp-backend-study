package item.dto;

import lombok.Data;

@Data
public class ItemFileDto {
	private int fileId;
	private int itemId;
	private String originalFileName;
	private String storedFilePath;
	private String fileSize;
}
