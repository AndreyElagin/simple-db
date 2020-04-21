package com.github.andreyelagin.simpledb;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

import static com.github.andreyelagin.simpledb.Constants.*;
import static com.github.andreyelagin.simpledb.Table.ROWS_PER_PAGE;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Row {
  private final int id;
  private final String userName;
  private final String email;

  public Row(int id, String userName, String email) {
    this.id = id;
    this.userName = userName;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public String getEmail() {
    return email;
  }

  public byte[] serialize() {
    var arr = ByteBuffer.allocate(ID_SIZE + USERNAME_SIZE + EMAIL_SIZE);
    arr.putInt(id);

    if (id < 0) {
      throw new RuntimeException("id can't be negative");
    }
    if (userName.length() > USERNAME_SIZE) {
      throw new RuntimeException("user name to long");
    }
    if (email.length() > EMAIL_SIZE) {
      throw new RuntimeException("email to long");
    }

    arr.position(USERNAME_OFFSET);
    arr.put(userName.getBytes(UTF_8), 0, userName.length());

    arr.position(EMAIL_OFFSET);
    arr.put(email.getBytes(UTF_8), 0, email.length());

    return arr.array();
  }

  public static Row deserialize(ByteBuffer page, int row) {
    var beginOffset = (row % ROWS_PER_PAGE) * ROW_SIZE;

    int id = page.getInt(beginOffset);

    byte[] name = new byte[USERNAME_SIZE];
    for (int i = USERNAME_OFFSET + beginOffset, j = 0; i < USERNAME_SIZE + beginOffset; i++, j++) {
      name[j] = page.get(i);
    }
    var userName = new String(name, Charset.forName(UTF_8.name())).trim();

    byte[] email = new byte[EMAIL_SIZE];
    for (int i = EMAIL_OFFSET + beginOffset, j = 0; i < EMAIL_SIZE + beginOffset; i++, j++) {
      email[j] = page.get(i);
    }
    var userEmail = new String(email, Charset.forName(UTF_8.name())).trim();

    return new Row(id, userName, userEmail);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Row row = (Row) o;
    return id == row.id &&
        Objects.equals(userName, row.userName) &&
        Objects.equals(email, row.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userName, email);
  }

  @Override
  public String toString() {
    return "Row{" +
        "id=" + id +
        ", userName='" + userName + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
