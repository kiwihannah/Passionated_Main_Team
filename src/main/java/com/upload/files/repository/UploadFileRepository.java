package com.upload.files.repository;

import com.upload.files.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long>{ // 리뷰 이미지용 repository
}
