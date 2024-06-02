/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.media.testing_proj.services;

import com.media.testing_proj.Bean.User;
import com.media.testing_proj.dbconnection.ConnectionManager;
import com.media.testing_proj.repository.UserRepository;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ShubhamVerma
 */
@Service
public class UserService {
     @Autowired
    private UserRepository repo;
    private static final Logger LOG = Logger.getLogger(UserService.class.getName());
public User login(String username, String password, HttpServletRequest request) {
    // Create a connection manager to manage database connections
    ConnectionManager mycon = new ConnectionManager();
    try (Connection conn = mycon.getDBConnection()) {
        // Prepare an SQL query to select a user with the provided username (id)
        PreparedStatement ps = conn.prepareStatement("select * from user_login where username=?");
        ps.setString(1, username);

        // Log the prepared statement for debugging
        LOG.log(Level.INFO, "ps--{0}", ps);

        // Execute the query and retrieve the result in a ResultSet
        ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // If the passwords match, return the User object with user information
                if (rs.getString("password").equals(getMD5(password))) {
                    // Set the "username" session attribute
                    request.getSession().setAttribute("username", rs.getString("username"));
                     request.getSession().setAttribute("email", rs.getString("Email_id"));
                     request.getSession().setAttribute("user_login_id", rs.getString("user_login_id"));
                      request.getSession().setAttribute("company_name", rs.getString("company_name"));
                      request.getSession().setAttribute("banner", rs.getString("banner"));

                    return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
                } else {
                    // Passwords do not match, return an error message
                    return new User(0L, "ERROR~Invalid Password", "");
                }
            } else {
                // User not found, return an error message
                return new User(0L, "ERROR~Invalid UserId", "");
            }
    } catch (SQLException e) {
        // Handle any SQLException (database error) and log the error
        LOG.log(Level.SEVERE, "Error during login", e);
        return new User(0L, "ERROR~" + e.getMessage(), "");
    }
}
public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
