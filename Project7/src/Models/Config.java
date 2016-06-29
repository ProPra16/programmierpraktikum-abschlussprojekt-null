package models;

import org.w3c.dom.Element;

import xmlModelParser.Parsable;
/**
 * Represents the XML tag "config", implements {@link Parsable}.
 *
 */
public class Config implements Parsable{
    public boolean babysteps;
    // TODO: Save babysteps time variable
    public boolean timetracking;
    
	public Config(boolean babysteps, boolean timetracking) {
		super();
		this.babysteps = babysteps;
		this.timetracking = timetracking;
	}

	public Config() {
		
	}

	@Override
	public Parsable loadfromXML(Element element) {
		//TODO: Discuss, how to implement this class! -->Class Variables
		return this;
	}
}