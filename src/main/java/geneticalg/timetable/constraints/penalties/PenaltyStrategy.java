package geneticalg.timetable.constraints.penalties;

public interface PenaltyStrategy {

	Long penalize(int constraintType, int value);
	
}
