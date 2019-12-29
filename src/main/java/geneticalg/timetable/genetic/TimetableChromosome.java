package geneticalg.timetable.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Room;
import geneticalg.timetable.entities.Teacher;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;

public class TimetableChromosome implements Chromosome<CourseGene> {

	private ISeq<CourseGene> iSeq;
	private final int length;

	public TimetableChromosome(ISeq<CourseGene> iSeq) {
		super();
		this.iSeq = iSeq;
		length = iSeq.length();
	}

	public static TimetableChromosome of(ISeq<CourseGene> genes) {
		return new TimetableChromosome(genes);
	}

	@Override
	public boolean isValid() {
		return iSeq.stream().allMatch(CourseGene::isValid);
	}

	@Override
	public Iterator<CourseGene> iterator() {
		return iSeq.iterator();
	}
	
	private boolean checkIfValidHardConstraints() {
		if (!iSeq.stream().allMatch(CourseGene::isValid))
			return false;
		Map<Teacher, List<Course>> teacherTimetables = new HashMap<Teacher, List<Course>>();
		Map<Group, List<Course>> groupTimetables = new HashMap<Group, List<Course>>();
		Map<Room, List<Course>> roomTimetables = new HashMap<Room, List<Course>>();
		iSeq.stream().forEach(x -> {
			Course course = x.getAllele();
			if (course != null) {
				if (teacherTimetables.get(course.getTeacher()) == null) {
					teacherTimetables.put(course.getTeacher(), new ArrayList<Course>());
				}
				teacherTimetables.get(course.getTeacher()).add(course);
				if (groupTimetables.get(course.getGroup()) == null) {
					groupTimetables.put(course.getGroup(), new ArrayList<Course>());
				}
				groupTimetables.get(course.getGroup()).add(course);
				if (roomTimetables.get(course.getRoom()) == null) {
					roomTimetables.put(course.getRoom(), new ArrayList<Course>());
				}
				roomTimetables.get(course.getRoom()).add(course);
			} else {
				System.out.println(iSeq);
			}
		});
		Long sum = 0L;
		sum += teacherTimetables.entrySet().stream()
				.collect(Collectors.summingLong(x -> x.getKey().checkConstraints(x.getValue())));
		sum += groupTimetables.entrySet().stream()
				.collect(Collectors.summingLong(x -> x.getKey().checkConstraints(x.getValue())));
		sum += roomTimetables.entrySet().stream()
				.collect(Collectors.summingLong(x -> x.getKey().checkConstraints(x.getValue())));
		if (sum > 0)
			return false;
		return true;
	}

	@Override
	public Chromosome<CourseGene> newInstance() {
		ISeq<CourseGene> genes = ISeq.empty();
		for (int i = 0; i < length; i++) {
			genes = genes.append(CourseGene.of(Course.randomCourse(Teacher.getTeachers(), Group.getGroups())));
		}
		return new TimetableChromosome(genes);
	}

	@Override
	public Chromosome<CourseGene> newInstance(ISeq<CourseGene> genes) {
		return new TimetableChromosome(genes);
	}

	@Override
	public CourseGene getGene(int index) {
		return iSeq.get(index);
	}

	@Override
	public int length() {
		return iSeq.length();
	}

	@Override
	public ISeq<CourseGene> toSeq() {
		return iSeq;
	}

	@Override
	public String toString() {
		String s = "";
		for (CourseGene cg : iSeq) {
			s += cg;
		}
		return s;
	}

}
