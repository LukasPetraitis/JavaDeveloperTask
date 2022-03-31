package lt.meetingApp.meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lt.meetingApp.fixedValues.Category;
import lt.meetingApp.fixedValues.Type;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Meeting {
	
	private Integer id;
	private String name;
	private Integer responsiblePersonId;
	private String description;
	private Category category;
	private Type type;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;
	private List<Integer> employeesAttending;
	
	public Meeting(
			Integer id, 
			String name, 
			Integer responsiblePersonId, 
			String description, 
			Category category,
			Type type, 
			LocalDateTime startDate, 
			LocalDateTime endDate) {
		super();
		this.id = id;
		this.name = name;
		this.responsiblePersonId = responsiblePersonId;
		this.description = description;
		this.category = category;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
		employeesAttending = new ArrayList<Integer>();
	}
	
	public void addEmployee(Integer employeeId) {
		employeesAttending.add(employeeId);
	}
	
}
