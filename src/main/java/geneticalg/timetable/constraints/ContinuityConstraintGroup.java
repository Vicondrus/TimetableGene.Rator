package geneticalg.timetable.constraints;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.WeekDay;

public class ContinuityConstraintGroup implements Constraint {

	private Integer continuousHoursMax;
	private Integer continuousHoursMin;

	public ContinuityConstraintGroup(Integer continuousHoursMin, Integer continuousHoursMax) {
		super();
		this.continuousHoursMax = continuousHoursMax;
		this.continuousHoursMin = continuousHoursMin;
	}

	public ContinuityConstraintGroup() {
	}

	public Integer getContinuousHoursMax() {
		return continuousHoursMax;
	}

	public void setContinuousHoursMax(Integer continuousHoursMax) {
		this.continuousHoursMax = continuousHoursMax;
	}

	public Integer getContinuousHoursMin() {
		return continuousHoursMin;
	}

	public void setContinuousHoursMin(Integer continuousHoursMin) {
		this.continuousHoursMin = continuousHoursMin;
	}

	@Override
	public Long checkConstraint(List<Course> timetable) {
		Map<WeekDay, List<Course>> map = timetable.stream().collect(Collectors.groupingBy(Course::getDay));
		Long sum = 0L;
		for (Entry<WeekDay, List<Course>> e : map.entrySet()) {
			sum += analyzeDayStartingMorning(e.getValue());
		}
		return sum;
	}

	public Long analyzeDayStartingMorning(List<Course> day) {
		List<Course> sorted = day.stream().sorted(Comparator.comparing(Course::getHour)).collect(Collectors.toList());
		Long sum = 0L;
		int count = 0;
		int hours = Course.getMinHour();
		for (int i = 0; i < sorted.size(); i++) {
			//if(sorted.get(i).getHour() == 8)
				//System.out.println("From 8");
			if (sorted.get(i).getHour() != hours) {
				count += Math.abs(sorted.get(i).getHour() - hours);
			}
			hours++;
		}
		sum += count * HARD_CONSTRAINT;
		if (continuousHoursMin > sorted.size()) {
			sum += PENALTY.penalize(HARD_CONSTRAINT, sorted.size() - continuousHoursMin);
		}
		if (continuousHoursMax < sorted.size()) {
			sum += PENALTY.penalize(HARD_CONSTRAINT, sorted.size() - continuousHoursMax);
		}
		return sum;
	}

}
