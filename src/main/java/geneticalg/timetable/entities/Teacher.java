package geneticalg.timetable.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import geneticalg.timetable.constraints.Constraint;
import geneticalg.timetable.constraints.SuperpositionConstraint;

public class Teacher {

	private static List<Teacher> teachers = new ArrayList<Teacher>();

	private String name;

	private List<Constraint> constraints = new ArrayList<Constraint>();

	private List<Course> timetable = new ArrayList<Course>();

	public Teacher(String name) {
		this.name = name;
		constraints.add(new SuperpositionConstraint());
		if (!teachers.stream().anyMatch(x -> x.getName().equals(this.name)))
			teachers.add(this);
	}

	public void addConstraint(Constraint c) {
		constraints.add(c);
	}

	public Long checkConstraints() {
		if (constraints.isEmpty())
			return 0L;
		return constraints.stream().collect(Collectors.summingLong(x -> x.checkConstraint(timetable)));
	}

	public static Teacher getTeacherByName(String name) {
		for (Teacher t : teachers)
			if (t.getName().equals(name))
				return t;
		return null;
	}

	public static List<Teacher> getTeachers() {
		return teachers;
	}

	public static void setTeachers(List<Teacher> teachers) {
		Teacher.teachers = teachers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
