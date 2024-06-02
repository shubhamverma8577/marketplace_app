/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.media.testing_proj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.media.testing_proj.Bean.SupportBean;
import com.media.testing_proj.Bean.ImageUploadRequest;
import com.media.testing_proj.Bean.User;
import com.media.testing_proj.services.CustomerManagement;
import com.media.testing_proj.services.UserService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ShubhamVerma
 */

@Controller

public class Modelcontroller {
        @Autowired
    private UserService userService;
    private static final Logger LOG = Logger.getLogger(Modelcontroller.class.getName());

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("username", new User());
        return mav;
    }

    @RequestMapping(value = "/login-auth", method = RequestMethod.POST)
    public String loginAuth(@ModelAttribute User user, HttpServletRequest request) {

        System.out.println(user.getUsername());
        User oauthUser = userService.login(user.getUsername(), user.getPassword(), request);
        System.out.println(oauthUser);
        System.out.println(user.getUsername()+"  "+user.getPassword());
        System.out.println(oauthUser.getUsername()+"  "+oauthUser.getPassword());
        LOG.log(Level.INFO, "---UserController{0}", oauthUser);
        if (getMD5(user.getPassword()).equals(oauthUser.getPassword())) {
            
        return "redirect:/home";
        } else {
          return "redirect:/login?invalidCreds=Y";
          
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
       @PostMapping(value = {"/register"})
    public void createSupportTicket(HttpServletRequest request, HttpServletResponse response) {
        try {
        PrintWriter out = response.getWriter();
        
        CustomerManagement cm = new CustomerManagement();
        System.out.println("request has been recieved....");
         String Username = request.getParameter("username");
        String Password = request.getParameter("password");
        String User_login_id = request.getParameter("user_login_id");
        String Id = "0";
        String Email_id = request.getParameter("Email_id");
        System.out.println(Username + "," + Password + "," + User_login_id + "," + Id + "," + Email_id);
        SupportBean customer = new SupportBean();
        customer.setUsername(Username);
        customer.setPassword(Password);
        customer.setUser_login_id(User_login_id);
        customer.setId(Id);
        customer.setEmail_id(Email_id);
        int createdTicket = cm.saveSupportData(customer, request);
        JSONObject res = new JSONObject();
        res.put("username", Username);
        res.put("Password", Password);
        res.put("User_login_id", User_login_id);
        out.write(res + "");
        }catch(Exception e){
            e.printStackTrace();
                 }
    }
      @RequestMapping(value = {"/getUserinfo"})
    public void getTicketData(String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            CustomerManagement cm = new CustomerManagement();
            System.out.println("id - " + id);
            org.json.JSONArray enrollList = cm.getTicketIdData(id);
            System.out.println("hello ticket data - " + new Date());
            System.out.println(enrollList);
            out.print(enrollList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 @PostMapping(value = {"/updateUserInfo"})
    public void updateUserInfo(@RequestBody SupportBean customer, HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();

            CustomerManagement cm = new CustomerManagement();
            System.out.println("Request has been received....");
            System.out.println(customer.getUsername() + "," + customer.getPassword() + "," + customer.getEmail_id() + "," + customer.getUser_login_id());
            
            int updatedUserInfo = cm.updateUserInfo(customer, request);
            
            // You can send a response back to the client if needed.
            out.println("Data updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     @RequestMapping(value = {"/changeUserpasswordRequest"})
public void changePasswordRequest(HttpServletRequest request, HttpServletResponse response) {
    try {
        PrintWriter out = response.getWriter();
        CustomerManagement cm = new CustomerManagement();
        System.out.println("Change password....");
        String emailId = request.getParameter("email"); // Retrieve the email from the request body
        System.out.println("Email ID: " + emailId);
        SupportBean customer = new SupportBean();
        customer.setEmail_id(emailId);
        JSONArray justifyValue = cm.changePasswordRequest(customer, request); // Remove additional parameters
        
        JSONObject res = new JSONObject();
        res.put("username", customer.getUsername());
        res.put("user_login_id", customer.getUser_login_id());
        res.put("encodedRandomCode", customer.getEncodedRandomCode());
        res.put("resetPasswordURL", customer.getResetPasswordURL());
        out.write(res + "");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@PostMapping(value = { "/getVerifyUser" })
public void getVerifyUser(String code, String secureCode, HttpServletRequest request, HttpServletResponse response) {
    try {
        PrintWriter out = response.getWriter();
        CustomerManagement cm = new CustomerManagement();
        System.out.println("randomcode - " + code);
        System.out.println("secureCode - " + secureCode);
        SupportBean customer = new SupportBean();
        
        int updatePassword = cm.getVerifyUserPassword(code, secureCode);
        org.json.JSONObject res = new org.json.JSONObject();

        if (updatePassword == 0) {
            res.put("userid", "true");
        } else {
            res.put("userid", "false");
        }

        out.write(res.toString());
    } catch (IOException e) {
        // Handle IOException appropriately (e.g., log or return an error response)
        e.printStackTrace();
    } catch (Exception e) {
        // Handle other exceptions appropriately (e.g., log or return an error response)
        e.printStackTrace();
    }
}

@PostMapping(value = { "/getChangePassword" })
public void getChangePassword(String confirmpassword, String code, HttpServletRequest request, HttpServletResponse response) {
    try (PrintWriter out = response.getWriter()) {
        CustomerManagement cm = new CustomerManagement();
        System.out.println("password - " + confirmpassword + " code - " + code);
        int updatePassword = cm.getChangePassword(confirmpassword, code);
        org.json.JSONObject res = new org.json.JSONObject();

        if (updatePassword == 0) {
            res.put("userid", "true");
        } else {
            res.put("userid", "false");
        }

        out.write(res.toString());
    } catch (IOException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

 @PostMapping(value = {"/uploadlogo"})
 public void getLogo(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ImageUploadRequest imageUploadRequest = objectMapper.readValue(jsonBuilder.toString(), ImageUploadRequest.class);

            System.out.println("imageFile-->" + imageUploadRequest.getImageFile());
            System.out.println("userloginid-->" + imageUploadRequest.getUserloginid());

            CustomerManagement cm = new CustomerManagement();
            int storelogo = cm.uploadCompnyLogo(imageUploadRequest.getImageFile(), imageUploadRequest.getUserloginid());
            System.out.println("hello1 - " + new Date());
            out.print(storelogo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
   @RequestMapping(value = {"/getuploadlogo"}, method = RequestMethod.POST)
public void getuploadlogo(@RequestParam("userloginid") String userloginid, HttpServletRequest request, HttpServletResponse response) {
    try {
        PrintWriter out = response.getWriter();
        CustomerManagement cm = new CustomerManagement();
        System.out.println("userloginid - " + userloginid);
        JSONArray enrollList = cm.getuploadCompnyLogo(userloginid,request);
        System.out.println("userloginid getuploadlogo - " + new Date());

        out.print(enrollList);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@RequestMapping(value = "/userdeletelogo", method = RequestMethod.POST)
public void getdeleteUserLogo(@RequestParam("userloginid") String userloginid, HttpServletRequest request, HttpServletResponse response) {
    try {
        PrintWriter out = response.getWriter();
        CustomerManagement cm = new CustomerManagement();
        System.out.println("userloginid - " + userloginid);
        JSONArray enrollList = cm.deleteCompnyLogo(userloginid);
        System.out.println("userloginid userdeletelogo - " + new Date());

        out.print(enrollList);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@RequestMapping(value = "/uploadBanners", method = RequestMethod.POST)
public void getaddBanners(@RequestBody Map<String, String> requestParams, HttpServletRequest request, HttpServletResponse response) {
    try {
        PrintWriter out = response.getWriter();
        CustomerManagement cm = new CustomerManagement();

        String userloginid = requestParams.get("userloginid");
        String base64Image = requestParams.get("base64Image");
        String email = requestParams.get("email");
        String company_name = requestParams.get("company_name");
        String bannerId = requestParams.get("bannerId");
        String bannerVarify = requestParams.get("bannerVarify");

        System.out.println("userloginid - " + userloginid);
        System.out.println("email - " + email);
        System.out.println("company_name - " + company_name);
        System.out.println("bannerId - " + bannerId);
        System.out.println("bannerVarify - " + bannerVarify);
        
        JSONArray enrollList = cm.addBanners(base64Image, userloginid, email, company_name,bannerId,bannerVarify,request);
        System.out.println("base64Image,userloginid,email,company_name - " + new Date());

        out.print(enrollList);
    } catch (Exception e) {
        e.printStackTrace();
    }
}


       @RequestMapping(value = {"/", "/index"})
    public ModelAndView homeView() {
        ModelAndView view = new ModelAndView("index");
        return view;
    }
    @RequestMapping(value = {"/app-email", "/home"})
    public ModelAndView CustomertableView() {
        ModelAndView view = new ModelAndView("app-email");
        return view;
    }
     @RequestMapping(value = {"/User-register", "/Register"})
    public ModelAndView RegisterdetailsView() {
        ModelAndView view = new ModelAndView("User-register");
        return view;
    }
    @RequestMapping(value = {"/Forgot-Password-Request", "/Forgot-Password-Request"})
    public ModelAndView ForgotpasswordrequestView() {
        ModelAndView view = new ModelAndView("Forgot-Password-Request");
        return view;
    }
    @RequestMapping(value = {"/varifaiy-user", "/varifaiy-user"})
    public ModelAndView VarifaiyuserView() {
        ModelAndView view = new ModelAndView("varifaiy-user");
        return view;
    }
     @RequestMapping(value = {"/Change-password", "/Change-password"})
    public ModelAndView ChangepasswordView() {
        ModelAndView view = new ModelAndView("Change-password");
        return view;
    }
     @RequestMapping(value = {"/Account_Info", "/Add-Account"})
    public ModelAndView CreateAccountView() {
        ModelAndView view = new ModelAndView("Account_Info");
        return view;
    }
    
    @RequestMapping(value = {"/Ui-Settings", "/Ui-Settings"})
    public ModelAndView SettingView() {
        ModelAndView view = new ModelAndView("Ui-Settings");
        return view;
    }
    
        @RequestMapping(value = "/logout")
public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate();
    }
    return "redirect:/";  //Where you go after logout here.
}
}
