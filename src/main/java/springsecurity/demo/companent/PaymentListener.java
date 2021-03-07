package springsecurity.demo.companent;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springsecurity.demo.entity.PaymentLog;
import springsecurity.demo.entity.Subject;
import springsecurity.demo.repository.PaymentListernRepository;

import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Component
@NoArgsConstructor
public class PaymentListener {

    PaymentListernRepository paymentListernRepository;

    @Autowired
    public PaymentListener(@Lazy PaymentListernRepository paymentListernRepository) {
        this.paymentListernRepository = paymentListernRepository;
    }


    @PreUpdate
    public void editEduPayment(Object object) {
        Subject payment = (Subject) object;
        saveLog(payment, "update");
    }

    @PreRemove
    public void deleteEduPayment(Object object) {
        Subject payment = (Subject) object;
        saveLog(payment, "delete");
    }

    @Transactional
    public void saveLog(Subject payment, String type) {
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setPaymentId(payment.getId());
        paymentLog.setType(type);
        paymentListernRepository.save(paymentLog);
    }
}
