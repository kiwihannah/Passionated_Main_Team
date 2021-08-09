package com.upload.files.service;

import com.upload.files.entity.BestReview;
import com.upload.files.repository.BestReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BestReviewService { //아직 사용하지 않는 로직

    private BestReviewRepository bestReviewRepository;

    public BestReviewService(BestReviewRepository bestReviewRepository) {
        this.bestReviewRepository = bestReviewRepository;
    }

    /** 전체 페이징 메소드 */
    public Page<BestReview> getBestReviewList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));

        return bestReviewRepository.findAll(pageable);
    }

    /** 검색 메소드 */
    @Transactional
    public Page<BestReview> search(String keyword, Pageable pageable) {

        Page<BestReview> BestReviewList = bestReviewRepository.findByTitleContaining(keyword, pageable);
        Page<BestReview> BestReviewAllList = bestReviewRepository.findAll(pageable);

        if (!keyword.isEmpty()) {
            return BestReviewList;
        } else {
            return BestReviewAllList;
        }
    }

}
