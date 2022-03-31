package lt.meetingApp.meeting;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lt.meetingApp.employee.Employee;
import lt.meetingApp.employee.EmployeeService;
import lt.meetingApp.fixedValues.EmployeeStatus;

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
		
		if(meetingRepository.meetingExists(meetingId) && employee != null) {
			return meetingRepository.addEmployeeToMeeting(meetingId, employeeId);
		}
		
		return EmployeeStatus.ERROR;
	}

	
	
	
}
