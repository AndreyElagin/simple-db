package com.github.andreyelagin.simpledb;

import jdk.jfr.Unsigned;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.andreyelagin.simpledb.Constants.ROW_SIZE;

public class Table {

  public static final int PAGE_SIZE = 4096;
  public static final int TABLE_MAX_PAGES = 100;
  public static final int ROWS_PER_PAGE = PAGE_SIZE / ROW_SIZE;
  public static final int TABLE_MAX_ROWS = ROWS_PER_PAGE * TABLE_MAX_PAGES;

  private int numRows;
  private ByteBuffer[] pages = new ByteBuffer[TABLE_MAX_PAGES];

  public Row deserialize(int pos) {
    return null;
  }

  public void addRow(Row row) {
    if (numRows > TABLE_MAX_ROWS) {
      throw new RuntimeException("Table is full of shit");
    }

    var page = pages[numRows / ROWS_PER_PAGE];
    if (page == null) {
      page = ByteBuffer.allocateDirect(PAGE_SIZE);
      pages[numRows / ROWS_PER_PAGE] = page;
    }

    page.position((numRows % ROWS_PER_PAGE) * ROW_SIZE);
    page.put(row.serialize());

    numRows++;
  }

  public List<Row> getAllRows() {
    var rows = new ArrayList<Row>();
    for (int i = 0; i < numRows; i++) {
      var page = pages[i / ROWS_PER_PAGE];
      rows.add(Row.deserialize(page, i));
    }
    return rows;
  }

  public void clearTable() {
    Arrays.fill(pages, null);
  }

  public int getNumRows() {
    return numRows;
  }

  public void setNumRows(int numRows) {
    this.numRows = numRows;
  }

  public ByteBuffer[] getPages() {
    return pages;
  }
}
