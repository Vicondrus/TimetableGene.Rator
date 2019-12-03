package geneticalg.timetable.constraints;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.WeekDay;

public class ContinuityConstraintTeacher implements Constraint{
	
	private Integer continuousHoursMax;
	private Integer continuousHoursMin;

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

	public ContinuityConstraintTeacher() {
		super();
	}

	public ContinuityConstraintTeacher(Integer continuousHoursMax, Integer continuousHoursMin) {
		super();
		this.continuousHoursMax = continuousHoursMax;
		this.continuousHoursMin = continuousHoursMin;
	}

	@Override
	public Long checkConstraint(List<Course> timetable) {
		Map<WeekDay, List<Course>> map = timetable.stream().collect(Collectors.groupingBy(Course::getDay));
		Long sum = 0L;
		for (Entry<WeekDay, List<Course>> e : map.entrySet()) {
			sum += analyzeDay(e.getValue());
		}
		return sum;
	}
	
	public Long analyzeDay(List<Course> day) {
		if(continuousHoursMax<continuousHoursMin) {
			int temp = continuousHoursMax;
			continuousHoursMax = continuousHoursMin;
			continuousHoursMin = temp;
		}
		List<Course> sorted = day.stream().sorted(Comparator.comparing(Course::getHour)).collect(Collectors.toList());
		Long sum = 0L;
		int cont = 1;
		if (sorted.size() == 1) {
			return new Double(Math.pow(SOFT_CONSTRAINT, Math.abs(cont - continuousHoursMin))).longValue();
		}
		for (int i = 0; i < sorted.size() - 1; i++) {
			if (sorted.get(i).getHour() == sorted.get(i + 1).getHour() - 1) {
				cont++;
			} else {
				if (continuousHoursMin > cont) {
					sum += new Double(Math.pow(SOFT_CONSTRAINT, Math.abs(cont - continuousHoursMin))).longValue();
				}
				if (continuousHoursMax < cont) {
					sum += new Double(Math.pow(SOFT_CONSTRAINT, Math.abs(cont - continuousHoursMax))).longValue();
				}
				cont = 1;
			}
		}
		if (continuousHoursMin > cont) {
			sum += new Double(Math.pow(SOFT_CONSTRAINT, Math.abs(cont - continuousHoursMin))).longValue();
		}
		if (continuousHoursMax < cont) {
			sum += new Double(Math.pow(SOFT_CONSTRAINT, Math.abs(cont - continuousHoursMax))).longValue();
		}
		return sum;
	}

}
