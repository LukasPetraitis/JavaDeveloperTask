package lt.meetingApp.meeting;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("meeting")
@AllArgsConstructor
public class MettingController {

	MeetingService meetingService;
	
	@PostMapping("/new")
	public ResponseEntity<String> createNewMeeting(
			@RequestBody MeetingDTO meetingDTO){
		
		if(meetingDTO == null || meetingDTO.getResponsiblePersonId() == null ) {
			return new ResponseEntity<String>("Meeting was not created", HttpStatus.BAD_REQUEST);
		}
		
		boolean isCreated = meetingService.createMeeting(meetingDTO);
		
		if(isCreated == true) {
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
	
	
	
}
