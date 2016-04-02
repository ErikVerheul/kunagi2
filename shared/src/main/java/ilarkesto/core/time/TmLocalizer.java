package ilarkesto.core.time;

public class TmLocalizer {

	public String full(Weekday day) {
		switch (day) {
			case MONDAY:
				return "Monday";
			case TUESDAY:
				return "Tuesday";
			case WEDNESDAY:
				return "Wednesday";
			case THURSDAY:
				return "Thursday";
			case FRIDAY:
				return "Friday";
			case SATURDAY:
				return "Saturday";
			case SUNDAY:
				return "Sunday";
		}
		throw new IllegalStateException(day.name());
	}

	public String full(Month month) {
		switch (month) {
			case JANUARY:
				return "January";
			case FEBRUARY:
				return "February";
			case MARCH:
				return "March";
			case APRIL:
				return "April";
			case MAY:
				return "May";
			case JUNE:
				return "June";
			case JULY:
				return "July";
			case AUGUST:
				return "August";
			case SEPTEMBER:
				return "September";
			case OCTOBER:
				return "October";
			case NOVEMBER:
				return "November";
			case DECEMBER:
				return "December";
		}
		throw new IllegalArgumentException(month.name());
	}

	public String years(long count) {
		return count == 1 || count == -1 ? "year" : "years";
	}

	public String months(long count) {
		return count == 1 || count == -1 ? "month" : "months";
	}

	public String weeks(long count) {
		return count == 1 || count == -1 ? "week" : "weeks";
	}

	public String days(long count) {
		return count == 1 || count == -1 ? "day" : "days";
	}

	public String hours(long count) {
		return count == 1 || count == -1 ? "hour" : "hours";
	}

	public String minutes(long count) {
		return count == 1 || count == -1 ? "minute" : "minutes";
	}

	public String seconds(long count) {
		return count == 1 || count == -1 ? "second" : "seconds";
	}

	public String millis(long count) {
		return count == 1 || count == -1 ? "milli" : "millis";
	}

}
