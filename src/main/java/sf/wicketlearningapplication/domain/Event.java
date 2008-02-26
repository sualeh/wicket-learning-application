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
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
public class Event
  implements Serializable
{

  private static final long serialVersionUID = 67206557276146560L;

  private Long id;
  private String name;
  private Date startDate;
  private Duration duration;
  private User owner;

  public Event()
  {
    duration = new Duration();
  }

  @Embedded
  public Duration getDuration()
  {
    return duration;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId()
  {
    return id;
  }

  @Basic
  public String getName()
  {
    return name;
  }

  @ManyToOne
  public User getOwner()
  {
    return owner;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getStartDate()
  {
    return startDate;
  }

  public void setDuration(final Duration duration)
  {
    this.duration = duration;
  }

  public void setId(final Long id)
  {
    this.id = id;
  }

  public void setName(final String name)
  {
    this.name = name;
  }

  public void setOwner(final User owner)
  {
    this.owner = owner;
  }

  public void setStartDate(final Date startDate)
  {
    this.startDate = startDate;
  }

  @Override
  public String toString()
  {
    return ReflectionToStringBuilder.toString(this,
                                              ToStringStyle.MULTI_LINE_STYLE);
  }

}
