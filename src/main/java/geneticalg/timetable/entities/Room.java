package geneticalg.timetable.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import geneticalg.timetable.constraints.Constraint;
import geneticalg.timetable.constraints.SuperpositionConstraint;

public class Room {

	private String number;
	
	private List<Constraint> constraints = new ArrayList<Constraint>();
	
	public Room(String number) {
		super();
		this.number = number;
		constraints.add(new SuperpositionConstraint());
	}

	public void addConstraint(Constraint c) {
		constraints.add(c);
	}

	public Long checkConstraints(List<Course> timetable) {
		if (constraints.isEmpty())
			return 0L;
		return constraints.stream().collect(Collectors.summingLong(x -> x.checkConstraint(timetable)));
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return number;
	}
	
}
