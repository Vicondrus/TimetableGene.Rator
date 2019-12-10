package geneticalg.timetable.constraints.penalties;

public class ExponentialPenalty implements PenaltyStrategy {

	@Override
	public Long penalize(int constraintType, int value) {
		return new Double(Math.pow(constraintType, Math.abs(value))).longValue();
	}

}
