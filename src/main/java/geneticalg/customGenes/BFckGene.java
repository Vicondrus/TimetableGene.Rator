package geneticalg.customGenes;

import java.util.Random;

import org.jenetics.Gene;
import org.jenetics.internal.math.random;
import org.jenetics.util.ISeq;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;

public class BFckGene implements Gene<Character, BFckGene> {

	private static final String BFCK_CHARS = "<>=-.,[]";

	private Character value;

	public BFckGene(Character value) {
		super();
		this.value = value;
	}

	public static BFckGene of(Character value) {
		return new BFckGene(value);
	}

	public static ISeq<BFckGene> seq(int length) {
		Random r = RandomRegistry.getRandom();
		return MSeq.<BFckGene>ofLength(length).fill(() -> new BFckGene(BFCK_CHARS.charAt(random.nextInt(r, 0, 7))))
				.toISeq();
	}

	@Override
	public boolean isValid() {
		return BFCK_CHARS.contains(value.toString());
	}

	@Override
	public Character getAllele() {
		return value;
	}

	@Override
	public BFckGene newInstance() {
		final Random random = RandomRegistry.getRandom();
		return new BFckGene(BFCK_CHARS.charAt(Math.abs(random.nextInt(8))));
	}

	@Override
	public BFckGene newInstance(Character value) {
		return new BFckGene(value);
	}

}
