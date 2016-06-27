package models;

public class Config {
    public boolean babysteps;
    // TODO: Save babysteps time variable
    public boolean timetracking;
    
	public Config(boolean babysteps, boolean timetracking) {
		super();
		this.babysteps = babysteps;
		this.timetracking = timetracking;
	}
}