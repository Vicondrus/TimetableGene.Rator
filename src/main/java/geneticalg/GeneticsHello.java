package geneticalg;

import java.util.function.Consumer;
import java.util.stream.Collectors;

import geneticalg.brainfck.Brainfck;
import geneticalg.customGenes.BFckChromosome;
import geneticalg.customGenes.BFckGene;
import io.jenetics.CharacterChromosome;
import io.jenetics.CharacterGene;
import io.jenetics.Genotype;
import io.jenetics.MultiPointCrossover;
import io.jenetics.Mutator;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.TournamentSelector;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;

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

	private static Long similarityBf(String a, String b) {
		long sum = 0;
		for (int i = 0; i < a.length() && i < b.length(); i++)
			sum -= Math.abs(a.charAt(i) - b.charAt(i));
		if (a.length() < b.length()) {
			for (int i = a.length(); i < b.length(); i++)
				if (b.charAt(i) != ' ')
					sum -= 5;
		} else {
			for (int i = b.length(); i < a.length(); i++)
				if (a.charAt(i) != ' ')
					sum -= 5;
		}

		return sum;
	}

	private static Long checkFitnessGeneticsHello(Genotype<CharacterGene> gt) {

		CharacterChromosome lc = gt.getChromosome().as(CharacterChromosome.class);

		String message = lc.stream().map(x -> "" + x.getAllele()).collect(Collectors.joining());

		System.out.println(message);

		return similarity(prop, message);

	}

	private static void demo1() {
		prop = "Hello World!";

		Factory<Genotype<CharacterGene>> gtf = Genotype.of(CharacterChromosome.of(30));

		Engine<CharacterGene, Long> engine = Engine.builder(GeneticsHello::checkFitnessGeneticsHello, gtf)
				.survivorsSelector(new TournamentSelector<>(5)).offspringSelector(new RouletteWheelSelector<>())
				.alterers(new Mutator<>(0.3), new MultiPointCrossover<>(0.4)).build();

		Consumer<? super EvolutionResult<CharacterGene, Long>> statistics = EvolutionStatistics.ofNumber();

		Genotype<CharacterGene> result = engine.stream().limit(Limits.byFitnessThreshold(similarity(prop, prop) - 1))
				.peek(statistics).collect(EvolutionResult.toBestGenotype());

		System.out.println(result);
		System.out.println(checkFitnessGeneticsHello(result));
		System.out.println(statistics);
	}

	private static Long checkFitnessBFck(Genotype<BFckGene> gt) {
		prop = "Hi";

		BFckChromosome c = gt.getChromosome().as(BFckChromosome.class);

		Brainfck bf = new Brainfck();

		String code = c.stream().map(x -> "" + x.getAllele()).collect(Collectors.joining());

		String msg = bf.interpret(code) == -1 ? "fail" : bf.getConsole();
		msg = msg.equals("") ? "empty" : msg;
		System.out.println(code);
		System.out.println(msg);
		if (msg.equals("empty"))
			return (long) -100;
		if (msg.equals("fail"))
			return (long) -200;
		return similarity(prop, msg);

	}

	private static void demoBFck() {
		prop = "Hi";
		Factory<Genotype<BFckGene>> bfg = Genotype.of(BFckChromosome.of(BFckGene.seq(100)));
		Engine<BFckGene, Long> engine = Engine.builder(GeneticsHello::checkFitnessBFck, bfg)
				.survivorsSelector(new TournamentSelector<>(5)).offspringSelector(new RouletteWheelSelector<>())
				.alterers(new Mutator<>(0.4), new SinglePointCrossover<>(0.4)).build();

		Consumer<? super EvolutionResult<BFckGene, Long>> statistics = EvolutionStatistics.ofNumber();

		Genotype<BFckGene> result = engine.stream().limit(Limits.byFitnessThreshold(similarity(prop, prop) - 1))
				.peek(statistics).collect(EvolutionResult.toBestGenotype());

		System.out.println(result);
		System.out.println(checkFitnessBFck(result));
		System.out.println(statistics);
	}

	public static void main(String[] args) {
		demo1();
	}

}
