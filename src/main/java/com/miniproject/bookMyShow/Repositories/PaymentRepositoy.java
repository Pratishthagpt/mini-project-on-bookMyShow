package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepositoy extends JpaRepository<Payment, Long> {
    @Override
    Optional<Payment> findById(Long aLong);
    Payment save (Payment payment);
}
