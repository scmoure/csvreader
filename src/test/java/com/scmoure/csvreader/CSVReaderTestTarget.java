package com.scmoure.csvreader;

public class CSVReaderTestTarget {

	@CSVColumn(name = "COLUMN1")
	private int column1;

	@CSVColumn(name = "column2")
	private Integer column2;

	@CSVColumn(name = "CoLumn3")
	private String column3;

	public int getColumn1() {
		return column1;
	}

	public void setColumn1(int column1) {
		this.column1 = column1;
	}

	public Integer getColumn2() {
		return column2;
	}

	public void setColumn2(Integer column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}
}
