package com.upload.files.service;

import com.upload.files.entity.Member;
import com.upload.files.repository.MemberDto;
import com.upload.files.repository.MemberRepository;

import com.upload.files.repository.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class MemberService implements UserDetailsService {

    @Autowired private MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDto memberDto) {

        /* 비밀번호 암호화 */
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    /**
     * 로그인 확인 로직
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String admin = "passionatedtour@gmail.com";

        Member userEntityWrapper = memberRepository.findByUsername(username);
        Member userEntity = userEntityWrapper;
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (admin.equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }

    /**
     * username 중복 확인 로직
     */
    public boolean checkUsernameDuplicate(String username) {
        return memberRepository.existsByUsername(username);
    }

    /**
     * email + 이름 유효성 검사
     */
    public boolean userEmailCheck(String username, String name) {

        if (memberRepository.existsByUsername(username) && memberRepository.existsByName(name)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 아이디 + 이름 찾기 유효성 검사
     */
    public boolean findEmail(String phoneNo, String name) {

        Member member = memberRepository.findMember(phoneNo, name);

        if(member != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 회원 리스트 페이징 메소드
     */
    public Page<Member> getMemberList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1 );
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));

        return memberRepository.findAll(pageable);
    }
    
    /** 비밀번호 암호화 */
    public String modifyPw(MemberDto memberDto) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String securedPw = encoder.encode(memberDto.getPassword());

        return securedPw;
    }

}