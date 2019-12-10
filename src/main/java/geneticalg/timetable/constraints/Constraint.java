package geneticalg.timetable.constraints;

import java.util.List;

import geneticalg.timetable.constraints.penalties.ExponentialPenalty;
import geneticalg.timetable.constraints.penalties.MultiplicativePenalty;
import geneticalg.timetable.constraints.penalties.PenaltyStrategy;
import geneticalg.timetable.entities.Course;

public interface Constraint {
	
	//TODO
	//Add for each teacher affiliations constraints	//ADDED
	//Add disjoint rooms for courses constraints	//ADDED
	//No gaps for groups //ADDED and with or without gaps for teachers	//ADDED
	//All days start at eight	//Trying
	
	//Try to eliminate inviable individuals
	//Maybe check from beginning when a timetable cannot be created
	//Test multiplicative penalties	//ADDED
	//Try different representation: Cartesian product of each course field + bit string to tell which is taken
	//OR modify crossover to modify inside course	//BAD IDEEA
	//start from existing timetable and find minimum number of changes to get to a new configuration
	
	
	int HARD_CONSTRAINT = 10;
	int SOFT_CONSTRAINT = 2;
	PenaltyStrategy PENALTY = new ExponentialPenalty();

	Long checkConstraint(List<Course> timetable);
	
}
