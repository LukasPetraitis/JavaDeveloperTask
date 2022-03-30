package lt.meetingApp.employee;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lt.meetingApp.meeting.Meeting;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee {
	
	private Integer id;
	private String firstname;
	private String lastname;
	private List<Meeting> meetings;

	public Employee(
			Integer id, 
			String firstname, 
			String lastname) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
}
