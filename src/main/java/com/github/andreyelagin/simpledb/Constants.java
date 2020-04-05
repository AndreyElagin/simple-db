package com.github.andreyelagin.simpledb;

public class Constants {

  public static final int COLUMN_USERNAME_SIZE = 32;
  public static final int COLUMN_EMAIL_SIZE = 255;

  public static final int ID_SIZE = 4;
  public static final int USERNAME_SIZE = 32;
  public static final int EMAIL_SIZE = 255;
  public static final int ID_OFFSET = 0;
  public static final int USERNAME_OFFSET = ID_OFFSET + ID_SIZE;
  public static final int EMAIL_OFFSET = USERNAME_OFFSET + USERNAME_SIZE;
  public static final int ROW_SIZE = ID_SIZE + USERNAME_SIZE + EMAIL_SIZE;
}
