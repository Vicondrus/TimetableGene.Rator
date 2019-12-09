package geneticalg.timetable.constraints;

import java.util.List;

import geneticalg.timetable.entities.Course;

public interface Constraint {
	
	//TODO
	//Add for each teacher affiliations constraints	//ADDED
	//Add disjoint rooms for courses constraints	//ADDED
	//No gaps for groups //ADDED and with or without gaps for teachers	//ADDED
	//All days start at eight	//Trying
	
	//Try to eliminate inviable individuals
	//Maybe check from beginning when a timetable cannot be created
	//Test multiplicative penalties
	//Try different representation: Cartesian product of each course field + bit string to tell which is taken
	//OR modify crossover to modify inside course
	//start from existing timetable and find minimum number of changes to get to a new configuration
	
	
	int HARD_CONSTRAINT = 10;
	int SOFT_CONSTRAINT = 2;

	Long checkConstraint(List<Course> timetable);
	
}
