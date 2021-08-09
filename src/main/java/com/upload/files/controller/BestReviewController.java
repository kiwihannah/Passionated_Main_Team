package com.upload.files.controller;


import com.upload.files.entity.Article;
import com.upload.files.entity.BestReview;
import com.upload.files.repository.ArticleRepository;
import com.upload.files.repository.BestReviewRepository;
import com.upload.files.service.ArticleService;
import com.upload.files.service.BestReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class BestReviewController { //리뷰용 컨트롤러

	@Autowired private BestReviewRepository bestReviewRepository;
	@Autowired private BestReviewService bestReviewService;
	@Autowired private ArticleRepository articleRepository;
	@Autowired private ArticleService articleService;

	/** 베스트 게시판으로 옮기기 */
	@GetMapping("/transfer/{id}")
	public String setArticle(BestReview bestReview
			, Model model
			, @PathVariable Long id) {

		Article article = articleRepository.findById(id).get();

		bestReview.setTitle(article.getTitle());
		bestReview.setContent(article.getContent());
		bestReview.setWriter(article.getWriter());
		bestReview.setRegisterDate(article.getRegisterDate());

		bestReviewRepository.save(bestReview);

		List<BestReview> bestReviews = bestReviewRepository.findAll();
		model.addAttribute("bestReviews", bestReviews);

		/* 리뷰 게시판에 있는 것 삭제 */
		articleRepository.deleteById(id);

		return "redirect:/bestReview/list";
	}

	/** 전체 리스트 + 페이징 */
	@GetMapping("/bestReview/list")
	public String getBestReviewList(Model model) {

		List<BestReview> bestReviews = bestReviewRepository.findAll();
		model.addAttribute("bestReviews", bestReviews);

		return "bestReview/list";
	}

	/** 상세 페이지 */
	@GetMapping("/bestReview/{id}")
	public String getArticle(@PathVariable("id") Long id, Model model) {
		BestReview bestReview = bestReviewRepository.findById(id).get();
		model.addAttribute("bestReview", bestReview);

		List<BestReview> bestReviews = bestReviewRepository.findAll();
		model.addAttribute("bestReviews", bestReviews);

		return "bestReview/detail";
	}

	/** 삭제하기 */
	@GetMapping("/bestReview/delete/{id}")
	@Transactional
	public String deleteArticle(Model model, @PathVariable Long id) {
		bestReviewRepository.deleteById(id);

		return "redirect:/bestReview/list";
	}

}