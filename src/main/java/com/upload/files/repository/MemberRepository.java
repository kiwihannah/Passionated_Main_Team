package com.upload.files.repository;

import com.upload.files.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 기본 제공 아이디로 유저찾기 */
    Member findByUsername(String username);

    /** 아이디 찾기용 */
    @Query("select m from Member m where phoneNo = :phoneNo and name = :name")
    Member findMember(@Param("phoneNo") String phoneNo, @Param("name") String name);

    /** 아이디 중복확인용 */
    boolean existsByUsername(String username);

    /** 전화번호 유효성 확인용 */
    boolean existsByPhoneNo(String phoneNo);

    /** 이름 유효성 확인용 */
    boolean existsByName(String name);

    /**회원 리스트 페이징 처리*/
    Page<Member> findAll(Pageable pageable);
}
