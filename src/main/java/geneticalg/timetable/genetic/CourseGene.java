package geneticalg.timetable.genetic;

import java.util.List;

import org.jenetics.Gene;
import org.jenetics.util.ISeq;
import org.jenetics.util.MSeq;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;

public class CourseGene implements Gene<Course, CourseGene> {

	private Course value;

	public CourseGene(Course c) {
		value = c;
	}

	public CourseGene(List<Group> groups, List<Teacher> teachers) {
		this(Course.randomCourse(teachers, groups));
	}

	public static CourseGene of(Course c) {
		return new CourseGene(c);
	}

	public static ISeq<CourseGene> seq(int length, List<Group> groups, List<Teacher> teachers) {
		return MSeq.<CourseGene>ofLength(length).fill(() -> new CourseGene(groups, teachers)).toISeq();

	}

	@Override
	public boolean isValid() {
		if (value.getHour() <= 8 || value.getHour() >= 16)
			return false;
		return true;
	}

	@Override
	public Course getAllele() {
		return value;
	}

	@Override
	public CourseGene newInstance() {
		return new CourseGene(Course.randomCourse(Teacher.getTeachers(), Group.getGroups()));
	}

	@Override
	public CourseGene newInstance(Course value) {
		return new CourseGene(value);
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
