package com.scmoure.csvreader.testutils;

import java.util.List;

import com.scmoure.csvreader.CSVColumn;

public class DummyClass {

	@CSVColumn(startingColumn = 2)
	private String name;

	@CSVColumn(startingColumn = 1)
	private Integer age;

	@CSVColumn(startingColumn = 0)
	private int sons;

	@CSVColumn(startingColumn = 3, endingColumn = 7)
	private List<Integer> failures;

	public String getName() {
		return name;
	}

	public List<Integer> getFailures() {
		return failures;
	}

	public void setFailures(List<Integer> failures) {
		this.failures = failures;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public int getSons() {
		return sons;
	}

	public void setSons(int sons) {
		this.sons = sons;
	}

}
