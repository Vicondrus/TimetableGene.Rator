package geneticalg;

import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.jenetics.CharacterChromosome;
import org.jenetics.CharacterGene;
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

public class GeneticsHello {

	private static String prop;

	private static Long similarity(String a, String b) {
		long sum = 0;
		for (int i = 0; i < a.length() && i < b.length(); i++)
			if (a.charAt(i) == b.charAt(i))
				sum += 10;
		if (a.length() < b.length()) {
			for (int i = a.length(); i < b.length(); i++)
				if (b.charAt(i) != ' ')
					sum -= 20;
		} else {
			for (int i = b.length(); i < a.length(); i++)
				if (a.charAt(i) != ' ')
					sum -= 20;
		}

		return sum;
	}

	private static Long checkFitness(Genotype<CharacterGene> gt) {

		CharacterChromosome lc = gt.getChromosome().as(CharacterChromosome.class);

		String message = lc.stream().map(x -> "" + x.getAllele()).collect(Collectors.joining());

		System.out.println(message);

		return similarity(prop, message);

	}

	public static void main(String[] args) {

		prop = "Hello World";

		Factory<Genotype<CharacterGene>> gtf = Genotype.of(CharacterChromosome.of(30));

		// gtf.instances().forEach(x -> System.out.println(count++));

//		Engine<CharacterGene, Long> eng = Engine.builder(GeneticsHello::checkFitness, gtf)
//				.survivorsSelector(new TournamentSelector<>(5)).offspringSelector(new RouletteWheelSelector<>())
//				.alterers(new Mutator<>(0.3), new SinglePointCrossover<>(0.4)).build();
//
//		Phenotype<CharacterGene, Long> p = eng.stream().limit(limit.byFitnessThreshold(new Long(110)))
//				.collect(EvolutionResult.toBestPhenotype());

		Engine<CharacterGene, Long> engine = Engine.builder(GeneticsHello::checkFitness, gtf)
				.survivorsSelector(new TournamentSelector<>(5)).offspringSelector(new RouletteWheelSelector<>())
				.alterers(new Mutator<>(0.3), new SinglePointCrossover<>(0.4)).build();

		Consumer<? super EvolutionResult<CharacterGene, Long>> statistics = EvolutionStatistics.ofNumber();

		Genotype<CharacterGene> result = engine.stream().limit(limit.byFitnessThreshold(similarity(prop, prop)))// .limit(limit.byFitnessConvergence(100,
				// 200,
				// 10E-4))
				.limit(limit.bySteadyFitness(30000)).peek(statistics).collect(EvolutionResult.toBestGenotype());

		System.out.println(result);
		System.out.println(checkFitness(result));
		System.out.println(statistics);

	}

}
