package geneticalg.timetable.constraints;

import java.util.List;

import geneticalg.timetable.constraints.conclusions.Conclusion;
import geneticalg.timetable.constraints.prerequsites.Prerequisite;
import geneticalg.timetable.entities.Course;

public class CustomConstraint implements Constraint {

	private final Prerequisite p;

	private final Conclusion c;

	public CustomConstraint(Prerequisite p, Conclusion c) {
		super();
		this.p = p;
		this.c = c;
	}

	@Override
	public Long checkConstraint(List<Course> timetable) {
		if(c == null)
			return 0L;
		else if(p == null || !p.checkPrerequsite(timetable))
			return 0L;
		else return c.checkConclusion(timetable);
			
	}

}
