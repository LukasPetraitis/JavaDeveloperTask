package lt.meetingApp.meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lt.meetingApp.employee.Employee;
import lt.meetingApp.employee.EmployeeService;
import lt.meetingApp.fixedValues.EmployeeStatus;
import lt.meetingApp.fixedValues.Filter;

@Service
@AllArgsConstructor
public class MeetingService {
	
	MeetingRepository meetingRepository;
	
	EmployeeService employeeService;
	
	public boolean createMeeting(MeetingDTO meetingDTO) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		
		String meetingJSON;
		
		Meeting meeting = produceMeeting(meetingDTO);
		
		try {
			meetingJSON  = objectMapper.writeValueAsString(meeting);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return false;
		}
		
		return meetingRepository.saveMeeting(meetingJSON);
	}
	
	public List<Meeting> getAllMeetings(){
		return meetingRepository.getAllMeetings();
	}
	
	public boolean deleteMeeting(Integer meetingId, Integer employeeId) {
		
		Employee employee = employeeService.getEmployeeById(employeeId);
		
		Meeting meeting = meetingRepository.getMeetingById(meetingId);
		
		
			
		if(employee != null && meeting != null) {
			if(meeting.getResponsiblePersonId().equals(employee.getId())) {
				meetingRepository.deleteMeeting(meetingId);
				return true;
			}
		}
		return false;
	}
	
	public Meeting produceMeeting(MeetingDTO meetingDTO) {

		Meeting meeting = new Meeting(
				meetingDTO.getId(),
				meetingDTO.getName(), 
				meetingDTO.getResponsiblePersonId(),
				meetingDTO.getDescription(),
				meetingDTO.getCategory(), 
				meetingDTO.getType(), 
				meetingDTO.getStartDate(), 
				meetingDTO.getEndDate() );
		
		return meeting;
	}

	public EmployeeStatus addEmployeeToMeeting(Integer meetingId, Integer employeeId) {
		
		Employee employee = employeeService.getEmployeeById(employeeId);
		
		if( employee == null) {
			return EmployeeStatus.ERROR;
		}
		
		Meeting meeting =  getAllMeetings()
				.stream()
				.filter(m -> m.getId()
				.equals(meetingId))
				.findFirst().get();
		if(isPersonInMeeting(employee, meeting)) {
			return EmployeeStatus.OCCUPIED;
		}
		if(meeting.getEmployeesAttending().contains(employeeId)) {
			return EmployeeStatus.ALREADYADDED;
		}
		
		return meetingRepository.addEmployeeToMeeting(meeting, employeeId);		
	}

	public boolean removeEmployeeFromMeeting(Integer meetingId, Integer employeeId) {
		
		return meetingRepository.removeEmployeeFromMeeting(meetingId, employeeId);	
	}

	private boolean isPersonInMeeting(Employee employee, Meeting meeting) {
		List<Meeting> meetings = getAllMeetings();
		
		meetings = meetings.stream()
				.filter(m -> m.getEmployeesAttending().contains(employee.getId()))
				.collect(Collectors.toList());
		
		for(Meeting m : meetings) {
			if(meeting.getStartDate().isBefore(m.getEndDate()) && meeting.getStartDate().isBefore(m.getStartDate())) {
				continue;
			}
			else if(meeting.getEndDate().isAfter(m.getEndDate()) && meeting.getEndDate().isAfter(m.getStartDate())){
				continue;
			}
			else {
				return true;
			}
		}
		return false;
	}

	public List<Meeting> getMeetingsByFilter(FilterDTO filterDTO) {
		
		List<Meeting> meetings = getAllMeetings();
		
		switch(filterDTO.getFilter()) {
		case DESCRIPTION:
			return meetings = meetings.stream()
				.filter(m -> m.getDescription().equals(filterDTO.getByWhat()))
				.collect(Collectors.toList());
		case RESPONSIBLE_PERSON:
			return meetings = meetings.stream()
				.filter(m -> m.getResponsiblePersonId().equals((Integer.parseInt(filterDTO.getByWhat()))))
				.collect(Collectors.toList());
		case ATTENDEES:
			return meetings = meetings.stream()
				.filter(m ->  m.getEmployeesAttending().size() >= (Integer.parseInt(filterDTO.getByWhat())))
				.collect(Collectors.toList());
		}
		return new ArrayList<Meeting>();
	}

	
	
	
}
