package lt.meetingApp.meeting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class MeetingRepository {
	
	public boolean saveMeeting(String meetingJSON) {
		
		File file = new File("meetings.txt");
		
		try {
			Writer writer = 
					new BufferedWriter( new FileWriter(file, true));
			
			writer.write(meetingJSON + "\n");
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public List<Meeting> getAllMeetings() {
		List<Meeting> meetings = new ArrayList<>();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		
		File file = new File("meetings.txt");
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
			String line;
			while(( line = bufferedReader.readLine()) != null) {
				
				Meeting meeting = objectMapper.readValue(line, Meeting.class);
				
				System.out.println(meeting.toString());
				meetings.add(meeting);
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return meetings;
	}

	public boolean deleteMeeting(Integer meetingId) {
		
		List<Meeting> meetings = getAllMeetings();
		
		meetings.removeIf(m -> m.getId().equals(meetingId));
		
		File file = new File("meetings.txt");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		
		try {
			Writer writer = 
					new BufferedWriter( new FileWriter(file));
			for(Meeting meeting : meetings) {
				
				String meetingJSON = objectMapper.writeValueAsString(meeting);
			
				writer.write(meetingJSON + "\n");
			}
				
				writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}

	public Meeting getMeetingById(Integer meetingId) {
		List<Meeting> meetings = getAllMeetings();
		
		Meeting meeting = meetings.stream()
				.filter(m -> m.getId()
				.equals(meetingId))
				.findFirst()
				.get();
		
		return meeting;
	}
	
}
