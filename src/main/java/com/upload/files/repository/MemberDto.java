package com.upload.files.repository;

import com.upload.files.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String gender;
    private String address;
    private String birthdate;
    private String phoneNo;
    private LocalDate regDate;
    private String role;

    public Member toEntity(){
        return Member.builder()
                .username(username)
                .password(password)
                .address(address)
                .name(name)
                .gender(gender)
                .birthdate(birthdate)
                .phoneNo(phoneNo)
                .role(Role.MEMBER)
                .build();
    }

    @Builder
    public MemberDto(Long id, String username, String password, String name
            , String gender, String address, String birthdate, String phoneNo, String role) {
        this.id = id;
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
