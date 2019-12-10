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
				sum += PENALTY.penalize(HARD_CONSTRAINT, x.getHours());
			} else {
				if (x.getHours() - map.get(x.getTeacher()) != 0)
					sum +=  PENALTY.penalize(HARD_CONSTRAINT, new Long(x.getHours() - map.get(x.getTeacher())).intValue());
							
				map.remove(x.getTeacher());
			}
		}
		Long y = map.entrySet().stream().collect(Collectors.summingLong(x -> x.getValue()));
		if (y != 0)
			sum += sum += PENALTY.penalize(HARD_CONSTRAINT, y.intValue());
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
