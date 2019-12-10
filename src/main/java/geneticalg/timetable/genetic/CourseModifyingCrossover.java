package geneticalg.timetable.genetic;

import static java.lang.String.format;

import java.util.Random;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Room;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.entities.WeekDay;
import io.jenetics.Crossover;
import io.jenetics.Gene;
import io.jenetics.internal.math.comb;
import io.jenetics.util.MSeq;
import io.jenetics.util.RandomRegistry;

public class CourseModifyingCrossover<G extends Gene<Course, CourseGene>, C extends Comparable<? super C>>
		extends Crossover<CourseGene, C> {

	private final int _n;

	private static double interGeneProbability;

	public CourseModifyingCrossover(double probability, double interGeneProbability, final int n) {
		super(probability);
		if (n < 1) {
			throw new IllegalArgumentException(format("n must be at least 1 but was %d.", n));
		}
		_n = n;
		CourseModifyingCrossover.interGeneProbability = interGeneProbability;
	}

	public CourseModifyingCrossover(double probability, double interGeneProbability) {
		this(probability, interGeneProbability, 2);
	}

	public CourseModifyingCrossover(final int n) {
		this(0.05, 0.3, n);
	}

	public CourseModifyingCrossover() {
		this(0.05, 0.3, 2);
	}

	public int getN() {
		return _n;
	}

	@Override
	protected int crossover(MSeq<CourseGene> that, MSeq<CourseGene> other) {
		assert that.length() == other.length();

		final int n = Math.min(that.length(), other.length());
		final int k = Math.min(n, _n);

		final Random random = RandomRegistry.getRandom();
		final int[] points = k > 0 ? comb.subset(n, k, random) : new int[0];

		crossover(that, other, points);
		return 2;
	}

	static <G> void crossover(MSeq<CourseGene> that, MSeq<CourseGene> other, final int[] indexes) {

		for (int i = 0; i < indexes.length - 1; i += 2) {
			final int start = indexes[i];
			final int end = indexes[i + 1];
			//that.swap(start, end, other, start);
			Random random = RandomRegistry.getRandom();
			for (int j = start; j < end; j++) {
				Course thatCourse = that.get(j).getAllele();
				Course otherCourse = other.get(j).getAllele();
				double teacherChance = random.nextDouble();
				if (teacherChance < interGeneProbability) {
					Teacher aux = thatCourse.getTeacher();
					thatCourse.setTeacher(otherCourse.getTeacher());
					otherCourse.setTeacher(aux);
				}
				double hourChance = random.nextDouble();
				if (hourChance < interGeneProbability) {
					Integer aux = thatCourse.getHour();
					thatCourse.setHour(otherCourse.getHour());
					otherCourse.setHour(aux);
				}
				double dayChance = random.nextDouble();
				if (dayChance < interGeneProbability) {
					WeekDay aux = thatCourse.getDay();
					thatCourse.setDay(otherCourse.getDay());
					otherCourse.setDay(aux);
				}
				double roomChance = random.nextDouble();
				if (roomChance < interGeneProbability) {
					Room aux = thatCourse.getRoom();
					thatCourse.setRoom(otherCourse.getRoom());
					otherCourse.setRoom(aux);
				}
				double groupChance = random.nextDouble();
				if (groupChance < interGeneProbability) {
					Group aux = thatCourse.getGroup();
					thatCourse.setGroup(otherCourse.getGroup());
					otherCourse.setGroup(aux);
				}
			}
		}
		if (indexes.length % 2 == 1) {
			final int index = indexes[indexes.length - 1];
			that.swap(index, Math.min(that.length(), other.length()), other, index);
		}
	}

}
