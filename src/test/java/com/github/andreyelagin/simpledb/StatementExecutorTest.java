package com.github.andreyelagin.simpledb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

import static com.github.andreyelagin.simpledb.StatementType.STATEMENT_INSERT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatementExecutorTest {

  private static final Random rand = new Random();

  @Test
  @DisplayName("should return exact number of rows which was inserted")
  void test1() {
    var table = new Table();
    var exec = new StatementExecutor(table);
    var expectedRow = new Row(666, "vasian", "google@goolag");
    var insertStatement = new Statement(STATEMENT_INSERT, expectedRow);

    exec.execute(insertStatement);

    assertThat(table.getAllRows().get(0), is(expectedRow));
  }

  @Test
  @DisplayName("should fail when table overflow")
  void test2() {
    var table = new Table();
    var executor = new StatementExecutor(table);

    assertThrows(
        RuntimeException.class,
        () -> IntStream
            .range(0, 1401)
            .mapToObj(id -> new Statement(
                STATEMENT_INSERT,
                new Row(
                    id,
                    generateRandomString(),
                    generateRandomString()
                ))
            )
            .forEach(executor::execute),
        "Table is full of shit"
    );
  }

  @Test
  @DisplayName("should fail to insert either long name or email")
  void test3() {
    var table = new Table();
    var exec = new StatementExecutor(table);
    var longNameRow = new Row(666, generateRandomString(33), "google@goolag");
    var longEmailRow = new Row(777, "vasian", generateRandomString(256));
    var nameStatement = new Statement(STATEMENT_INSERT, longNameRow);
    var emailStatement = new Statement(STATEMENT_INSERT, longEmailRow);

    assertThrows(
        RuntimeException.class,
        () -> exec.execute(nameStatement),
        "user name to long"
    );
    assertThrows(
        RuntimeException.class,
        () -> exec.execute(emailStatement),
        "email to long"
    );
  }

  @Test
  @DisplayName("should fail when id is negative")
  void test4() {
    var table = new Table();
    var exec = new StatementExecutor(table);
    var longNameRow = new Row(-666, generateRandomString(), "google@goolag");
    var nameStatement = new Statement(STATEMENT_INSERT, longNameRow);

    assertThrows(
        RuntimeException.class,
        () -> exec.execute(nameStatement),
        "id can't be negative"
    );
  }

  private String generateRandomString() {
    return generateRandomString(14);
  }

  private String generateRandomString(int length) {
    return rand.ints(48, 123)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(length)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}