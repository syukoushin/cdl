package com.ibm.core.util;


public class ExcelCellContent {
	
	private int row;            // 单元格所在行
	private int rowSpan;		// 单元格跨的行数
	private int col;			// 单元格所在列
	private int colSpan;		// 单元格跨的列数
	private String text;		// 单元格内容
	private String alignment;	// 单元格内容位置
	private int colWidth; 	// 单元格宽度
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAlignment() {
		return alignment;
	}
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	public int getColWidth() {
		return colWidth;
	}
	public void setColWidth(int colWidth) {
		this.colWidth = colWidth;
	}

}
