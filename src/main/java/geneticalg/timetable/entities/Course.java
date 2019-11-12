package geneticalg.timetable.entities;

import java.util.List;
import java.util.Random;

import io.jenetics.internal.math.random;
import io.jenetics.util.RandomRegistry;

public class Course {

	private static final Integer MIN_HOUR = 8;

	private static final Integer MAX_HOUR = 16;

	private Group group;

	private Teacher teacher;

	private Integer hour;

	private WeekDay day;

	private Room room;

	public Course(Group group, Teacher teacher, Integer hour, WeekDay day, Room room) {
		super();
		this.group = group;
		this.teacher = teacher;
		this.hour = hour;
		this.day = day;
		this.room = room;
	}

	public Course() {

	}

	public static Course randomCourse(List<Teacher> teachers, List<Group> groups) {
		Course c = new Course();
		Random r = RandomRegistry.getRandom();
		c.setDay(WeekDay.values()[random.nextInt(0, 5, r)]);
		c.setHour(random.nextInt(MIN_HOUR, MAX_HOUR, r));
		Teacher t = teachers.get(random.nextInt(0, teachers.size(), r));
		c.setTeacher(t);
		c.setGroup(groups.get(random.nextInt(0, groups.size(), r)));
		c.setRoom(t.getRooms().get(random.nextInt(0, t.getRooms().size(), r)));
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

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Course [group=" + group + ", teacher=" + teacher + ", room=" + room + ", hour=" + hour + ", day=" + day
				+ "]";
	}

}
