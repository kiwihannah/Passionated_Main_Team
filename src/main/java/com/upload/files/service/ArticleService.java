package com.upload.files.service;

import com.upload.files.entity.Article;
import com.upload.files.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ArticleService { //아직 사용하지 않는 로직

    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /** 전체 페이징 메소드 */
    public Page<Article> getArticleList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));

        return articleRepository.findAll(pageable);
    }

    /** 검색 메소드 */
    @Transactional
    public Page<Article> search(String keyword, Pageable pageable) {

        Page<Article> ArticleList = articleRepository.findByTitleContaining(keyword, pageable);
        Page<Article> ArticleAllList = articleRepository.findAll(pageable);

        if (!keyword.isEmpty()) {
            return ArticleList;
        } else {
            return ArticleAllList;
        }
    }
}
