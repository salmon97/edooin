package springsecurity.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springsecurity.demo.entity.Student;
import springsecurity.demo.projection.MoneyFinish;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Transactional
public interface StudentRepository extends JpaRepository<Student, UUID> {

    Student findByUser_Id(UUID user_id);
//
//    @Query(value = "select * from student s , students_groups st where s.id =st.student_id and st.group_id = :id", nativeQuery = true)
//    List<Student> getStudentByGroupId(UUID id);

    @Query(value = "select count(*) from users u , student s where s.user_id = u.id and u.edu_center_id = :id", nativeQuery = true)
    Integer getStudentCount(Integer id);

    @Query(value = "select s.* from student s , users u where s.user_id = u.id and u.identification_code = :login and s.password = :password", nativeQuery = true)
    Student getStudentByLoginAndPassword(String login, String password);

    @Query(value = "select exists (select s.* from student s , users u where s.user_id = u.id and u.identification_code = :login and s.password = :password)", nativeQuery = true)
    boolean checkLoginAndPassword(String login, String password);

    Page<Student> findAllByUser_EduCenter_Id(Integer user_eduCenter_id, Pageable pageable);


    @Query(value = "select u.first_name , u.last_name ,st.balance, g.name as group_name " +
            " from users u, groups g , student s , st_join_group st where g.edu_center_id =:eduCenterId and " +
            " u.id = s.user_id and " +
            " st.group_id = g.id and s.id = st.student_id and st.balance <= g.price order by g.price asc ", nativeQuery = true)
    List<MoneyFinish> getMoneyFinish(int eduCenterId);
}

