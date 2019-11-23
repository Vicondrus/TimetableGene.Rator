package geneticalg.timetable.constraints.tests;

import java.util.Arrays;
import java.util.List;

import geneticalg.timetable.constraints.ContinuityConstraint;
import geneticalg.timetable.constraints.DiversityConstraint;
import geneticalg.timetable.constraints.GroupWeeklyConstraint;
import geneticalg.timetable.entities.Affiliation;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Room;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.auxiliary.TeacherAmount;
import geneticalg.timetable.functions.Genetic;

public class GeneticTester {

	public static void main(String[] args) {
		Teacher t1 = new Teacher("Maths1", Affiliation.Sciences);
		Teacher t2 = new Teacher("English1", Affiliation.Languages);
		Teacher t3 = new Teacher("Physics", Affiliation.Sciences);
		Teacher t4 = new Teacher("Maths2", Affiliation.Sciences);
		Teacher t5 = new Teacher("ComputerScience", Affiliation.Sciences);
		t1.addRoom(new Room("1"));
		t2.addRoom(new Room("2"));
		t3.addRoom(new Room("3"));
		t4.addRoom(new Room("1"));
		t5.addRoom(new Room("5"));
		List<TeacherAmount> how1 = Arrays.asList(new TeacherAmount(t1, 5), new TeacherAmount(t2, 3),
				new TeacherAmount(t3, 3), new TeacherAmount(t5, 5));
		List<TeacherAmount> how2 = Arrays.asList(new TeacherAmount(t4, 4), new TeacherAmount(t2, 4),
				new TeacherAmount(t3, 5));
		GroupWeeklyConstraint c1 = new GroupWeeklyConstraint();
		c1.setNecessaryHours(how1);
		GroupWeeklyConstraint c2 = new GroupWeeklyConstraint();
		c2.setNecessaryHours(how2);
		ContinuityConstraint cc = new ContinuityConstraint(4, 7);
		DiversityConstraint dc1 = new DiversityConstraint();
		dc1.setNecessaryHours(how1);
		DiversityConstraint dc2 = new DiversityConstraint();
		dc2.setNecessaryHours(how2);
		Group g1 = new Group("12A");
		g1.addConstraint(c1);
		g1.addConstraint(cc);
		g1.addConstraint(dc1);
		Group g2 = new Group("12B");
		g2.addConstraint(c2);
		g2.addConstraint(cc);
		g2.addConstraint(dc2);
		List<Teacher> ts = Teacher.getTeachers();
		List<Group> gs = Group.getGroups();
		Genetic.runEvolution(5);
		// System.out.print(g.checkConstraints());
	}

}
