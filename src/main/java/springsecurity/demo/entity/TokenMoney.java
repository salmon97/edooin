package springsecurity.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import springsecurity.demo.entity.template.AbsEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenMoney  {

    @Id
    private UUID id;

    private Date date;

    private Double tokenMoney;

    @ManyToOne
    private StJoinGroup stJoinGroup;

}
