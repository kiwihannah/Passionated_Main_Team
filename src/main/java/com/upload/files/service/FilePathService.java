package com.upload.files.service;

import com.upload.files.entity.FilePath;
import com.upload.files.repository.FilePathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilePathService { //제품용 이미지 저장 위한 서비스 => 저장을 하기 위해 준비하는 단계

    @Autowired private FilePathRepository filePathRepository;
    @Autowired private ProductService productService;

    public void save(FilePath files) {
        FilePath f = new FilePath();
        f.setFileName(files.getFileName());
        f.setFileOriName(files.getFileOriName());
        f.setFileUrl(files.getFileUrl());
        f.setProNo(files.getProNo());

        filePathRepository.save(f);
    }

    public void deleteByProNo(int fno) {
        filePathRepository.deleteById(fno);
    }

}
