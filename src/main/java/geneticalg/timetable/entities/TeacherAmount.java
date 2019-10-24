package geneticalg.timetable.entities;

public class TeacherAmount {

	private Teacher teacher;
	
	private Integer hours;

	public TeacherAmount(Teacher teacher, Integer hours) {
		super();
		this.teacher = teacher;
		this.hours = hours;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}
	
}
