package com.upload.files.controller;


import com.upload.files.entity.Article;
import com.upload.files.entity.FilePath;
import com.upload.files.entity.Product;
import com.upload.files.repository.ArticleRepository;
import com.upload.files.repository.FilePathRepository;
import com.upload.files.service.ArticleService;
import com.upload.files.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Controller
public class ArticleController { //리뷰용 컨트롤러

	@Autowired private ArticleRepository articleRepository;
	@Autowired private ArticleService articleService;
	@Autowired private ProductService productService;
	@Autowired private FilePathRepository filePathRepository;

	/**
	 * 폼으로 가기
	 */
	@GetMapping("/form")
	public String form(@RequestParam(name = "proNo") Long proNo,
					   @RequestParam(name = "N") String N,
					   @RequestParam(name = "E") String E, Model model) {

		model.addAttribute("proNo", proNo);
		model.addAttribute("N", N);
		model.addAttribute("E", E);

		return "article/write";
	}

	/**글쓰기*/
	@PostMapping("/write")
	public String setArticle(Article article,
							 @AuthenticationPrincipal UserDetails userDetails,
							 @RequestParam(name = "proNo") Long proNo,
							 @PageableDefault Pageable pageable,
							 @RequestParam(value = "page", defaultValue = "1") String pageNum,
							 Model model) {
		article.setRegisterDate(LocalDate.now());
		article.setWriter(userDetails.getUsername());
		articleRepository.save(article);

		Product product = productService.findOne(proNo);
		model.addAttribute("productInfo", product);

		int[] fno = filePathRepository.findAllFno(proNo);
		FilePath MainImg = filePathRepository.findById(fno[0]).get();
		FilePath DetailImg = filePathRepository.findById(fno[1]).get();

		model.addAttribute("MainImg", MainImg.getFileName());
		model.addAttribute("DetailImg", DetailImg.getFileName());

		Page<Article> articleList = articleService.getArticleList(pageable);
		model.addAttribute("articleList", articleList);

		return "board/get";
	}

	/** 전체 리스트 + 페이징 */
	@GetMapping("/article/list")
	public String getArticleList(Model model, @PageableDefault Pageable pageable,
								 @RequestParam(value = "page", defaultValue = "1") String pageNum,
								 @AuthenticationPrincipal UserDetails userDetails,
								 @RequestParam(name = "proNo") Long proNo) {
		Page<Article> articleList = articleService.getArticleList(pageable);
		model.addAttribute("articleList", articleList);

		Product product = productService.findOne(proNo);
		model.addAttribute("productInfo", product);

		int[] fno = filePathRepository.findAllFno(proNo);
		FilePath MainImg = filePathRepository.findById(fno[0]).get();
		FilePath DetailImg = filePathRepository.findById(fno[1]).get();

		model.addAttribute("MainImg", MainImg.getFileName());
		model.addAttribute("DetailImg", DetailImg.getFileName());
		model.addAttribute("userDetails", userDetails);

		return "article/list";
	}

	/**상세 페이지*/
	@GetMapping("/article/detail")
	public String getArticle(@RequestParam("id") Long id,
							 @RequestParam(name="proNo", required = false) Long proNo,
							 @AuthenticationPrincipal UserDetails userDetails,
							 Model model) {
		model.addAttribute("userDetails", userDetails);

		Article article = articleRepository.findById(id).get();
		model.addAttribute("article", article);

		List<Article> listArticles = articleRepository.findAll();
		model.addAttribute("listArticles", listArticles);

		model.addAttribute("proNo", proNo);

		return "article/detail";
	}

	/**수정 페이지 들어가기*/
	@GetMapping("/article/update")
	public String getArticleUpdate(Model model, @RequestParam Long id,
								   @AuthenticationPrincipal UserDetails userDetails,
								   @RequestParam("proNo") Long proNo) {
		model.addAttribute("userDetails", userDetails);

		Article article = articleRepository.findById(id).get();
		model.addAttribute("article", article);

		model.addAttribute("proNo", proNo);

		return "article/update";
	}

	/**수정 하기*/
	@PostMapping(value = "/article/updated")
	public String setArticleUpdate(Model model, @RequestParam Long id,
								   @RequestParam(name="proNo", required = false) Long proNo,
								   @AuthenticationPrincipal UserDetails userDetails,
								   Article updatedArticle) {
		model.addAttribute("userDetails", userDetails);

		Article article = articleRepository.findById(id).get();
		article.setTitle(updatedArticle.getTitle());
		article.setContent(updatedArticle.getContent());
		article.setUpdateDate(LocalDate.now());
		articleRepository.save(article);
		model.addAttribute("article", article);

		List<Article> articleList = articleRepository.findAll();
		model.addAttribute("listArticles", articleList);

		model.addAttribute("proNo", proNo);

		return "article/detail";
	}

	/** 삭제하기*/
	@GetMapping("/article/delete")
	@Transactional
	public String deleteArticle(Model model,
								@RequestParam Long id,
								@RequestParam("proNo") Long proNo,
								@PageableDefault Pageable pageable,
								@AuthenticationPrincipal UserDetails userDetails,
								@RequestParam(value = "page", defaultValue = "1") String pageNum) {
		model.addAttribute("userDetails", userDetails);

		articleRepository.deleteById(id);

		Product product = productService.findOne(proNo);
		model.addAttribute("productInfo", product);

		int[] fno = filePathRepository.findAllFno(proNo);
		FilePath MainImg = filePathRepository.findById(fno[0]).get();
		FilePath DetailImg = filePathRepository.findById(fno[1]).get();

		model.addAttribute("MainImg", MainImg.getFileName());
		model.addAttribute("DetailImg", DetailImg.getFileName());

		Page<Article> articleList = articleService.getArticleList(pageable);
		model.addAttribute("articleList", articleList);

		return "board/get";
	}

	/** 개인 리뷰 모음 리스트 */
	@GetMapping("/myReview/list")
	public String myReviewAll(@AuthenticationPrincipal UserDetails userDetails
			, Model model, @PageableDefault Pageable pageable
			, @RequestParam(value = "page", defaultValue = "1") String pageNum) {

		Page<Article> articleList = articleRepository.findAllByWriter(userDetails.getUsername(), pageable);
		model.addAttribute("articleList", articleList);

		return "myReview/list";
	}

	/** 개인리뷰 디테일 --> 수정 삭제 로직으로 전환 */
	@GetMapping("/myReview/detail/{id}")
	public String myReviewDetail(@AuthenticationPrincipal UserDetails userDetails
			, @PathVariable Long id
			, Model model, @PageableDefault Pageable pageable
			, @RequestParam(value = "page", defaultValue = "1") String pageNum) {

		Article article = articleRepository.findById(id).get();
		model.addAttribute("article", article);

		return "myReview/detail";
	}

	/** 개인리뷰 수정 */
	@GetMapping("/myReview/update/{id}")
	public String myReviewUpdateForm(@PathVariable Long id, Article updatedArticle
			, @PageableDefault Pageable pageable, @AuthenticationPrincipal UserDetails userDetails
			, @RequestParam(value = "page", defaultValue = "1") String pageNum, Model model) {

		Article article = articleRepository.findById(id).get();
		model.addAttribute("article", article);

		return "myReview/update";
	}

	@PostMapping("/myReview/update/{id}")
	public String myReviewUpdate(@PathVariable Long id, Article updatedArticle
			, @PageableDefault Pageable pageable, @AuthenticationPrincipal UserDetails userDetails
			, @RequestParam(value = "page", defaultValue = "1") String pageNum, Model model) {

		Article article = articleRepository.findById(id).get();
		article.setTitle(updatedArticle.getTitle());
		article.setContent(updatedArticle.getContent());
		article.setUpdateDate(LocalDate.now());
		articleRepository.save(article);

		Page<Article> articleList = articleRepository.findAllByWriter(userDetails.getUsername(), pageable);
		model.addAttribute("articleList", articleList);


		return "myReview/list";
	}

	/** 개인 리뷰 확인후 삭제 */
	@GetMapping("/myReview/delete/{id}")
	public String deleteMine(@PathVariable Long id, Article updatedArticle
			, @PageableDefault Pageable pageable, @AuthenticationPrincipal UserDetails userDetails
			, @RequestParam(value = "page", defaultValue = "1") String pageNum, Model model) {

		articleRepository.deleteById(id);

		Page<Article> articleList = articleRepository.findAllByWriter(userDetails.getUsername(), pageable);
		model.addAttribute("articleList", articleList);

		return "myReview/list";
	}
}