package models;

import java.util.Arrays;
import java.util.List;

public class Catalog {
	public String name; 
	public List<Excercise> excercises;
	
	
	public Catalog(String name, List<Excercise> excercises) {
		this.name = name;
		this.excercises = excercises;
	}

	public Catalog(String name, Excercise... excercises) {
		this.name = name;
		this.excercises = Arrays.asList(excercises);
	}
}
