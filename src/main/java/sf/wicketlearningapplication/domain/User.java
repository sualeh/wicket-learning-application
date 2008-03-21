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

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
public class User
  implements Serializable, Comparable<User>
{

  private static final long serialVersionUID = -2683387699863424341L;

  private Long id;
  private String name;
  private String username;
  private String password;

  public int compareTo(final User o)
  {
    return (int) (id - o.id);
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final User other = (User) obj;
    if (id == null)
    {
      if (other.id != null)
      {
        return false;
      }
    }
    else if (!id.equals(other.id))
    {
      return false;
    }
    if (name == null)
    {
      if (other.name != null)
      {
        return false;
      }
    }
    else if (!name.equals(other.name))
    {
      return false;
    }
    if (password == null)
    {
      if (other.password != null)
      {
        return false;
      }
    }
    else if (!password.equals(other.password))
    {
      return false;
    }
    if (username == null)
    {
      if (other.username != null)
      {
        return false;
      }
    }
    else if (!username.equals(other.username))
    {
      return false;
    }
    return true;
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

  public String getPassword()
  {
    return password;
  }

  public String getUsername()
  {
    return username;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + (id == null? 0: id.hashCode());
    result = prime * result + (name == null? 0: name.hashCode());
    result = prime * result + (password == null? 0: password.hashCode());
    result = prime * result + (username == null? 0: username.hashCode());
    return result;
  }

  public void setId(final Long id)
  {
    this.id = id;
  }

  public void setName(final String name)
  {
    this.name = name;
  }

  public void setPassword(final String password)
  {
    this.password = password;
  }

  public void setUsername(final String userName)
  {
    username = userName;
  }

  @Override
  public String toString()
  {
    return ReflectionToStringBuilder.toString(this,
                                              ToStringStyle.MULTI_LINE_STYLE);
  }

}
