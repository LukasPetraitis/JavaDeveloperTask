package lt.meetingApp.meeting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.meetingApp.fixedValues.Filter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {
	
	private Filter filter;
	private String byWhat;
}
