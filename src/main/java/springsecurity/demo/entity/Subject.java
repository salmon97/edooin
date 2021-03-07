package springsecurity.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springsecurity.demo.companent.PaymentListener;
import springsecurity.demo.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(PaymentListener.class)
public class Subject extends AbsEntity {

    @ManyToOne
    EduCenter eduCenter;

    private String name;
}


