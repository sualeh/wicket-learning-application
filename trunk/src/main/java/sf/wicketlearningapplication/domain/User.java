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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( {
  @NamedQuery(name = "authentication", query = "select u from User u where u.username = :username and u.password = :password"),
})
public class User
  implements Serializable
{

  private static final long serialVersionUID = -2683387699863424341L;

  private Long id;
  private String name;
  private String username;
  private String password;
  private boolean isAdmin;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId()
  {
    return id;
  }

  @Column(nullable = false, unique = true)
  public String getName()
  {
    return name;
  }

  @Column(nullable = false)
  public String getPassword()
  {
    return password;
  }

  @Column(nullable = false, unique = true)
  public String getUsername()
  {
    return username;
  }

  public boolean isAdmin()
  {
    return isAdmin;
  }

  public void setAdmin(final boolean isAdmin)
  {
    this.isAdmin = isAdmin;
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
    return getName();
  }

}
