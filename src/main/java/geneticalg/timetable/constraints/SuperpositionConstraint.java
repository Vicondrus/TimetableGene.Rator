package geneticalg.timetable.constraints;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.WeekDay;

public class SuperpositionConstraint implements Constraint {

	@Override
	public Long checkConstraint(List<Course> timetable) {
//		timetable.stream().forEach(x -> {
//			timetable.forEach(y -> System.out.println(y));
//			if(x == null)
//				System.out.println("CE PLM");
//			if(x.getDay() == null)
//				System.out.println("AICI II BAI");
//			if(x.getHour() == null)
//				System.out.println("SAU AICI");
//		});
		
		Map<WeekDay, Map<Integer, List<Course>>> map = timetable.stream()
				.collect(Collectors.groupingBy(Course::getDay, Collectors.groupingBy(Course::getHour)));
		Long sum = 0L;
		for (Entry<WeekDay, Map<Integer, List<Course>>> e1 : map.entrySet()) {
			for (Entry<Integer, List<Course>> e2 : e1.getValue().entrySet()) {
				if (e2.getValue().size() > 1)
					sum += new Double(Math.pow(3, e2.getValue().size())).longValue();
			}
		}
		return sum;
	}
}
