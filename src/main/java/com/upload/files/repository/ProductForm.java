package com.upload.files.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class ProductForm { //컨테이너(상품 관련 데이터 일시적으로 담는 용도)

    private Long proNo;
    private String proTitle;
    private String proWriter;
    private int price;
    private String proContent;
    private String region;
    private String season;
    private String theme;
    private String N; //위도
    private String E; //경도

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate registerDate;

    private int fno;

    public ProductForm() {}

    public ProductForm(Long proNo, String proTitle, String proWriter, int price
            , String proContent, String region, String season, String theme, String N
            , String E, int fno, LocalDate registerDate) {
        this.proNo = proNo;
        this.proTitle = proTitle;
        this.proWriter = proWriter;
        this.price = price;
        this.proContent = proContent;
        this.region = region;
        this.season = season;
        this.theme = theme;
        this.N = N;
        this.E = E;
        this.fno = fno;
        this.registerDate = registerDate;
    }
}
