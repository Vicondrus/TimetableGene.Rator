package geneticalg.timetable.constraints.tests;

import java.util.Arrays;
import java.util.List;

import geneticalg.timetable.constraints.GroupWeeklyConstraint;
import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.TeacherAmount;
import geneticalg.timetable.entities.WeekDay;

public class WeeklyConstraintTester {

	public static void main(String[] args) {
		Teacher t1 = new Teacher("Gustav");
		Teacher t2 = new Teacher("Marin");
		Teacher t3 = new Teacher("Ionut");
		List<TeacherAmount> how = Arrays.asList(new TeacherAmount(t1, 1), new TeacherAmount(t2, 2),
				new TeacherAmount(t3, 1));
		GroupWeeklyConstraint c = new GroupWeeklyConstraint();
		c.setNecessaryHours(how);
		Group g = new Group("12A");
		g.addConstraint(c);
		Course c1 = new Course(g, t1, 12, WeekDay.MONDAY);
		Course c2 = new Course(g, t2, 13, WeekDay.MONDAY);
		Course c3 = new Course(g, t2, 9, WeekDay.TUESDAY);
		Course c4 = new Course(g, t3, 12, WeekDay.FRIDAY);
		g.getTimetable().addAll(Arrays.asList(c1, c2, c3, c4));
		System.out.print(g.checkConstraints());
	}

}
