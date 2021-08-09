package com.upload.files.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class UploadFile { //리뷰 사진용 엔티티
	
	@Id @GeneratedValue
	private Long id;
	
	@Column
	private String fileName;
	
	@Column
	private String saveFileName;
	
	@Column
	private String filePath;
	
	@Column
	private String contentType;
	
	private long size;
	
	private LocalDate registerDate;

}
