package springsecurity.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springsecurity.demo.entity.TimeTable;

import java.util.List;
import java.util.UUID;

public interface TimetableRepository extends JpaRepository<TimeTable, UUID> {

}
