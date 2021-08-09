package com.upload.files.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "product")
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue
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

    public Product(){}

    public Product(Long proNo, String proTitle, String proWriter, String proContent
            , String region, String season, String theme, int price, String N, String E, LocalDate registerDate) {
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
        this.registerDate = registerDate;
    }

}
