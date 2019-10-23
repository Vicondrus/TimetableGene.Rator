package geneticalg.timetable.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import geneticalg.timetable.constraints.Constraint;
import geneticalg.timetable.constraints.SuperpositionConstraint;

public class Group {

	private static List<Group> groups = new ArrayList<Group>();

	private String number;

	private Integer noHours;

	private List<Constraint> constraints = new ArrayList<Constraint>();

	private List<Course> timetable = new ArrayList<Course>();

	public Group() {
		if (!groups.stream().anyMatch(x -> x.getNumber().equals(this.number)))
			groups.add(this);
		constraints.add(new SuperpositionConstraint());
	}

	public Long checkConstraints() {
		if (constraints.isEmpty())
			return 0L;
		return constraints.stream().collect(Collectors.summingLong(x -> x.checkConstraint(timetable)));
	}

	public static Group getGroupByNumber(String number) {
		for (Group g : groups)
			if (g.getNumber().equals(number))
				return g;
		return null;
	}

	public static List<Group> getGroups() {
		return groups;
	}

	public static void setGroups(List<Group> groups) {
		Group.groups = groups;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getNoHours() {
		return noHours;
	}

	public void setNoHours(Integer noHours) {
		this.noHours = noHours;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	public List<Course> getTimetable() {
		return timetable;
	}

	public void setTimetable(List<Course> timetable) {
		this.timetable = timetable;
	}

}
