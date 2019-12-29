package geneticalg.timetable.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import geneticalg.timetable.constraints.Constraint;
import geneticalg.timetable.constraints.GroupWeeklyConstraint;
import geneticalg.timetable.constraints.SuperpositionConstraint;

public class Teacher {

	private static List<Teacher> teachers = new ArrayList<Teacher>();

	private String name;

	private List<Constraint> constraints = new ArrayList<Constraint>();

	private List<Course> timetable = new ArrayList<Course>();

	private List<Room> rooms = new ArrayList<Room>();

	private Affiliation affiliation;

	public Teacher(String name, Affiliation affiliation) {
		this.name = name;
		this.affiliation = affiliation;
		constraints.add(new SuperpositionConstraint());
		if (!teachers.stream().anyMatch(x -> x.getName().equals(this.name)))
			teachers.add(this);
	}

	public Affiliation getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(Affiliation affiliation) {
		this.affiliation = affiliation;
	}

	public void addConstraint(Constraint c) {
		constraints.add(c);
	}

	public void addRoom(Room r) {
		rooms.add(r);
	}

	public Long checkConstraints(List<Course> timetable) {
		if (constraints.isEmpty())
			return 0L;
		return constraints.stream().collect(Collectors.summingLong(x -> x.checkConstraint(timetable)));
	}

	public Long checkImperativeConstraints(List<Course> timetable) {
		if (constraints.isEmpty())
			return 0L;
		return constraints.stream().filter(x -> {
			if (x instanceof SuperpositionConstraint || x instanceof GroupWeeklyConstraint)
				return true;
			return false;
		}).collect(Collectors.summingLong(x -> x.checkConstraint(timetable)));
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

	@Override
	public String toString() {
		return name;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

}
