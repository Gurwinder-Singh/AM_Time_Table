
/**
 * Do Not Modify This Class.
 */
package com.gdev.timetable.db;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
class ScriptRunner {
 private static final String DEFAULT_T_DELIMITER = ";";
    private Connection connection;
    private boolean stopOnError;
    private boolean autoCommit;
    private PrintWriter logWriter = new PrintWriter(System.out);
    private PrintWriter errorLogWriter = new PrintWriter(System.err);
    private String delimiter = DEFAULT_T_DELIMITER;
    private boolean fullLineDelimiter = false;

    public ScriptRunner(Connection con, boolean autoCommit, boolean stopOnError) {
        this.connection = con;
        this.autoCommit = autoCommit;
        this.stopOnError = stopOnError;
    }

    public void setDelimiter(String delimiter, boolean fullLineDelimiter) {
        this.delimiter = delimiter;
        this.fullLineDelimiter = fullLineDelimiter;
    }

    public void runScript(Reader reader) throws IOException, SQLException {
        try {
            boolean originalAutoCommit = connection.getAutoCommit();
            try {
                if (originalAutoCommit != this.autoCommit) {
                    connection.setAutoCommit(autoCommit);
                }
                runScript(connection, reader);
            } finally {
                connection.setAutoCommit(originalAutoCommit);
            }
        } catch (IOException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error running Script. Cause: " + e.getMessage());
        }
    }

    private void runScript(Connection con, Reader reader) throws IOException, SQLException {
        StringBuffer command = null;
        try {
            LineNumberReader lineReader = new LineNumberReader(reader);
            String line = null;
            while ((line = lineReader.readLine()) != null) {
                if (command == null) {
                    command = new StringBuffer();
                }
                String trimmedLine = line.trim();
                if (trimmedLine.startsWith("--")) {
//                    println(trimmedLine);
                } else if (trimmedLine.length() < 1 || trimmedLine.startsWith("//")) {
                } else if (trimmedLine.length() < 1 || trimmedLine.startsWith("--")) {
                } else if (!fullLineDelimiter && trimmedLine.endsWith(getDelimiter())
                        || fullLineDelimiter && trimmedLine.equals(getDelimiter())) {
                    command.append(line.substring(0, line.lastIndexOf(getDelimiter())));
                    command.append(" ");
                    Statement statement = con.createStatement();
//                    println(command);
                    boolean hasResult = false;
                    if (stopOnError) {
                        hasResult = statement.execute(command.toString());
                    } else {
                        try {
                            statement.execute(command.toString());
                        } catch (SQLException e) {
                            e.fillInStackTrace();
                            printlnError("Error Executing: " + command);
                            printlnError(e);
                        }
                    }
                    if (autoCommit && !con.getAutoCommit()) {
                        con.commit();
                    }
                    ResultSet rs = statement.getResultSet();
                    if (hasResult && rs != null) {
                        ResultSetMetaData md = rs.getMetaData();
                        int cols = md.getColumnCount();
                        for (int i = 0; i < cols; i++) {
                            String name = md.getColumnLabel(i);
                            print(name + "\t");
                        }
                        println("");
                        while (rs.next()) {
                            for (int i = 0; i < cols; i++) {
                                String value = rs.getString(i);
                                print(value + "\t");
                            }
                            println("");
                        }
                    }
                    command = null;
                    try {
                        statement.close();
                    } catch (Exception e) {
                    }
                    Thread.yield();
                } else {
                    command.append(line);
                    command.append(" ");
                }
            }
            if (!autoCommit) {
                con.commit();
            }
        } catch (SQLException e) {
        } catch (IOException e) {
        } finally {
            con.rollback();
            flush();
        }

    }

    private String getDelimiter() {
        return delimiter;
    }

    private void print(Object o) {
        if (logWriter != null) {
            System.out.print(o);
        }
    }

    private void println(Object o) {
        if (logWriter != null) {
            logWriter.println(o);
            System.out.println(o);
        }
    }

    private void printlnError(Object o) {
        if (errorLogWriter != null) {
            errorLogWriter.println(o);
            System.err.println(o);
        }
    }

    private void flush() {
        if (logWriter != null) {
            logWriter.flush();
        }
        if (errorLogWriter != null) {
            errorLogWriter.flush();
        }
    }
}
