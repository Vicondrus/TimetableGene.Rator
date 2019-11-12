package geneticalg.timetable.genetic;

import java.util.Iterator;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
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
