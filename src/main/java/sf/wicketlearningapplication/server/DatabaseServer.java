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
      e.printStackTrace();
      System.exit(1);
    }
  }

  public void start()
  {
    LOGGER.log(Level.FINE, toString() + " - Setting up database");
    Server.main(new String[] {
        "-database.0",
        "hsqldb.wicketlearningapplication."
            + String.valueOf(System.currentTimeMillis()).substring(7),
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
