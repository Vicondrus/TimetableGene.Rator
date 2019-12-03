package geneticalg.timetable.constraints.prerequsites;

import java.util.List;

import geneticalg.timetable.entities.Course;

public interface Prerequisite {

	boolean checkPrerequsite(List<Course> timetable);
	
}
