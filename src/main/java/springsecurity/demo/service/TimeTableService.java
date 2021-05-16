package springsecurity.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsecurity.demo.entity.WeekDays;
import springsecurity.demo.repository.GroupRepository;
import springsecurity.demo.repository.TimetableRepository;
import springsecurity.demo.repository.WeekDaysRepository;

@Service
public class TimeTableService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TimetableRepository timetableRepository;

    @Autowired
    WeekDaysRepository weekDaysRepository;




}
