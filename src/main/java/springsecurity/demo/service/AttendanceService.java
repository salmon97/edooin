package springsecurity.demo.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import springsecurity.demo.entity.Attendance;
import springsecurity.demo.entity.StJoinGroup;
import springsecurity.demo.payload.ReqServiceStudent;
import springsecurity.demo.payload.ResAttendance;
import springsecurity.demo.payload.ResStudent;
import springsecurity.demo.repository.AttendanceRepository;
import springsecurity.demo.repository.StJoinGroupRepository;

@Service
public class AttendanceService {

    final
    AttendanceRepository attendanceRepository;

    final
    StJoinGroupRepository stJoinGroupRepository;

    final
    StudentService studentService;

    public AttendanceService(AttendanceRepository attendanceRepository, StJoinGroupRepository stJoinGroupRepository, StudentService studentService) {
        this.attendanceRepository = attendanceRepository;
        this.stJoinGroupRepository = stJoinGroupRepository;
        this.studentService = studentService;
    }


    public ResAttendance relationToLesson(ReqServiceStudent reqServiceStudent) {
        Attendance attendance = attendanceRepository.findById(reqServiceStudent.getAttendanceId()).orElseThrow(() -> new ResourceNotFoundException("getAttendanceID"));
        attendance.setRelationToLesson(reqServiceStudent.isRelationToLesson());
        Attendance save = attendanceRepository.save(attendance);
        return new ResAttendance(save.getId(), save.isRelationToLesson());
    }

    public void attend(ReqServiceStudent reqServiceStudent) {
        Attendance attendanceByStudent = attendanceRepository.getAttendanceByStudent(reqServiceStudent.getStJoinGroupId());
        StJoinGroup stJoinGroup = stJoinGroupRepository.findByStudent_IdAndGroup_Id(reqServiceStudent.getStudentId(), reqServiceStudent.getGroupId());
        if (attendanceByStudent == null) {
            attendanceByStudent = new Attendance();
            attendanceByStudent.setStJoinGroup(stJoinGroup);
            attendanceByStudent.setTodayInLesson(reqServiceStudent.getTodayInLesson());
        } else {
            attendanceByStudent.setTodayInLesson(!attendanceByStudent.isExplicable() ? reqServiceStudent.getTodayInLesson() : false);
//            attendanceByStudent.setTodayPayed(attendanceByStudent.isExplicable());
//            attendanceByStudent.setExplicable(reqServiceStudent.isExplicable());
//            attendanceByStudent.setTodayInLesson(!attendanceByStudent.isExplicable() ? reqServiceStudent.getTodayInLesson() : false);
        }
        if (!attendanceByStudent.isTodayPayed() && !attendanceByStudent.isExplicable()) {
            takeMoney(stJoinGroup);
            attendanceByStudent.setTodayPayed(true);
        }
        attendanceRepository.save(attendanceByStudent);
    }


    public ResStudent explicable(ReqServiceStudent reqServiceStudent) {
        Attendance attendanceByStudent = attendanceRepository.getAttendanceByStudent(reqServiceStudent.getStJoinGroupId());
        StJoinGroup stJoinGroup = stJoinGroupRepository.findByStudent_IdAndGroup_Id(reqServiceStudent.getStudentId(), reqServiceStudent.getGroupId());
        if (attendanceByStudent == null) {
            attendanceByStudent = new Attendance();
            attendanceByStudent.setStJoinGroup(stJoinGroup);
            attendanceByStudent.setExplicable(reqServiceStudent.isExplicable());
        } else {
            attendanceByStudent.setExplicable(reqServiceStudent.isExplicable());
            attendanceByStudent.setTodayInLesson(false);
            attendanceByStudent.setRelationToLesson(false);
        }
        if (attendanceByStudent.isTodayPayed()) {
            cancelTakeMoney(stJoinGroup);
            attendanceByStudent.setTodayPayed(false);
        }
//        attendanceByStudent.setTodayPayed();
        attendanceRepository.save(attendanceByStudent);
//        attendanceByStudent.setStJoinGroup(stJoinGroup);
        return studentService.getStudentForTeachTemp(stJoinGroup);
    }

    public void takeMoney(StJoinGroup stJoinGroup) {
        stJoinGroup.setBalance(stJoinGroup.getBalance() - stJoinGroup.getPrice());
        stJoinGroupRepository.save(stJoinGroup);
    }

    public void cancelTakeMoney(StJoinGroup stJoinGroup) {
        stJoinGroup.setBalance(stJoinGroup.getBalance() + stJoinGroup.getPrice());
        stJoinGroupRepository.save(stJoinGroup);
    }

}
