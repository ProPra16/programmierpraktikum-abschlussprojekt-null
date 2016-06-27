package models;

import java.util.Arrays;
import java.util.List;

public class Excercise {
	public String name;
	public String description;
	public List<String> classes;
    public List<String> tests;
    public Config config;
    
	public Excercise(String name, String description, List<String> classes, List<String> tests, Config config) {
		this.name = name;
		this.description = description;
		this.classes = classes;
		this.tests = tests;
		this.config = config;
	}
	
	public Excercise(String name, String description, String[] classes, String[] tests, Config config) {
		this.name = name;
		this.description = description;
		this.classes = Arrays.asList(classes);
		this.tests = Arrays.asList(tests);
		this.config = config;
	}
	
	
}
