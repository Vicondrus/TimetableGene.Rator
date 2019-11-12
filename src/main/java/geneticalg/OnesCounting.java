package geneticalg;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;

public class OnesCounting {

	private static Integer count(final Genotype<BitGene> gt) {
		return gt.getChromosome().as(BitChromosome.class).bitCount();
	}

	public static void main(String[] args) {
		final Engine<BitGene, Integer> engine = Engine.builder(OnesCounting::count, BitChromosome.of(20, 0.15))
				.populationSize(500).selector(new RouletteWheelSelector<>())
				.alterers(new Mutator<>(0.55), new SinglePointCrossover<>(0.06)).build();

		final EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

		final Phenotype<BitGene, Integer> best = engine.stream()
				// Truncate the evolution stream after " steady " generations
				.limit(Limits.bySteadyFitness(7))
				// The evolution will stop after maximal 10045 generations
				.limit(100)
				// Update the evaluation statistics after each generation
				.peek(statistics)
				// Collect ( reduce ) the evolution stream to its best phenotype .
				.collect(EvolutionResult.toBestPhenotype());
		System.out.println(statistics);
		System.out.println(best);
	}
}