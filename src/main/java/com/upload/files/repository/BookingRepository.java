package com.upload.files.repository;

import com.upload.files.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    //order CRUD 용
    @Query(value = "select b from Booking b where orderId = :orderId", nativeQuery = false)
    Booking findByOrderId(@Param("orderId") Long orderId);

    @Query(value = "select b from Booking b where b.member.id = :id", nativeQuery = false)
    List<Booking> findByMemberId(@Param("id") Long id);

}
