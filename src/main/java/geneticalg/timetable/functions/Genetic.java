/**
 * 
 */
package geneticalg.timetable.functions;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import geneticalg.timetable.entities.Group;
import geneticalg.timetable.entities.Teacher;
import geneticalg.timetable.genetic.CourseGene;
import geneticalg.timetable.genetic.TimetableChromosome;
import io.jenetics.EliteSelector;
import io.jenetics.Genotype;
import io.jenetics.MultiPointCrossover;
import io.jenetics.Mutator;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;

public class Genetic {

	public static void runEvolution(Integer minutes) {

		Integer totalHours = Group.getGroups().stream().collect(Collectors.summingInt(x -> x.getNoHours()));

		Factory<Genotype<CourseGene>> factory = Genotype
				.of(TimetableChromosome.of(CourseGene.seq(totalHours, Group.getGroups(), Teacher.getTeachers())));

		Engine<CourseGene, Long> engine = Engine.builder(Fitness::checkFitnessOneChromosome, factory).minimizing()
				.survivorsSelector(new EliteSelector<>())
				// .survivorsSelector(new TournamentSelector<>(5))
				.offspringSelector(new RouletteWheelSelector<>())
				.alterers(new Mutator<>(0.6), new MultiPointCrossover<>(0.4)).build();

		Consumer<? super EvolutionResult<CourseGene, Long>> statistics = EvolutionStatistics.ofNumber();

		Genotype<CourseGene> result = engine.stream().limit(Limits.byExecutionTime(Duration.ofMinutes(minutes)))
				.limit(Limits.byFitnessThreshold(1L)).limit(Limits.bySteadyFitness(50000)).peek(statistics).collect(EvolutionResult.toBestGenotype());

		SolutionHandler.handleResult(result);
		System.out.println(Fitness.checkFitnessOneChromosome(result));
		System.out.println(statistics);
	}
}
