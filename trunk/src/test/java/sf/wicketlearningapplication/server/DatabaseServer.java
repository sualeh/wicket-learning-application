/*
 * Copyright 2007-2008, Sualeh Fatehi <sualeh@hotmail.com>
 * 
 * This work is licensed under the Creative Commons Attribution 3.0 License. 
 * To view a copy of this license, visit 
 * http://creativecommons.org/licenses/by/3.0/ 
 * or send a letter to 
 * Creative Commons
 * 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package sf.wicketlearningapplication.server;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hsqldb.Server;

public class DatabaseServer
{

  private static final Logger LOGGER = Logger.getLogger(DatabaseServer.class
    .getName());

  static
  {
    try
    {
      Class.forName("org.hsqldb.jdbcDriver");
    }
    catch (final ClassNotFoundException e)
    {
      throw new RuntimeException("Cannot load database driver", e);
    }
  }

  public static void main(final String[] args)
    throws Exception
  {
    final DatabaseServer databaseServer = new DatabaseServer();
    databaseServer.start();
    TestUtility.createData();

    while (System.in.available() == 0)
    {
      Thread.sleep(5000);
    }
    databaseServer.stop();
  }

  private final File workingDirectory;

  public DatabaseServer()
  {
    this("./target");
  }

  public DatabaseServer(final String workingDirectory)
  {
    this.workingDirectory = new File(workingDirectory);
    try
    {
      File.createTempFile("dir", ".test", this.workingDirectory).mkdirs();
    }
    catch (final IOException e)
    {
      LOGGER.log(Level.WARNING, "", e);
    }
  }

  public void start()
  {
    LOGGER.log(Level.FINE, toString() + " - Setting up database");
    final String path = workingDirectory
                        + "\\"
                        + "hsqldb.wicketlearningapplication."
                        + String.valueOf(System.currentTimeMillis())
                          .substring(7);
    Server.main(new String[] {
        "-database.0",
        path,
        "-dbname.0",
        "wicketlearningapplication",
        "-silent",
        "false",
        "-trace",
        "true"
    });
  }

  public void stop()
  {
    Connection connection = null;
    Statement statement = null;
    try
    {
      connection = DriverManager
        .getConnection("jdbc:hsqldb:hsql://localhost/wicketlearningapplication",
                       "sa",
                       "");
      statement = connection.createStatement();
      statement.execute("SHUTDOWN");
      statement.close();
      connection.close();
    }
    catch (final SQLException e)
    {
      LOGGER.log(Level.WARNING, "", e);
    }
    finally
    {
      try
      {
        if (statement != null)
        {
          statement.close();
        }
      }
      catch (final SQLException e)
      {
        LOGGER.log(Level.WARNING, "", e);
      }
      try
      {
        if (connection != null)
        {
          connection.close();
        }
      }
      catch (final SQLException e)
      {
        LOGGER.log(Level.WARNING, "", e);
      }
    }
  }

}
