/**
 * 
 */
package geneticalg.timetable.functions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.jenetics.Genotype;
import org.jenetics.Mutator;
import org.jenetics.RouletteWheelSelector;
import org.jenetics.SinglePointCrossover;
import org.jenetics.TournamentSelector;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.EvolutionStatistics;
import org.jenetics.engine.limit;
import org.jenetics.util.Factory;

import geneticalg.timetable.entities.Course;
import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.genetic.CourseGene;
import geneticalg.timetable.genetic.TimetableChromosome;

public class Genetic {

	public static void runEvolution() {

		Integer totalHours = Group.getGroups().stream().collect(Collectors.summingInt(x -> x.getNoHours()));

		Factory<Genotype<CourseGene>> factory = Genotype
				.of(TimetableChromosome.of(CourseGene.seq(totalHours, Group.getGroups(), Teacher.getTeachers())));

		Engine<CourseGene, Long> engine = Engine.builder(Fitness::checkFitnessOneChromosome, factory).minimizing()
				.survivorsSelector(new TournamentSelector<>(5)).offspringSelector(new RouletteWheelSelector<>())
				.alterers(new Mutator<>(0.4), new SinglePointCrossover<>(0.4)).build();

		Consumer<? super EvolutionResult<CourseGene, Long>> statistics = EvolutionStatistics.ofNumber();

		Genotype<CourseGene> result = engine.stream().limit(limit.bySteadyFitness(10000)).peek(statistics)
				.collect(EvolutionResult.toBestGenotype());
		
		List<Course> finalmente = new ArrayList<Course>();
		result.getChromosome().forEach(x -> finalmente.add(x.getAllele()));
		System.out.println(finalmente.stream().sorted(Comparator.comparing(Course::getDay).thenComparing(Course::getHour)).collect(Collectors.toList()));
		System.out.println(Fitness.checkFitnessOneChromosome(result));
		System.out.println(statistics);
	}
}
