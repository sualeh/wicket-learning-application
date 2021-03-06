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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@NamedQuery(name = "count", query = "select count(b.id) from Bug b, User u where (u = :owner and u.admin = true) or (b.owner = :owner and b.owner = u)")
public class Bug
  implements Serializable
{

  private static final long serialVersionUID = 67206557276146560L;

  private Long id;
  private String summary;
  private Date dueByDate;
  private Integer estimatedHours;
  private Severity severity;
  private User owner;

  @Temporal(TemporalType.DATE)
  public Date getDueByDate()
  {
    if (dueByDate != null)
    {
      return new Date(dueByDate.getTime());
    }
    else
    {
      return null;
    }
  }

  public Integer getEstimatedHours()
  {
    return estimatedHours;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId()
  {
    return id;
  }

  @ManyToOne
  public User getOwner()
  {
    return owner;
  }

  @Column(nullable = false)
  public Severity getSeverity()
  {
    return severity;
  }

  @Column(nullable = false)
  public String getSummary()
  {
    return summary;
  }

  public void setDueByDate(final Date dueByDate)
  {
    if (dueByDate != null)
    {
      this.dueByDate = new Date(dueByDate.getTime());
    }
  }

  public void setEstimatedHours(final Integer estimatedHours)
  {
    this.estimatedHours = estimatedHours;
  }

  public void setId(final Long id)
  {
    this.id = id;
  }

  public void setOwner(final User owner)
  {
    this.owner = owner;
  }

  public void setSeverity(final Severity severity)
  {
    this.severity = severity;
  }

  public void setSummary(final String summary)
  {
    this.summary = summary;
  }

  @Override
  public String toString()
  {
    return ReflectionToStringBuilder.toString(this,
                                              ToStringStyle.MULTI_LINE_STYLE);
  }

}
