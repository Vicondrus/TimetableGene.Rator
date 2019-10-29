package geneticalg.timetable.entities;

import java.util.List;
import java.util.Random;

import org.jenetics.internal.math.random;
import org.jenetics.util.RandomRegistry;

public class Course {

	private static final Integer MIN_HOUR = 8;

	private static final Integer MAX_HOUR = 16;

	private Group group;

	private Teacher teacher;

	private Integer hour;

	private WeekDay day;

	public Course(Group group, Teacher teacher, Integer hour, WeekDay day) {
		super();
		this.group = group;
		this.teacher = teacher;
		this.hour = hour;
		this.day = day;
	}

	public Course() {

	}

	public static Course randomCourse(List<Teacher> teachers, List<Group> groups) {
		Course c = new Course();
		Random r = RandomRegistry.getRandom();
		c.setDay(WeekDay.values()[random.nextInt(r, 0, 4)]);
		c.setHour(random.nextInt(r, MIN_HOUR, MAX_HOUR));
		c.setTeacher(teachers.get(random.nextInt(r, 0, teachers.size() - 1)));
		c.setGroup(groups.get(random.nextInt(r, 0, groups.size() - 1)));
		return c;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		if (hour >= MIN_HOUR && hour <= MAX_HOUR)
			this.hour = hour;
		else
			throw new IllegalArgumentException("Not an appropriate hour");
	}

	public WeekDay getDay() {
		return day;
	}

	public void setDay(WeekDay day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "Course [group=" + group + ", teacher=" + teacher + ", hour=" + hour + ", day=" + day + "]";
	}
	
}
