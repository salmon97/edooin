package springsecurity.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springsecurity.demo.entity.PaymentLog;

import java.util.UUID;


public interface PaymentListernRepository extends JpaRepository<PaymentLog, UUID> {
}