package geneticalg.timetable.constraints.tests;

import geneticalg.timetable.entities.Affiliation;
import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.WeekDay;

public class SuperpositionConstraintTester {

	public static void main(String[] args) {
		Teacher t1 = new Teacher("Marin", Affiliation.Sciences);
		Course c1 = new Course();
		c1.setDay(WeekDay.FRIDAY);
		c1.setHour(12);
		c1.setGroup(new Group("12A"));
		c1.setTeacher(t1);
		t1.getTimetable().add(c1);
		Course c2 = new Course();
		c2.setDay(WeekDay.FRIDAY);
		c2.setHour(12);
		c2.setGroup(new Group("12B"));
		c2.setTeacher(t1);
		t1.getTimetable().add(c2);
		Long x = t1.checkConstraints();
		System.out.println(x);
	}

}
