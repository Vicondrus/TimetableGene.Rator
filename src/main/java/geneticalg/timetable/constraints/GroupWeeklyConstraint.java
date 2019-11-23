package geneticalg.timetable.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.auxiliary.TeacherAmount;

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
				sum += new Double(Math.pow(HARD_CONSTRAINT, x.getHours())).longValue();
			} else {
				if (x.getHours() - map.get(x.getTeacher()) != 0)
					sum += new Double(Math.pow(HARD_CONSTRAINT, Math.abs(x.getHours() - map.get(x.getTeacher())))).longValue();
				map.remove(x.getTeacher());
			}
		}
		Long y = map.entrySet().stream().collect(Collectors.summingLong(x -> x.getValue()));
		if (y != 0)
			sum += new Double(Math.pow(HARD_CONSTRAINT, y)).longValue();
		return sum;
	}

	public Integer getTotalHours() {
		return necessaryHours.stream().collect(Collectors.summingInt(TeacherAmount::getHours));
	}

	public List<TeacherAmount> getNecessaryHours() {
		return necessaryHours;
	}

	public void setNecessaryHours(List<TeacherAmount> necessaryHours) {
		this.necessaryHours = necessaryHours;
	}

}
