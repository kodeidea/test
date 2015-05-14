package com.test.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.models.User;
import com.test.models.UserDao;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author
 */
@Controller
public class UserController {

  // ==============
  // PRIVATE FIELDS
  // ==============

  @Autowired
  private UserDao userDao;
  
  // ==============
  // PUBLIC METHODS
  // ==============
  
  /**
   * Create a new user and save it in the database.
   * 
   * @param email user email
   * @param name use name
   * @return a string describing if the user is succesfully created or not.
   */
  @RequestMapping("/create")
  @ResponseBody
  public String create(String email, String name) {
    User user = null;
    try {
      user = new User(email, name);
      userDao.save(user);
    }
    catch (Exception ex) {
      return "Error creating the user: " + ex.toString();
    }
    return "User succesfully created! (id = " + user.getId() + ")";
  }
  
  /**
   * Delete the user having the passed id.
   * 
   * @param id the id of the user to delete
   * @return a string describing if the user is succesfully deleted or not.
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String delete(long id) {
    try {
      User user = new User(id);
      userDao.delete(user);
    }
    catch (Exception ex) {
      return "Error deleting the user:" + ex.toString();
    }
    return "User succesfully deleted!";
  }
  
  /**
   * Return the id for the user having the passed email.
   * 
   * @param email the email to search in the database.
   * @return the user id or a message error if the user is not found.
   */
  @RequestMapping("/get-by-email")
  @ResponseBody
  public String getByEmail(String email) {
    String userId;
    String userName;
    try {
      User user = userDao.findByEmail(email);
      userId = String.valueOf(user.getId());
      userName = user.getName();
    }
    catch (Exception ex) {
      return "User not found";
    }
    return "The user id is: " + userId + "<br>" + "The user name is: " + userName;
  }
  
  /**
   * Update the email and the name for the user in the database having the
   * passed id.
   * 
   * @param id the id for the user to update.
   * @param email the new email.
   * @param name the new name.
   * @return a string describing if the user is succesfully updated or not.
   */
  @RequestMapping("/update")
  @ResponseBody
  public String updateUser(long id, String email, String name) {
    try {
      User user = userDao.findOne(id);
      user.setEmail(email);
      user.setName(name);
      userDao.save(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User succesfully updated!";
  }

} // class UserController