package lt.meetingApp.meeting;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lt.meetingApp.employee.EmployeeService;

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
	
	public Meeting produceMeeting(MeetingDTO meetingDTO) {

		Meeting meeting = new Meeting(
				meetingDTO.getName(), 
				meetingDTO.getResponsiblePersonId(),
				meetingDTO.getDescription(),
				meetingDTO.getCategory(), 
				meetingDTO.getType(), 
				meetingDTO.getStartDate(), 
				meetingDTO.getEndDate() );
		
		return meeting;
	}
	
	
}
