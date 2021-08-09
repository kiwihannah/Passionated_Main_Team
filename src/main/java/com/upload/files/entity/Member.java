package com.upload.files.entity;

import com.upload.files.repository.Role;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String username; //email 로그인 id로 사용
    private String password;
    private String name;
    private String gender;
    private String address;
    private String phoneNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthdate;

    @Column(name = "RegDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Booking> bookingList = new ArrayList<>();

    @Builder
    public Member(String username, String password, String name
            , String gender, String address, String birthdate
            , String phoneNo, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.birthdate = birthdate;
        this.phoneNo = phoneNo;
        this.role = role;
    }

}