package com.upload.files.repository;

import com.upload.files.entity.FilePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilePathRepository extends JpaRepository<FilePath, Integer> { //제품 이미지용 repository

    @Query(value = "select f.fno from FilePath f where f.proNo = :proNo", nativeQuery = false)
    int[] findAllFno(@Param("proNo") Long proNo);

}


