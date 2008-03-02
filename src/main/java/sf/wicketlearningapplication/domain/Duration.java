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


import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Embeddable
public class Duration
  implements Serializable, Comparable<Duration>
{

  private static final long serialVersionUID = 67206557276146560L;

  private int duration;

  private DurationType durationType;

  public Duration()
  {
  }

  public Duration(final int duration, final DurationType durationType)
  {
    this.duration = duration;
    this.durationType = durationType;
  }

  public int compareTo(final Duration otherDuration)
  {
    final long thisDurationLength = duration * durationType.getSeconds();
    final long otherDurationLength = otherDuration.duration
                                     * otherDuration.durationType.getSeconds();
    return (int) (thisDurationLength - otherDurationLength);
  }

  public int getDuration()
  {
    return duration;
  }

  public DurationType getDurationType()
  {
    return durationType;
  }

  public void setDuration(final int duration)
  {
    this.duration = duration;
  }

  public void setDurationType(final DurationType durationType)
  {
    this.durationType = durationType;
  }

  @Override
  public String toString()
  {
    return ReflectionToStringBuilder.toString(this,
                                              ToStringStyle.MULTI_LINE_STYLE);
  }

}
