package models;

import java.util.Date;

public class TrackingData {
	
	/**
	 * Cycle mode representation - red, green, blue
	 */
	public enum Mode {
		RED,
		GREEN,
		BLUE
	}
	
	/**
	 * Cycle mode
	 */
	private final Mode mode; 
	
	/**
	 * Start date
	 */
	private final Date start;
	
	/**
	 * End date
	 */
	private Date end;
	
	/**
	 * Duration needed for a step
	 */
	private long duration;
	
	/**
	 * Constructs a tracking data with a start date
	 * @param start
	 */
	public TrackingData(Mode mode, Date start) {
		this.mode = mode;
		this.start = start;
	}
	
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
		if(start != null && end != null) {
			duration = end.getTime() - start.getTime();
		}
	}

	/**
	 * @return The duration needed for a step
	 */
	public long getDuration() {
		return duration;
	}
	
}
