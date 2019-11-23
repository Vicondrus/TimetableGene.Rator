package geneticalg.timetable.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.WeekDay;
import geneticalg.timetable.genetic.CourseGene;
import io.jenetics.Genotype;

public class SolutionHandler {

	public static void handleResult(Genotype<CourseGene> result) {
		List<Course> finalmente = new ArrayList<Course>();
		result.getChromosome().forEach(x -> finalmente.add(x.getAllele()));
		Map<Group, List<Course>> map = finalmente.stream().collect(Collectors.groupingBy(Course::getGroup));
		map.entrySet().stream().forEach(x -> timetableForGroup(x.getKey(), x.getValue()));
	}

	public static void timetableForGroup(Group group, List<Course> timetable) {
		System.out.print(String.format("%20s", group.getNumber()));
		for (int i = Course.getMinHour(); i <= Course.getMaxHour(); i++) {
			System.out.print(String.format("|%20d|", i));
		}
		System.out.println();
		for (int i = 0; i < WeekDay.values().length; i++) {
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
	}

}
