package springsecurity.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import springsecurity.demo.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTable extends AbsEntity {

    @ManyToOne
    private Group group;

    @ManyToMany
    private Set<WeekDays> weekDays;

    private String startHour;

    private String endHour;

    boolean active = true;
}