/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.media.testing_proj.services;

import com.media.testing_proj.Bean.SupportBean;
import com.media.testing_proj.controller.Modelcontroller;
import com.media.testing_proj.dbconnection.ConnectionManager;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Shubham Verma
 */
public class CustomerManagement {

    private static final Logger logger = LogManager.getLogger(CustomerManagement.class);
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 16;

    public int saveSupportData(SupportBean beanObj, HttpServletRequest request) {
        int flag = 0;
        int ticket_id = 0;
        try {
            PreparedStatement ps = null;
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();

            if (conn != null) {

                ps = conn.prepareStatement("INSERT INTO user_login (username, password, user_login_id, id, Email_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, beanObj.getUsername());
                String password = getMD5(beanObj.getPassword());
                ps.setString(2, password);
                ps.setInt(3, 0);
                ps.setInt(4, 0);
                ps.setString(5, beanObj.getEmail_id());

                // Execute the INSERT statement
                int rowsInserted = ps.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        ticket_id = generatedKeys.getInt(1);
                    }
                } else {
                    // Handle insert failure
                    flag = -1; // or some other error code
                }

                // Close the PreparedStatement and Connection
                ps.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            // Handle the exception appropriately
            flag = -1; // or some other error code
        }
        return flag;
    }

    public JSONArray getTicketIdData(String id) {
        JSONArray enrollList = new JSONArray();
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            String cond = "";
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();
            if (conn != null) {

                ps = conn.prepareStatement("SELECT username,password,Email_id,user_login_id FROM user_login WHERE id =?");
                ps.setString(1, id);

                System.out.println(ps);

                rs = ps.executeQuery();
                while (rs.next()) {
                    JSONObject SupportBean = new JSONObject();
                    SupportBean.put("username", rs.getString("username"));
                    SupportBean.put("password", rs.getString("password"));
                    SupportBean.put("Email_id", rs.getString("Email_id"));
                    SupportBean.put("user_login_id", rs.getString("user_login_id"));
                    enrollList.put(SupportBean);
                }

                conn.close();
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return enrollList;
    }

    public int updateUserInfo(SupportBean beanObj, HttpServletRequest request) {
        int flag = 0;
        try {
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();
            if (conn != null) {
                PreparedStatement ps = null;
                ps = conn.prepareStatement("UPDATE user_login SET username=?, password=?, Email_id=? WHERE user_login_id=?");
                ps.setString(1, beanObj.getUsername());
                ps.setString(2, beanObj.getPassword());
                ps.setString(3, beanObj.getEmail_id());
                ps.setString(4, beanObj.getUser_login_id());  // Use index 4 for user_login_id

                int rowsAffected = ps.executeUpdate(); // Execute the update

                if (rowsAffected > 0) {
                    flag = 1; // Update was successful
                }

                ps.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    public JSONArray changePasswordRequest(SupportBean beanObj, HttpServletRequest request)
            throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {

        int flag = 0;
        String userName = null;
        String userLoginId = null;
        String email = null;
        String encodedRandomCode = null;
        String resetPasswordURL = null;

        JSONArray enrollList = new JSONArray();
        try {
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();
            Modelcontroller mdc = new Modelcontroller();

            if (conn != null) {
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    // Create a prepared statement to fetch the user information
                    ps = conn.prepareStatement("SELECT username, user_login_id, Email_id FROM user_login WHERE Email_id = ? AND id = '0'");
                    ps.setString(1, beanObj.getEmail_id());

                    logger.info("Support data insertion Query change status " + ps);
                    rs = ps.executeQuery();

                    // Check if there are results
                    if (rs.next()) {
                        // Retrieve values from the result set
                        userName = rs.getString("username");
                        userLoginId = rs.getString("user_login_id");
                        email = rs.getString("Email_id");

                        // Generate a random code
                        String randomCode = generateRandomCode();

                        PreparedStatement psw = conn.prepareStatement("UPDATE user_login SET email_verification = ? WHERE user_login_id = ?");
                        encodedRandomCode = encryptCode(randomCode);
                        psw.setString(1, encodedRandomCode);
                        psw.setString(2, rs.getString("user_login_id"));

                        logger.info("Support data insertion Query change status " + psw);
                        psw.executeUpdate();// Encode the random code

                        // Encode the email for the secureCode
                        String secureCode = DatatypeConverter.printBase64Binary(email.getBytes("UTF-8"));

                        // Create the URL with the encrypted code and secureCode
                        resetPasswordURL = "http://localhost:8080/testing_proj/varifaiy-user?code=" + encodedRandomCode + "&secureCode=" + secureCode;

                        beanObj.setUsername(userName);
                        beanObj.setUser_login_id(userLoginId);
                        beanObj.setEncodedRandomCode(encodedRandomCode);
                        beanObj.setResetPasswordURL(resetPasswordURL);

                        // Use the retrieved values and URL in the sendMail method
//                    mdc.changePasswordRequest(request, response);
                        System.out.println("Email ID: " + email + "," + "userName: " + userName + "," + "userLoginId: " + userLoginId + "," + "resetPasswordURL: " + resetPasswordURL + "," + "encodedRandomCode: " + encodedRandomCode);
                    } else {
                        flag = -1;
                    }
                } finally {
                    // Close resources in a try-finally block
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }

                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enrollList;
    }

    public int getVerifyUserPassword(String code, String secureCode) throws UnsupportedEncodingException {
        int flag = -1; // Assume failure
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();

            byte[] emailBytes = Base64.getDecoder().decode(secureCode.getBytes("UTF-8"));
            String email = new String(emailBytes, "UTF-8");

            System.out.println("randomcode------------------->" + code);

            if (conn != null) {
                ps = conn.prepareStatement("SELECT Email_id,email_verification FROM user_login WHERE Email_id=? and email_verification=?");
                ps.setString(1, email);
                ps.setString(2, code);

                System.out.println("Query: " + ps);

                rs = ps.executeQuery();

                if (rs.next()) {
                    // User ID found in the database
                    String userId = rs.getString("Email_id");
                    String emailVerification = rs.getString("email_verification");

                    // Compare user ID with email
                    if (userId.equals(email) && emailVerification.equals(code)) {
                        flag = 0; // Match, set flag to success
                    }
                }

                conn.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public int getChangePassword(String confirmpassword, String code) throws UnsupportedEncodingException {
        int flag = -1; // Assume failure
        try {
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();
            System.out.println("confirmpassword--->" + confirmpassword + "code--->" + code);
            if (conn != null) {

                flag = 0;
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_login WHERE email_verification = ?");
                // ps.setString(1, hashedPassword);
                ps.setString(1, code);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String hashedPassword = getMD5(confirmpassword);
                    // Use the hashedPassword in your SQL query
                    ps = conn.prepareStatement("update user_login set password= ?,email_verification= 'Verified' where email_verification= ?");
                    ps.setString(1, hashedPassword);
                    ps.setString(2, code);
                    int rows = ps.executeUpdate();

                    if (rows > 0) {

                        // yes password updated
                    } else {
                        //no password hasnt updated
                    }
                } else {
                    //no password hasnt updated
                }

                // Apply MD5 hashing to the confirmpassword
                conn.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public int uploadCompnyLogo(String imageFile, String userloginid) {
        int flag = 0;
        try {
            PreparedStatement ps = null;
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();

            if (conn != null) {
                ps = conn.prepareStatement("UPDATE user_login SET logo=? WHERE user_login_id=?", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, imageFile);
                ps.setString(2, userloginid);

                int rowsInserted = ps.executeUpdate();
                flag = rowsInserted; // Set flag to the number of rows inserted/updated

                ps.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Log the exception
            flag = -1; // or some other error code
        }
        return flag;
    }

    public JSONArray getuploadCompnyLogo(String userloginid, HttpServletRequest request) {
        JSONArray enrollList = new JSONArray();
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            String cond = "";
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();
           
            if (conn != null) {

                ps = conn.prepareStatement("select user_login_id,logo,banner_id,banner_varify from user_login as ul join company_banner as cb on user_login_id=user_id  where user_id=?");

                ps.setString(1, userloginid);

                System.out.println("Subham Query" + ps);

                rs = ps.executeQuery();
                while (rs.next()) {

                    JSONObject SelectItem = new JSONObject();
                    SelectItem.put("user_login_id", rs.getString("user_login_id"));
                    SelectItem.put("logo", rs.getString("logo"));
                    SelectItem.put("banner_id", rs.getString("banner_id"));
                    SelectItem.put("banner_varify", rs.getString("banner_varify"));

                    enrollList.put(SelectItem);
                }
                conn.close();
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return enrollList;
    }

    public JSONArray deleteCompnyLogo(String userloginid) {
        JSONArray enrollList = new JSONArray();
        try {
            PreparedStatement ps = null;
            ConnectionManager mycon = new ConnectionManager();
            Connection conn = mycon.getDBConnection();
            if (conn != null) {
                // Prepare the UPDATE statement
                ps = conn.prepareStatement("UPDATE user_login SET logo = NULL WHERE user_login_id =?;");
                ps.setString(1, userloginid);
                System.out.println("Subham Query: " + ps);

                // Execute the UPDATE statement
                int rowsAffected = ps.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);

                // If needed, you can query back the updated data
                // Uncomment the below lines if you need to return the updated data
                /*
            ps = conn.prepareStatement("SELECT user_login_id, logo FROM user_login WHERE user_login_id =?");
            ps.setString(1, userloginid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject SelectItem = new JSONObject();
                SelectItem.put("user_login_id", rs.getString("user_login_id"));
                SelectItem.put("logo", rs.getString("logo"));
                enrollList.put(SelectItem);
            }
            rs.close();
                 */
                ps.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enrollList;
    }

    public JSONArray addBanners(String base64Image, String userloginid, String email, String company_name, String bannerId,String bannerVarify, HttpServletRequest request) {
    JSONArray enrollList = new JSONArray();
    ConnectionManager mycon = new ConnectionManager();

    

    String updateCompanyBannerSQL = "";
    String updateUserLoginSQL = "";

    switch (bannerVarify) {
        case "0":
            updateCompanyBannerSQL = "UPDATE company_banner SET banner1 = ?, user_id = ?, email = ?, company = ? WHERE banner_id = ?";
            updateUserLoginSQL = "UPDATE company_banner SET banner_varify = '1' WHERE bannerId = ?";
            break;
        case "1":
            updateCompanyBannerSQL = "UPDATE company_banner SET banner2 = ?, user_id = ?, email = ?, company = ? WHERE banner_id = ?";
            updateUserLoginSQL = "UPDATE company_banner SET banner_varify = '2' WHERE bannerId = ?";
            break;
        case "2":
            updateCompanyBannerSQL = "UPDATE company_banner SET banner3 = ?, user_id = ?, email = ?, company = ? WHERE banner_id = ?";
            updateUserLoginSQL = "UPDATE company_banner SET banner_varify = '3' WHERE bannerId = ?";
            break;
        case "3":
            updateCompanyBannerSQL = "UPDATE company_banner SET banner4 = ?, user_id = ?, email = ?, company = ? WHERE banner_id = ?";
            updateUserLoginSQL = "UPDATE company_banner SET banner_varify = '4' WHERE bannerId = ?";
            break;
        default:
            throw new IllegalArgumentException("Invalid banner case: " + bannerVarify);
    }

    try (Connection conn = mycon.getDBConnection()) {
        if (conn != null) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement psCompanyBanner = conn.prepareStatement(updateCompanyBannerSQL);
                 PreparedStatement psUserLogin = conn.prepareStatement(updateUserLoginSQL)) {

                // Update company_banner table
                psCompanyBanner.setString(1, base64Image);
                psCompanyBanner.setString(2, userloginid);
                psCompanyBanner.setString(3, email);
                psCompanyBanner.setString(4, company_name);
                psCompanyBanner.setString(5, bannerId);

                int rowsAffectedCompanyBanner = psCompanyBanner.executeUpdate();

                // Update user_login table
                psUserLogin.setString(1, bannerId);

                int rowsAffectedUserLogin = psUserLogin.executeUpdate();

                conn.commit(); // Commit transaction

                // Build the result JSON
                JSONObject result = new JSONObject();
                result.put("status", "success");
                result.put("rowsAffectedCompanyBanner", rowsAffectedCompanyBanner);
                result.put("rowsAffectedUserLogin", rowsAffectedUserLogin);
                enrollList.put(result);

            } catch (SQLException ex) {
                conn.rollback(); // Rollback transaction on error
                ex.printStackTrace();

                JSONObject error = new JSONObject();
                error.put("status", "error");
                error.put("message", ex.getMessage());
                enrollList.put(error);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JSONObject error = new JSONObject();
        error.put("status", "error");
        error.put("message", ex.getMessage());
        enrollList.put(error);
    }

    return enrollList;
}

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }

    private String encryptCode(String randomCode) {
        try {
            byte[] encodedBytes = Base64.getEncoder().encode(randomCode.getBytes());
            return new String(encodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
