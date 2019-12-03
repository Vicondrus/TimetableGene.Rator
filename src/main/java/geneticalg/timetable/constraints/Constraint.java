package geneticalg.timetable.constraints;

import java.util.List;

import geneticalg.timetable.entities.Course;

public interface Constraint {
	
	//TODO
	//Add for each teacher affiliations constraints	//ADDED
	//Add disjoint rooms for courses constraints	//ADDED
	//No gaps for groups //ADDED and with or without gaps for teachers	//MUST BE TESTED in GENETIC
	//All days start at eight	//Trying
	
	int HARD_CONSTRAINT = 10;
	int SOFT_CONSTRAINT = 2;

	Long checkConstraint(List<Course> timetable);
	
}
