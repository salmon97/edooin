package springsecurity.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springsecurity.demo.entity.Subject;

import java.util.List;
import java.util.UUID;

//@CrossOrigin
//@RepositoryRestResource(path = "subject", collectionResourceRel = "list", excerptProjection = CustomSubject.class)
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    @Query(value = "select s.* from subject s, groups_subjects gr  where s.id = gr.subject_id and gr.group_id = :id", nativeQuery = true)
    List<Subject> getAllByGroupId(UUID id);

    List<Subject> findAllByEduCenter_Id(Integer eduCenter_id);
}