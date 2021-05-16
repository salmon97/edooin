package springsecurity.demo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import springsecurity.demo.entity.*;
import springsecurity.demo.payload.*;
import springsecurity.demo.repository.*;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {

    final
    GroupRepository groupRepository;

    final
    TeacherRepository teacherRepository;

    final
    EduCenterRepository eduCenterRepository;

    final
    SubjectRepository subjectRepository;

    final
    StudentRepository studentRepository;

    final
    StudentService studentService;

    final
    StJoinGroupRepository stJoinGroupRepository;

    final
    WeekDaysRepository weekDaysRepository;

    public GroupService(GroupRepository groupRepository, TeacherRepository teacherRepository, EduCenterRepository eduCenterRepository, SubjectRepository subjectRepository, StudentRepository studentRepository, StudentService studentService, StJoinGroupRepository stJoinGroupRepository, WeekDaysRepository weekDaysRepository) {
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.eduCenterRepository = eduCenterRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.stJoinGroupRepository = stJoinGroupRepository;
        this.weekDaysRepository = weekDaysRepository;
    }


//    public boolean openGroup(UUID id) throws ParseException {
//        Group getGroup = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getGroup"));
//        Calendar cal = Calendar.getInstance();
//        int day = cal.get(Calendar.DAY_OF_WEEK);
//        Date date = new Date();
//        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
//        String format = parser.format(date);
//        String hour = getGroup.getWeekAndHour().get(dayWeekName(day));
//        Date openHour = parser.parse(hour);
//        Date now = parser.parse(format);
//        if (getGroup.isOpenGroup()) {
//            getGroup.setOpenGroup(false);
//            return true;
//        }
//        if (hour != null && openHour.before(now)) {
//            getGroup.setOpenGroup(true);
//            return true;
//        }
//        groupRepository.save(getGroup);
//        return false;
//    }
//
//    public String dayWeekName(int week) {
//        switch (week) {
//            case Calendar.MONDAY:
//                return "Monday";
//            case Calendar.TUESDAY:
//                return "Tuesday";
//            case Calendar.WEDNESDAY:
//                return "Wednesday";
//            case Calendar.THURSDAY:
//                return "Thursday";
//            case Calendar.FRIDAY:
//                return "Friday";
//            case Calendar.SATURDAY:
//                return "Saturday";
//            case Calendar.SUNDAY:
//                return "Sunday";
//            default:
//                return "noDay";
//        }
//    }

    public ApiResponse deleteChild(UUID groupId, Object childId) {
        Optional<Group> byId = groupRepository.findById(groupId);
        if (byId.isPresent()) {
            boolean b;
            try {
                Integer id = Integer.valueOf(String.valueOf(childId));
                if (weekDaysRepository.existsById(id)) {
                    b = byId.get().getWeekDays().removeIf(weekDays -> weekDays.getId().equals(id));
//                    byId.get().setWeekDays(byId.get().getWeekDays());
                    groupRepository.save(byId.get());
                    return new ApiResponse("weekdays", b, getResWeekDaysByGrId(groupId));
                }
            }catch (Exception e){
                UUID uuid = UUID.fromString(String.valueOf(childId));
                if (subjectRepository.existsById(uuid)) {
                    b = byId.get().getSubject().removeIf(subject -> subject.getId().equals(uuid));
//                    byId.get().setSubject(byId.get().getSubject());
                    groupRepository.save(byId.get());
                    return new ApiResponse("subject", b, getSubjectByGrId(groupId));
                }
                if (teacherRepository.existsById(uuid)) {
                    b = byId.get().getTeacher().removeIf(teacher -> teacher.getId().equals(uuid));
//                    byId.get().setTeacher(byId.get().getTeacher());
                    groupRepository.save(byId.get());
                    return new ApiResponse("teacher", b, getTeachersByGrId(groupId));
                }
                if (studentRepository.existsById(uuid)) {
                    stJoinGroupRepository.deleteByStudent_IdAndGroup_Id(uuid, groupId);
                    return new ApiResponse("student", true, studentService.getStudentIntoGrForStaff(groupId));
                }
            }

        }
        return new ApiResponse("no", false);
    }

    public ApiResponse changeGroup(ReqGroup reqGroup) {
        Group getGroup = groupRepository.findById(reqGroup.getId()).orElseThrow(() -> new ResourceNotFoundException("getGroup"));
        if (getGroup.getGroupCode().equals(reqGroup.getGroupCode()) || !groupRepository.existsByGroupCode(reqGroup.getGroupCode())) {
            getGroup.setName(reqGroup.getName());
            getGroup.setDirection(reqGroup.getDirection());
            getGroup.setGroupCode(reqGroup.getGroupCode());
            getGroup.setPrice(reqGroup.getPrice());
            getGroup.setStartHour(Time.valueOf(reqGroup.getStartHour().length() > 6 && reqGroup.getStartHour().endsWith(":00") ? reqGroup.getStartHour() : reqGroup.getStartHour() + ":00"));
            getGroup.setEndHour(Time.valueOf(reqGroup.getEndHour().length() > 6 && reqGroup.getEndHour().endsWith(":00") ? reqGroup.getEndHour() : reqGroup.getEndHour() + ":00"));
            getGroup.setActive(reqGroup.isActive());
//            getGroup.setWeekAndHour(reqGroup.getWeekAndHour());
            stJoinGroupRepository.findAllByGroup_Id(getGroup.getId()).forEach(stJoinGroup -> {
                stJoinGroup.setPrice(getGroup.getPrice());
                stJoinGroupRepository.save(stJoinGroup);
            });
            ApiResponse apiResponse = new ApiResponse("", true);
            if (!reqGroup.getSubjectId().isEmpty()) {
                reqGroup.getSubjectId().forEach(uuid -> getGroup.getSubject().add(subjectRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("getSubject"))));
                apiResponse.setMessage("subject");
                apiResponse.setObject(getSubjectByGrId(getGroup.getId()));
            }
            if (!reqGroup.getTeacherId().isEmpty()) {
                reqGroup.getTeacherId().forEach(uuid -> getGroup.getTeacher().add(teacherRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("getTeacher"))));
                apiResponse.setObject(getTeachersByGrId(getGroup.getId()));
                apiResponse.setMessage("teacher");
            }
            if (!reqGroup.getWeekdaysId().isEmpty()) {
                reqGroup.getWeekdaysId().forEach(integer -> getGroup.getWeekDays().add(weekDaysRepository.findById(integer).orElseThrow(() -> new ResourceNotFoundException("getWeekdays"))));
                apiResponse.setMessage("weekdays");
            }
            groupRepository.save(getGroup);
            return apiResponse;
        }
        return new ApiResponse("like this is groupCode exist in your Groups ", false);
    }

    public UUID deleteGroup(UUID id) {
        groupRepository.deleteById(id);
        return id;
    }


    public ApiResponse addGroup(ReqGroup reqGroup, User user) {

        if (!groupRepository.existsByGroupCode(reqGroup.getGroupCode() + "_" + user.getEduCenter().getName())) {
            EduCenter eduCenter = eduCenterRepository.findById(user.getEduCenter().getId()).orElseThrow(() -> new ResourceNotFoundException("getEdu"));
            Group group = new Group();
            group.setName(reqGroup.getName());
            group.setDirection(reqGroup.getDirection());
            group.setGroupCode(reqGroup.getGroupCode() + "_" + eduCenter.getName());
            group.setPrice(reqGroup.getPrice());
            group.setEduCenter(eduCenter);
            group.setSubject(subjectList(reqGroup));
            group.setTeacher(teachers(reqGroup));
            group.setWeekDays(weekdays(reqGroup));
            group.setStartHour(Time.valueOf(reqGroup.getStartHour().length() > 6 && reqGroup.getStartHour().endsWith(":00") ? reqGroup.getStartHour() : reqGroup.getStartHour() + ":00"));
            group.setEndHour(Time.valueOf(reqGroup.getEndHour().length() > 6 && reqGroup.getEndHour().endsWith(":00") ? reqGroup.getEndHour() : reqGroup.getEndHour() + ":00"));
            group.setActive(reqGroup.isActive());
            Group save = groupRepository.save(group);
            return new ApiResponse("ok", true, getGroup(save));
        }
        return new ApiResponse("like this is groupCode exist in your Groups ", false);
    }

    //Find by Id teachers which Staff choose
    public Set<Teacher> teachers(ReqGroup reqGroup) {
        Set<Teacher> teacherList = new HashSet<>();
        for (UUID uuid : reqGroup.getTeacherId()) {
            Teacher teacher1 = teacherRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("getTeacher"));
            teacherList.add(teacher1);
        }
        return teacherList;
    }

    //Find by Id Weekdays which Staff choose
    public Set<WeekDays> weekdays(ReqGroup reqGroup) {
        Set<WeekDays> weekDaysSet = new HashSet<>();
        for (Integer id : reqGroup.getWeekdaysId()) {
            WeekDays weekDays = weekDaysRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Weekdays"));
            weekDaysSet.add(weekDays);
        }
        return weekDaysSet;
    }

    //Find by Id subjects which Staff choose
    public Set<Subject> subjectList(ReqGroup reqGroup) {
        Set<Subject> subjects = new HashSet<>();
        for (UUID uuid : reqGroup.getSubjectId()) {
            Subject subject = subjectRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("getSubject"));
            subjects.add(subject);
        }
        return subjects;
    }

    public List<ResSubject> getSubjectByGrId(UUID id) {
        List<Subject> allByGroupId = subjectRepository.getAllByGroupId(id);
        List<ResSubject> resSubjects = new ArrayList<>();
        for (Subject subject : allByGroupId) {
            resSubjects.add(new ResSubject(subject.getId(), subject.getName()));
        }
        return resSubjects;
    }

    public ResPageable getStaffGroup(int page, int size, User user) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Group> byEduCenterId = groupRepository.findAllByEduCenter_Id(user.getEduCenter().getId(), pageable);
        return new ResPageable(
                page, size,
                byEduCenterId.getTotalElements(),
                byEduCenterId.getTotalPages(),
                byEduCenterId.getContent().stream().map(this::getGroup).collect(Collectors.toList())
        );
    }

    public List<ResTeacher> getTeachersByGrId(UUID id) {
        List<Teacher> allByGroupId = teacherRepository.getAllByGroupId(id);
        List<ResTeacher> resTeachers = new ArrayList<>();
        for (Teacher teacher : allByGroupId) {
            resTeachers.add(new ResTeacher(teacher.getId(), teacher.getUser().getFirstName(), teacher.getUser().getLastName()));
        }
        return resTeachers;
    }

    public List<ResWeekdays> getResWeekDaysByGrId(UUID id) {
        List<WeekDays> allByGroupId = weekDaysRepository.getWeekDaysByGroupId(id);
        List<ResWeekdays> resWeekDays = new ArrayList<>();
        for (WeekDays weekDays : allByGroupId) {
            resWeekDays.add(new ResWeekdays(weekDays.getId(), weekDays.getName()));
        }
        return resWeekDays;
    }


    public ApiResponse getGroupForStudent(UUID studentId) {
        List<StJoinGroup> byStudent_id = stJoinGroupRepository.findByStudent_Id(studentId);
        List<ResGroup> collect = byStudent_id.stream().map(stJoinGroup -> new ResGroup(stJoinGroup.getGroup().getId(), stJoinGroup.getGroup().getName(), stJoinGroup.getGroup().getPrice())).collect(Collectors.toList());
        return new ApiResponse("student", true, collect);
    }

    public ResGroup getGroup(Group group) {
        return new ResGroup(
                group.getId(),
                group.getName(),
                group.getGroupCode(),
                group.getDirection(),
                group.getCreatedAt().toString().substring(0, 10),
                group.getPrice(),
                group.isActive(),
                group.getStartHour(),
                group.getEndHour()
        );
    }
}