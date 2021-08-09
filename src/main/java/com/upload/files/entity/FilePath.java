package com.upload.files.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter @Setter
@Table(name = "files")
public class FilePath { //메인 이미지(제품)용 엔티티 - DB에 저장용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int fno;
    String fileName;
    String fileOriName;
    String fileUrl;
    Long proNo;

}
