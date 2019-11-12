package geneticalg.customGenes;

import java.util.Iterator;
import java.util.Random;

import geneticalg.brainfck.Brainfck;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;
import io.jenetics.util.RandomRegistry;

public class BFckChromosome implements Chromosome<BFckGene> {

	private static final String BFCK_CHARS = "<>=-.,[]";

	private Brainfck bf = new Brainfck();

	private ISeq<BFckGene> iSeq;
	private final int length;

	public BFckChromosome(ISeq<BFckGene> iSeq) {
		super();
		this.iSeq = iSeq;
		length = iSeq.length();
	}

	public static BFckChromosome of(ISeq<BFckGene> genes) {
		return new BFckChromosome(genes);
	}

	@Override
	public boolean isValid() {
		if (!iSeq.stream().allMatch(BFckGene::isValid))
			return false;
		//String code = iSeq.stream().map(x -> "" + x.getAllele()).collect(Collectors.joining());
		//if (bf.interpret(code) > -1)
			//return true;
		return true;

	}

	@Override
	public Iterator<BFckGene> iterator() {
		return iSeq.iterator();
	}

	@Override
	public Chromosome<BFckGene> newInstance() {
		final Random random = RandomRegistry.getRandom();
		ISeq<BFckGene> genes = ISeq.empty();
		for (int i = 0; i < length; i++) {
			genes = genes.append(BFckGene.of(BFCK_CHARS.charAt(Math.abs(random.nextInt(8)))));
		}
		return new BFckChromosome(genes);
	}

	@Override
	public Chromosome<BFckGene> newInstance(ISeq<BFckGene> genes) {
		return new BFckChromosome(genes);
	}

	@Override
	public BFckGene getGene(int index) {
		return iSeq.get(index);
	}

	@Override
	public int length() {
		return iSeq.length();
	}

	@Override
	public ISeq<BFckGene> toSeq() {
		return iSeq;
	}

}
