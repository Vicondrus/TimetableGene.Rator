package geneticalg.timetable.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.TeacherAmount;

public class GroupWeeklyConstraint implements Constraint {

	private List<TeacherAmount> necessaryHours = new ArrayList<TeacherAmount>();

	public void addToNecessaryHours(TeacherAmount ta) {
		necessaryHours.add(ta);
	}

	@Override
	public Long checkConstraint(List<Course> timetable) {
		Map<Teacher, Long> map = timetable.stream()
				.collect(Collectors.groupingBy(Course::getTeacher, Collectors.counting()));
		Long sum = 0L;
		for (TeacherAmount x : necessaryHours) {
			if (!map.containsKey(x.getTeacher())) {
				sum += new Double(Math.pow(3.0, x.getHours())).longValue();
			} else {
				sum += new Double(Math.pow(3.0, Math.abs(x.getHours() - map.get(x.getTeacher())))).longValue();
				map.remove(x.getTeacher());
			}
		}
		sum += new Double(Math.pow(3.0, map.entrySet().stream().collect(Collectors.summingLong(x -> x.getValue()))))
				.longValue();
		return sum;
	}

}
