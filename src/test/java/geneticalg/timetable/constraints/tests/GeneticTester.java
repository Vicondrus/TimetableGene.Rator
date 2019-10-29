package geneticalg.timetable.constraints.tests;

import java.util.Arrays;
import java.util.List;

import geneticalg.timetable.constraints.GroupWeeklyConstraint;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.TeacherAmount;
import geneticalg.timetable.functions.Genetic;

public class GeneticTester {

	public static void main(String[] args) {
		Teacher t1 = new Teacher("Gustav");
		Teacher t2 = new Teacher("Marin");
		Teacher t3 = new Teacher("Ionut");
		List<TeacherAmount> how1 = Arrays.asList(new TeacherAmount(t1, 3), new TeacherAmount(t2, 5),
				new TeacherAmount(t3, 3));
		List<TeacherAmount> how2 = Arrays.asList(new TeacherAmount(t1, 5), new TeacherAmount(t2, 3),
				new TeacherAmount(t3, 2));
		GroupWeeklyConstraint c1 = new GroupWeeklyConstraint();
		c1.setNecessaryHours(how1);
		GroupWeeklyConstraint c2 = new GroupWeeklyConstraint();
		c2.setNecessaryHours(how2);
		Group g1 = new Group("12A");
		g1.addConstraint(c1);
		Group g2 = new Group("11A");
		g2.addConstraint(c2);
		List<Teacher> ts = Teacher.getTeachers();
		List<Group> gs = Group.getGroups();
		Genetic.runEvolution();
		// System.out.print(g.checkConstraints());
	}

}
