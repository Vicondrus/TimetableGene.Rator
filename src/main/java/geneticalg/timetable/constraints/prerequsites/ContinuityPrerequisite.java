package geneticalg.timetable.constraints.prerequsites;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.WeekDay;

public class ContinuityPrerequisite implements Prerequisite {

	private Integer continuousHoursMax;
	private Integer continuousHoursMin;
	private WeekDay day;

	@Override
	public boolean checkPrerequsite(List<Course> timetable) {
		Map<WeekDay, List<Course>> map = timetable.stream().collect(Collectors.groupingBy(Course::getDay));
		boolean ok = true;
		for (Entry<WeekDay, List<Course>> e : map.entrySet()) {
			if (e.getKey().equals(WeekDay.WORKDAYS)) {
				ok = ok & analyzeDay(e.getValue());
			} else if (e.getKey().equals(day)) {
				return analyzeDay(e.getValue());
			}
		}
		return ok;
	}

	public boolean analyzeDay(List<Course> day) {
		if (continuousHoursMax < continuousHoursMin) {
			int temp = continuousHoursMax;
			continuousHoursMax = continuousHoursMin;
			continuousHoursMin = temp;
		}
		List<Course> sorted = day.stream().sorted(Comparator.comparing(Course::getHour)).collect(Collectors.toList());
		int cont = 1;
		if (sorted.size() == 1) {
			return false;
		}
		for (int i = 0; i < sorted.size() - 1; i++) {
			if (sorted.get(i).getHour() == sorted.get(i + 1).getHour() - 1) {
				cont++;
			} else {
				if (continuousHoursMin > cont) {
					return false;
				}
				if (continuousHoursMax < cont) {
					return false;
				}
				cont = 1;
			}
		}
		if (continuousHoursMin > cont) {
			return false;
		}
		if (continuousHoursMax < cont) {
			return false;
		}
		return true;
	}

	public ContinuityPrerequisite(Integer continuousHoursMax, Integer continuousHoursMin, WeekDay day) {
		super();
		this.continuousHoursMax = continuousHoursMax;
		this.continuousHoursMin = continuousHoursMin;
		this.day = day;
	}

	public ContinuityPrerequisite(Integer continuousHoursMax, Integer continuousHoursMin) {
		new ContinuityPrerequisite(continuousHoursMax, continuousHoursMin, WeekDay.WORKDAYS);
	}

	public WeekDay getDay() {
		return day;
	}

	public void setDay(WeekDay day) {
		this.day = day;
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

}
