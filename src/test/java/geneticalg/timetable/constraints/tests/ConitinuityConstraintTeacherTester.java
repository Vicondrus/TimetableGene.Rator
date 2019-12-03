package geneticalg.timetable.constraints.tests;

import java.util.Arrays;

import geneticalg.timetable.constraints.ContinuityConstraintTeacher;
import geneticalg.timetable.entities.Affiliation;
import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Room;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.WeekDay;

public class ConitinuityConstraintTeacherTester {

	public static void main(String[] args) {
		Teacher t1 = new Teacher("Gustav", Affiliation.Sciences);
		Teacher t2 = new Teacher("Marin", Affiliation.Sciences);
		Teacher t3 = new Teacher("Ionut", Affiliation.Sciences);
		t1.addConstraint(new ContinuityConstraintTeacher(4, 8));
		t2.addConstraint(new ContinuityConstraintTeacher(4, 8));
		t3.addConstraint(new ContinuityConstraintTeacher(4, 8));
		Group g = new Group("12A");
		Room r1 = new Room("1");
		Room r2 = new Room("2");
		Room r3 = new Room("3");
		Course c1 = new Course(g, t2, 12, WeekDay.MONDAY, r1);
		Course c2 = new Course(g, t2, 13, WeekDay.MONDAY, r1);
		Course c3 = new Course(g, t2, 14, WeekDay.MONDAY, r1);
		Course c5 = new Course(g, t2, 15, WeekDay.MONDAY, r2);
		Course c4 = new Course(g, t1, 12, WeekDay.FRIDAY, r1);
		Course c6 = new Course(g, t1, 13, WeekDay.FRIDAY, r2);
		t2.getTimetable().addAll(Arrays.asList(c1, c2, c3, c5));
		t1.getTimetable().addAll(Arrays.asList(c4, c6));
		System.out.println(t2.checkConstraints());
		System.out.println(t1.checkConstraints());

		// g.getTimetable().clear();

		Course c7 = new Course(g, t1, 8, WeekDay.MONDAY, r1);
		Course c8 = new Course(g, t1, 9, WeekDay.MONDAY, r1);
		Course c9 = new Course(g, t1, 10, WeekDay.MONDAY, r1);
		Course c10 = new Course(g, t1, 11, WeekDay.MONDAY, r2);
		Course c14 = new Course(g, t3, 8, WeekDay.FRIDAY, r1);
		Course c11 = new Course(g, t3, 9, WeekDay.FRIDAY, r1);
		Course c12 = new Course(g, t3, 10, WeekDay.FRIDAY, r2);
		Course c13 = new Course(g, t3, 11, WeekDay.FRIDAY, r2);
		t1.getTimetable().clear();
		t1.getTimetable().addAll(Arrays.asList(c7, c8, c9, c10));
		t3.getTimetable().addAll(Arrays.asList(c14, c11, c12, c13));
		System.out.println(t3.checkConstraints());
		System.out.println(t1.checkConstraints());
	}

}
