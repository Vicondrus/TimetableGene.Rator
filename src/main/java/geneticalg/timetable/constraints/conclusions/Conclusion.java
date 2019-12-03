package geneticalg.timetable.constraints.conclusions;

import java.util.List;

import geneticalg.timetable.entities.Course;

public interface Conclusion {

	Long checkConclusion(List<Course> timetable);
	
}
