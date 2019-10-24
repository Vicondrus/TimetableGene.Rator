package geneticalg.timetable.constraints;

import java.util.List;

import geneticalg.timetable.entities.Course;

public interface Constraint {

	Long checkConstraint(List<Course> timetable);
	
}
