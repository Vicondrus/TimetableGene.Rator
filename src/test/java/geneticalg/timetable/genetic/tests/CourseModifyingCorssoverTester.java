package geneticalg.timetable.genetic.tests;

import geneticalg.timetable.genetic.CourseModifyingCrossover;
import geneticalg.timetable.genetic.TimetableChromosome;

public class CourseModifyingCorssoverTester {

	public static void main(String[] args) {
		TimetableChromosome c1 = (TimetableChromosome) new TimetableChromosome(null).newInstance();
		TimetableChromosome c2 = (TimetableChromosome) new TimetableChromosome(null).newInstance();
		CourseModifyingCrossover cmc = new CourseModifyingCrossover(0.5,0.5,2);
		//c1.get
		//CourseModifyingCrossover.crossover(c1,c2);
	}
}
