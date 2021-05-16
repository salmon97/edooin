package springsecurity.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import springsecurity.demo.entity.Subject;
import springsecurity.demo.entity.WeekDays;
import springsecurity.demo.projection.CustomWeekDays;

import java.util.List;
import java.util.UUID;


@CrossOrigin
@RepositoryRestResource(path = "week", collectionResourceRel = "list", excerptProjection = CustomWeekDays.class)
public interface WeekDaysRepository extends JpaRepository<WeekDays, Integer> {

    @Query(value = "select wk.* from week_days wk, groups_week_days gr  where wk.id = gr.week_days_id and gr.groups_id = :id", nativeQuery = true)
    List<WeekDays> getWeekDaysByGroupId(UUID id);
}