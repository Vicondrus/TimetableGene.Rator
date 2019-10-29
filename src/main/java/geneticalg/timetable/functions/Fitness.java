package geneticalg.timetable.functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jenetics.Genotype;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.genetic.CourseGene;
import geneticalg.timetable.genetic.TimetableChromosome;

public class Fitness {

	public static Long checkFitnessOneChromosome(Genotype<CourseGene> genotype) {
		TimetableChromosome c = genotype.getChromosome().as(TimetableChromosome.class);
		Map<Teacher, List<Course>> teacherTimetables = new HashMap<Teacher, List<Course>>();
		Map<Group, List<Course>> groupTimetables = new HashMap<Group, List<Course>>();
		c.stream().forEach(x -> {
			Course course = x.getAllele();
			if (course != null) {
				if (teacherTimetables.get(course.getTeacher()) == null) {
					teacherTimetables.put(course.getTeacher(), new ArrayList<Course>());
				}
				teacherTimetables.get(course.getTeacher()).add(course);
				if (groupTimetables.get(course.getGroup()) == null) {
					groupTimetables.put(course.getGroup(), new ArrayList<Course>());
				}
				groupTimetables.get(course.getGroup()).add(course);
			} else {
				System.out.println(c);
			}
		});
		Long sum = 0L;
		sum += teacherTimetables.entrySet().stream()
				.collect(Collectors.summingLong(x -> x.getKey().checkConstraints(x.getValue())));
		sum += groupTimetables.entrySet().stream()
				.collect(Collectors.summingLong(x -> x.getKey().checkConstraints(x.getValue())));
		return sum;
	}

	//// TODO
	// very interseting - a genotype has multiple chromosome
	// so it may be useful to have one chromosome for one groupe
	// meaning that the timetable of a week for a group is held
	// in a chromsome -> eliminates mixing up the groups
	public static Long checkFitnessMultipleChromosomesIndividualGroups(Genotype<CourseGene> genotype) {
		List<TimetableChromosome> chs = new ArrayList<TimetableChromosome>();
		for (int i = 0; i < genotype.length(); i++)
			chs.add((TimetableChromosome) genotype.getChromosome(i));

		return null;
	}

}
