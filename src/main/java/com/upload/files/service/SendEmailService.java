package com.upload.files.service;

import com.upload.files.entity.Member;
import com.upload.files.repository.MailDto;
import com.upload.files.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SendEmailService {

    @Autowired private MemberRepository memberRepository;
    @Autowired private JavaMailSender mailSender;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String FROM_ADDRESS = "passionatedtour@gmail.com";

    /**
     * 임시 비밀번호 생성 로직
     */
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    /**
     * 암호화->DB저장, raw pw->고객 이메일 전송 값 설정
     */
    public MailDto createMailAndChangePassword(String username, String name) {
        String str = getTempPassword();
        String pw = bCryptPasswordEncoder.encode(str);

        if (memberRepository.existsByUsername(username) && memberRepository.existsByName(name)) {
            Member member = memberRepository.findByUsername(username);
            member.setPassword(pw);
            memberRepository.save(member);

            MailDto mailDto = new MailDto();
            mailDto.setAddress(username);
            mailDto.setTitle(name + "님의 너나들이투어 임시비밀번호 안내 이메일 입니다.");
            mailDto.setMessage("안녕하세요. 너나들이투어 임시비밀번호 안내 관련 이메일 입니다."
                    + "[" + name + "]" + "님의 임시 비밀번호는 [" + str + "] 입니다.");

            return mailDto;
        } else {
            return null;
        }
    }

    /**
     * 가입 링크 이메일 전송
     */
    public MailDto verifyUserAccount(String username) {
        MailDto mailDto = new MailDto();
        mailDto.setAddress(username);
        mailDto.setTitle("[너나들이 투어] 이메일 계정인증 링크 안내");
        mailDto.setMessage("안녕하세요. 가입을 계속하려면 링크를 클릭해 주세요.☞"
                + "http://localhost:8080/user/join/" + username);

        return mailDto;
    }

    /**
     * 이메일 전송 로직
     */
    public void mailSend(MailDto mailDto) {

        if (mailDto != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailDto.getAddress());
            message.setFrom(SendEmailService.FROM_ADDRESS);
            message.setSubject(mailDto.getTitle());
            message.setText(mailDto.getMessage());

            mailSender.send(message);
        }
    }
}

