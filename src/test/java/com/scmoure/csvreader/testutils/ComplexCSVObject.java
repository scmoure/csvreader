package com.scmoure.csvreader.testutils;

import com.scmoure.csvreader.annotations.CSVObject;

public class ComplexCSVObject {

	@CSVObject(startingColumn = 0)
	private TestClass innerObject;

	public TestClass getInnerObject() {
		return innerObject;
	}

	public void setInnerObject(TestClass innerObject) {
		this.innerObject = innerObject;
	}
}
