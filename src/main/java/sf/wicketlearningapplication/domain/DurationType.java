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
package sf.wicketlearningapplication.domain;


import java.util.Arrays;
import java.util.List;

public enum DurationType
{
  minutes(60), hours(60 * 60), days(24 * 60 * 60), weeks(7 * 24 * 60 * 60);

  public static List<DurationType> list()
  {
    return Arrays.asList(DurationType.values());
  }

  private final long seconds;

  private DurationType(final long seconds)
  {
    this.seconds = seconds;
  }

  long getSeconds()
  {
    return seconds;
  }

}
