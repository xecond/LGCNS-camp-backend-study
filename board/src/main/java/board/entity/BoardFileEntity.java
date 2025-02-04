package board.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_jpa_file")
@Data
@DynamicUpdate
public class BoardFileEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idx;
	
	//private int boardIdx;
	
	@Column(nullable = false)
	private String originalFileName;
	
	@Column(nullable = false)
	private String storedFilePath;
	
	@Column(nullable = false)
	private long fileSize;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_idx")
	private BoardEntity board;
}
