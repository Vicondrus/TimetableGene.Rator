package geneticalg.timetable.constraints;

import java.util.List;

import geneticalg.timetable.entities.Course;

public interface Constraint {

	Integer checkConstraint(List<Course> timetable);
	
}
