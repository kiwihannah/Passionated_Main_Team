package com.upload.files.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@ToString
@Table(name = "review")
public class Article { //댓글 테이블 생성용
	@Id @GeneratedValue
	private Long id;

	private String title;

	@Column(length= 100000000)
	private String content;

	private String writer;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate registerDate;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate updateDate;
}
