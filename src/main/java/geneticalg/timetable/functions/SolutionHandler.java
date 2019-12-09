package geneticalg.timetable.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import geneticalg.timetable.constraints.Constraint;
import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.WeekDay;
import geneticalg.timetable.genetic.CourseGene;
import io.jenetics.Genotype;

public class SolutionHandler {

	public static void handleResult(Genotype<CourseGene> result) {
		List<Course> finalmente = new ArrayList<Course>();
		result.getChromosome().forEach(x -> finalmente.add(x.getAllele()));
		Map<Group, List<Course>> map1 = finalmente.stream().collect(Collectors.groupingBy(Course::getGroup));
		map1.entrySet().stream().forEach(x -> timetableForGroup(x.getKey(), x.getValue()));
		Map<Teacher, List<Course>> map2 = finalmente.stream().collect(Collectors.groupingBy(Course::getTeacher));
		map2.entrySet().stream().forEach(x -> timetableForTeacher(x.getKey(), x.getValue()));
		System.out.println("Unresolved: ");
		map1.entrySet().stream().forEach(x -> printUnresolvedForGroup(x.getKey(), x.getValue()));
		map2.entrySet().stream().forEach(x -> printUnresolvedForTeacher(x.getKey(), x.getValue()));
	}
	
	public static void printUnresolvedForTeacher(Teacher teacher, List<Course> timetable) {
		System.out.print("Teacher " + teacher.getName() + ": ");
		for(Constraint c : teacher.getConstraints()) {
			if(c.checkConstraint(timetable) > 0)
				System.out.print(c.getClass().getSimpleName() + " ");
		}
		System.out.println();
	}
	
	public static void printUnresolvedForGroup(Group group, List<Course> timetable) {
		System.out.print("Group " + group.getNumber() + ": ");
		for(Constraint c : group.getConstraints()) {
			if(c.checkConstraint(timetable) > 0)
				System.out.print(c.getClass().getSimpleName() + " ");
		}
		System.out.println();
	}

	public static void timetableForGroup(Group group, List<Course> timetable) {
		System.out.print(String.format("%20s", group.getNumber()));
		for (int i = Course.getMinHour(); i <= Course.getMaxHour(); i++) {
			System.out.print(String.format("|%20d|", i));
		}
		System.out.println();
		for (int i = 0; i < WeekDay.values().length-1; i++) {
			System.out.print(String.format("%20s|", WeekDay.values()[i]));
			WeekDay z = WeekDay.values()[i];
			List<Course> q = timetable.stream().filter(x -> x.getDay().equals(z)).collect(Collectors.toList());
			if (!q.isEmpty()) {
				for (int j = Course.getMinHour(); j <= Course.getMaxHour(); j++) {
					if (q != null) {
						int y = j;
						Course c = q.stream().filter(x -> x.getHour() == y).findAny().orElse(null);
						if (c != null)
							System.out.print(String.format("%5s %15s|", c.getRoom(), c.getTeacher().getName()));
						else {
							System.out.print(String.format("%21s|", ""));
						}
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void timetableForTeacher(Teacher teacher, List<Course> timetable) {
		System.out.print(String.format("%20s", teacher.getName()));
		for (int i = Course.getMinHour(); i <= Course.getMaxHour(); i++) {
			System.out.print(String.format("|%20d|", i));
		}
		System.out.println();
		for (int i = 0; i < WeekDay.values().length-1; i++) {
			System.out.print(String.format("%20s|", WeekDay.values()[i]));
			WeekDay z = WeekDay.values()[i];
			List<Course> q = timetable.stream().filter(x -> x.getDay().equals(z)).collect(Collectors.toList());
			if (!q.isEmpty()) {
				for (int j = Course.getMinHour(); j <= Course.getMaxHour(); j++) {
					if (q != null) {
						int y = j;
						Course c = q.stream().filter(x -> x.getHour() == y).findAny().orElse(null);
						if (c != null)
							System.out.print(String.format("%5s %15s|", c.getRoom(), c.getGroup().getNumber()));
						else {
							System.out.print(String.format("%21s|", ""));
						}
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}

}
