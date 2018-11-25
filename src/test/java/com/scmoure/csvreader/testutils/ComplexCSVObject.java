package com.scmoure.csvreader.testutils;

import com.scmoure.csvreader.annotations.CSVObject;

public class ComplexCSVObject {

	@CSVObject(startingColumn = 0)
	private DummyClass innerObject;

	public DummyClass getInnerObject() {
		return innerObject;
	}

	public void setInnerObject(DummyClass innerObject) {
		this.innerObject = innerObject;
	}
}
