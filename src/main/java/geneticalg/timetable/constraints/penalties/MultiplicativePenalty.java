package geneticalg.timetable.constraints.penalties;

public class MultiplicativePenalty implements PenaltyStrategy{

	@Override
	public Long penalize(int constraintType, int value) {
		return new Long(constraintType*Math.abs(value));
	}

}
