package ilarkesto.core.time;

/**
 *
 *
 */
public class TmLocalizer {

    /**
     *
     * @param day
     * @return
     */
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

    /**
     *
     * @param month
     * @return
     */
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

    /**
     *
     * @param count
     * @return
     */
    public String years(long count) {
		return count == 1 || count == -1 ? "year" : "years";
	}

    /**
     *
     * @param count
     * @return
     */
    public String months(long count) {
		return count == 1 || count == -1 ? "month" : "months";
	}

    /**
     *
     * @param count
     * @return
     */
    public String weeks(long count) {
		return count == 1 || count == -1 ? "week" : "weeks";
	}

    /**
     *
     * @param count
     * @return
     */
    public String days(long count) {
		return count == 1 || count == -1 ? "day" : "days";
	}

    /**
     *
     * @param count
     * @return
     */
    public String hours(long count) {
		return count == 1 || count == -1 ? "hour" : "hours";
	}

    /**
     *
     * @param count
     * @return
     */
    public String minutes(long count) {
		return count == 1 || count == -1 ? "minute" : "minutes";
	}

    /**
     *
     * @param count
     * @return
     */
    public String seconds(long count) {
		return count == 1 || count == -1 ? "second" : "seconds";
	}

    /**
     *
     * @param count
     * @return
     */
    public String millis(long count) {
		return count == 1 || count == -1 ? "milli" : "millis";
	}

}
