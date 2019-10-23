package geneticalg.timetable.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jenetics.Genotype;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.genetic.CourseGene;
import geneticalg.timetable.genetic.TimetableChromosome;

public class Fitness {

	private static Long checkFitnessOneChromosome(Genotype<CourseGene> genotype) {
		TimetableChromosome c = genotype.getChromosome().as(TimetableChromosome.class);
		c.stream().forEach(x -> {
			Course course = x.getAllele();
			course.getTeacher().getTimetable().add(course);
			course.getGroup().getTimetable().add(course);
		});
		Long sum = 0L;
		sum += Teacher.getTeachers().stream().collect(Collectors.summingLong(x -> x.checkConstraints()));
		sum += Group.getGroups().stream().collect(Collectors.summingLong(x -> x.checkConstraints()));
		return sum;
	}

	//// TODO
	// very interseting - a genotype has multiple chromosome
	// so it may be useful to have one chromosome for one groupe
	// meaning that the timetable of a week for a group is held
	// in a chromsome -> eliminates mixing up the groups
	private static Long checkFitnessMultipleChromosomesIndividualGroups(Genotype<CourseGene> genotype) {
		List<TimetableChromosome> chs = new ArrayList<TimetableChromosome>();
		for (int i = 0; i < genotype.length(); i++)
			chs.add((TimetableChromosome) genotype.getChromosome(i));

		return null;
	}

}
