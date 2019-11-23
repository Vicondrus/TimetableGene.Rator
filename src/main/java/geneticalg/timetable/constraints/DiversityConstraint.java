package geneticalg.timetable.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Affiliation;
import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.WeekDay;
import geneticalg.timetable.entities.auxiliary.TeacherAmount;

public class DiversityConstraint implements Constraint {

	private List<TeacherAmount> necessaryHours = new ArrayList<TeacherAmount>();

	public void addToNecessaryHours(TeacherAmount ta) {
		necessaryHours.add(ta);
	}

	public List<TeacherAmount> getNecessaryHours() {
		return necessaryHours;
	}

	public void setNecessaryHours(List<TeacherAmount> necessaryHours) {
		this.necessaryHours = necessaryHours;
	}

	private Map<Affiliation, Integer> countMinAffiliationHoursPerDay() {
		if (necessaryHours.isEmpty())
			return null;
		Map<Affiliation, Integer> perWeek = necessaryHours.stream().collect(
				Collectors.groupingBy(x -> x.getTeacher().getAffiliation(), Collectors.summingInt(x -> x.getHours())));
		perWeek.forEach((x, y) -> perWeek.put(x, y / 5));
		return perWeek;
	}

	@Override
	public Long checkConstraint(List<Course> timetable) {
		Map<Affiliation, Integer> minAffPerDay = countMinAffiliationHoursPerDay();
		Map<WeekDay, Map<Affiliation, Long>> map = timetable.stream().collect(Collectors.groupingBy(Course::getDay,
				Collectors.groupingBy(x -> x.getTeacher().getAffiliation(), Collectors.counting())));
		Long sum = 0L;
		for (Entry<WeekDay, Map<Affiliation, Long>> e1 : map.entrySet()) {
			for (Entry<Affiliation, Integer> e2 : minAffPerDay.entrySet()) {
				Long act = e1.getValue().get(e2.getKey());
				if(act == null)
					sum += new Double(Math.pow(SOFT_CONSTRAINT, e2.getValue() - 0)).longValue();
				else if (act < e2.getValue())
					sum += new Double(Math.pow(SOFT_CONSTRAINT, e2.getValue() - act)).longValue();
				else if (act > e2.getValue() + 1)
					sum += new Double(Math.pow(SOFT_CONSTRAINT, act - e2.getValue() - 1)).longValue();
			}
		}
		return sum;

	}
}
