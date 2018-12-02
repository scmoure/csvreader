package com.scmoure.csvreader.testutils;

import java.util.List;

import com.scmoure.csvreader.annotations.CSVObjectList;

public class ListTestClass {

	@CSVObjectList(startingColumn = 0, endingColumn = 5, cycle = 2)
	private List<InnerTestClass> innerList;

	public List<InnerTestClass> getInnerList() {
		return innerList;
	}

	public void setInnerList(List<InnerTestClass> innerList) {
		this.innerList = innerList;
	}

}
