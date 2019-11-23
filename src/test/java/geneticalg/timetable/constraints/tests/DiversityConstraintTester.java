package geneticalg.timetable.constraints.tests;

import java.util.Arrays;
import java.util.List;

import geneticalg.timetable.constraints.DiversityConstraint;
import geneticalg.timetable.entities.Affiliation;
import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.WeekDay;
import geneticalg.timetable.entities.auxiliary.TeacherAmount;

public class DiversityConstraintTester {

	public static void main(String[] args) {
		Teacher t1 = new Teacher("Maths1", Affiliation.Sciences);
		Teacher t2 = new Teacher("English1", Affiliation.Languages);
		Teacher t3 = new Teacher("Physics", Affiliation.Sciences);
		Teacher t4 = new Teacher("Maths2", Affiliation.Sciences);
		Teacher t5 = new Teacher("ComputerScience", Affiliation.Sciences);
		List<TeacherAmount> how1 = Arrays.asList(new TeacherAmount(t1, 3), new TeacherAmount(t2, 3),
				new TeacherAmount(t3, 2));
		DiversityConstraint c = new DiversityConstraint();
		Group g = new Group("12A");
		c.setNecessaryHours(how1);
		Course c1 = new Course(g, t1, 12, WeekDay.MONDAY, null);
		Course c2 = new Course(g, t1, 12, WeekDay.TUESDAY, null);
		Course c3 = new Course(g, t1, 12, WeekDay.WEDNESDAY, null);
		Course c4 = new Course(g, t3, 12, WeekDay.THURSDAY, null);
		Course c5 = new Course(g, t3, 12, WeekDay.FRIDAY, null);
		Course c6 = new Course(g, t2, 12, WeekDay.MONDAY, null);
		Course c7 = new Course(g, t2, 12, WeekDay.TUESDAY, null);
		Course c8 = new Course(g, t2, 12, WeekDay.WEDNESDAY, null);
		Course c9 = new Course(g, t2, 12, WeekDay.THURSDAY, null);
		Course c10 = new Course(g, t2, 12, WeekDay.FRIDAY, null);
		List<Course> timetable = Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
		System.out.println(c.checkConstraint(timetable));
	}

}
