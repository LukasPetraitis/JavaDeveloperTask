package lt.meetingApp.meeting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lt.meetingApp.fixedValues.EmployeeStatus;

@RestController
@RequestMapping("meeting")
@AllArgsConstructor
public class MettingController {

	MeetingService meetingService;
	
	@PostMapping("/new")
	public ResponseEntity<String> createNewMeeting(
			@RequestBody MeetingDTO meetingDTO){
		
		if(meetingDTO.getId() == null || meetingDTO.getResponsiblePersonId() == null || meetingDTO == null) {
			return new ResponseEntity<String>("Meeting was not created", HttpStatus.BAD_REQUEST);
		}
		
		boolean isCreated = meetingService.createMeeting(meetingDTO);
		
		if(isCreated) {
			return new ResponseEntity<String>("Meeting created succesfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Something wenr wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all")
	public List<Meeting> getAllMeetings(){
		return meetingService.getAllMeetings();
	}
	
	@DeleteMapping("/delete/{meetingId}/employee/{employeeId}")
	public ResponseEntity<String> deleteMeeting(
			@PathVariable Integer meetingId, 
			@PathVariable Integer employeeId){
		
		boolean isEmployeeResponsibleForMeeting = 
				meetingService.deleteMeeting(meetingId, employeeId);
		
		if(isEmployeeResponsibleForMeeting) {
			return new ResponseEntity<String>(
					"meeting deleted", 
					HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(
				"meering was not found", 
				HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{meetingId}/addEmployee/{employeeId}")
	public ResponseEntity<String> addEmployeeToMeeting(
			@PathVariable Integer meetingId, 
			@PathVariable Integer employeeId){
		
		EmployeeStatus employeeStatus = meetingService
				.addEmployeeToMeeting(meetingId, employeeId);
		
		if(employeeStatus.equals(EmployeeStatus.ADDED)) {
			return new ResponseEntity<String>("Added employee", HttpStatus.OK);
		}	
		else if(employeeStatus.equals(EmployeeStatus.ALREADYADDED)){
			return new ResponseEntity<String>("Employee already added", HttpStatus.ALREADY_REPORTED);
		}
		else if(employeeStatus.equals(EmployeeStatus.OCCUPIED)) {
			return new ResponseEntity<String>("Employee has another meeting", HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<String>("Something went wrong", HttpStatus.NOT_FOUND);

		}
	}
	
	@PutMapping("/{meetingId}/removeEmployee/{employeeId}")
	public ResponseEntity<String> removeEmployeeFromMeeting(
			@PathVariable Integer meetingId, 
			@PathVariable Integer employeeId){
		
		boolean isRemoved = meetingService.removeEmployeeFromMeeting(meetingId, employeeId);
		
		if(isRemoved) {
			return new ResponseEntity<String>("Employee removed", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("you cannot remove employee responsible for the meeting", HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping("/filter")
	public  List<Meeting> filterMeetings(@RequestBody FilterDTO filterDTO){
		
		return meetingService.getMeetingsByFilter(filterDTO);
		
	}
	
}

