package com.upload.files.repository;

import com.upload.files.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> { //댓글 CRUD 용

    /**
     * DB에 접근하여 데이터를 조회하는 메소드
     */
    Page<Article> findByTitleContaining(String keyword, Pageable pageable);

    Page<Article> findAll(Pageable pageable);

    Page<Article> findAllByWriter(String writer, Pageable pageable);

}