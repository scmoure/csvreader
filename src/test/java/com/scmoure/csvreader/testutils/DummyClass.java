package com.scmoure.csvreader.testutils;

import java.util.List;

import com.scmoure.csvreader.annotations.CSVColumn;
import com.scmoure.csvreader.annotations.CSVObjectArray;

public class DummyClass {

	@CSVColumn(column = 2)
	private String name;

	@CSVColumn(column = 1)
	private Integer age;

	@CSVColumn(column = 0)
	private int sons;

	@CSVObjectArray(startingColumn = 3, endingColumn = 7)
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
