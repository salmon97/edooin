package springsecurity.demo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsecurity.demo.entity.User;
import springsecurity.demo.payload.ApiResponse;
import springsecurity.demo.payload.ReqEduCenter;
import springsecurity.demo.repository.GroupRepository;
import springsecurity.demo.repository.StudentRepository;
import springsecurity.demo.repository.TeacherRepository;
import springsecurity.demo.security.CurrentUser;
import springsecurity.demo.service.EduCenterService;

@RestController
@RequestMapping("/api")
public class EduCenterController {

    final
    EduCenterService eduCenterService;

    final
    StudentRepository studentRepository;

    final
    GroupRepository groupRepository;

    final
    TeacherRepository teacherRepository;


    public EduCenterController(EduCenterService eduCenterService, StudentRepository studentRepository, GroupRepository groupRepository, TeacherRepository teacherRepository) {
        this.eduCenterService = eduCenterService;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
    }


    @GetMapping("getCount")
    public HttpEntity<?> getInfo(@CurrentUser User user) {
        Integer id = user.getEduCenter().getId();
        int[] counts = {studentRepository.getStudentCount(id), groupRepository.getCountGroup(id), teacherRepository.getTeacherCount(id)};
        return ResponseEntity.ok(new ApiResponse("ok", true, counts));
    }

    @PostMapping("/admin/addEdu")
    public HttpEntity<?> add(@RequestBody ReqEduCenter reqEduCenter) {
        ApiResponse apiResponse = eduCenterService.addEduCenter(reqEduCenter);
        return ResponseEntity.ok(apiResponse);
    }
}
