package com.upload.files.controller;

import com.upload.files.entity.FilePath;
import com.upload.files.entity.Product;
import com.upload.files.repository.FilePathRepository;
import com.upload.files.service.FilePathService;
import com.upload.files.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.UUID;


@Controller
@Slf4j
public class FilesController { /*메인 이미지 파일 업로드 컨트롤러*/

    @Autowired private FilePathService filesService;
    @Autowired private FilePathRepository filePathRepository;
    @Autowired private ProductService productService;

    @RequestMapping("/admin/fileUpload")
    public String Insert() {
        return "/admin/fileUpload";
    }

    @PostMapping("/upload")
    public String fileInsert(MultipartFile[] files, HttpServletRequest request, Model model) {

        FilePath filePath = new FilePath();
        Long afterUpload = Long.parseLong(request.getParameter("proNo"));

        for (int i = 0; i < files.length; ++i) {
            if (!files[i].isEmpty()) {
                try {
                    String originalFilename = files[i].getOriginalFilename();
                    UUID uuid = UUID.randomUUID();
                    String destinationFileName = uuid + originalFilename;
                    String pathFile = uuid + originalFilename;

                    File file1 = new File("C:/upload/" + pathFile);
                    files[i].transferTo(file1);
                    String fileUrl = "C:/upload/";

                    filePath.setProNo(afterUpload);
                    filePath.setFileName(destinationFileName);
                    filePath.setFileOriName(originalFilename);
                    filePath.setFileUrl(fileUrl);

                    filesService.save(filePath);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                System.out.println("is empty");
            }
        }
        //Back to view
        return "redirect:/admin/list";
    }

    /**
     * 상품 정보 수정 관련
     * 정보 수정 후 파일 덮어쓰기(업로드 내용 삭제 후 재생성)
     */
    @RequestMapping("/admin/fileUpdate/{proNo}")
    @Transactional
    public String fileUpdate(Model model
            , @PathVariable("proNo") Long proNo) {

        /* 이미지 먼저 삭제 */
        int[] fno = filePathRepository.findAllFno(proNo);
        for (int i = 0; i < fno.length; ++i) {
            filePathRepository.deleteById(fno[i]);
        }

        /* 업로드 로직으로 돌아감 */
        return "/admin/fileUpload";
    }

    @GetMapping("/admin/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") String pageNum,
                       @PageableDefault Pageable pageable) {

        Page<Product> products = productService.pagingProducts(pageable);
        model.addAttribute("products", products);

        return "admin/itemList";
    }

}