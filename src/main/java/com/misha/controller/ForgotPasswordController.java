package com.misha.controller;


import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misha.model.User;
import com.misha.payload.request.ForgetPasswordRequest;
import com.misha.payload.request.ResetPasswordRequest;
import com.misha.payload.response.MessageResponse;
import com.misha.services.UserNotFoundException;
import com.misha.services.UserService;


import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private UserService service;
	
	
	@PostMapping("/forgot_password")
    public ResponseEntity<?> processForgotPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
    	String token = RandomString.make(30);
    	try {
    		service.updateResetPasswordToken(token, forgetPasswordRequest.getEmail());
    		//String url = request.getScheme() + "://" + request.getServerName();
    		//String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
    		String staticUrl = "https://reactmapmisha.shiftescape.com";

    		String resetPasswordLink = staticUrl +"/reset_password?token=" + token;
    		sendEmail(forgetPasswordRequest.getEmail(), resetPasswordLink);
    	}catch(UserNotFoundException ex) {
    		return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Please enter a valid email address!"));
    	}catch (UnsupportedEncodingException | MessagingException e) {
    		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
	    
	    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException{
	    	
	    	MimeMessage message = mailSender.createMimeMessage();
	    	MimeMessageHelper helper = new MimeMessageHelper(message);
	    	
	    	helper.setFrom("contact@mishinfotech.com", "MishaInfotech Support");
	    	helper.setTo(recipientEmail);
	    	String subject = "Here's the link to reset your password";
	    	
	    	String content = "<p>Hello,</p>"
	                + "<p>You have requested to reset your password.</p>"
	                + "<p>Click the link below to change your password:</p>"
	                + "<p><a href=\"" + link + "\">Change my password</a></p>"
	                + "<br>"
	                + "<p>Ignore this email if you do remember your password, "
	                + "or you have not made the request.</p>";
	         
	        helper.setSubject(subject);
	         
	        helper.setText(content, true);
	         
	        mailSender.send(message);
	    }  
	     
	    
	     
	    @PostMapping("/reset_password")
	    public ResponseEntity<?> processResetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {   	
	    	User user = service.getByResetPasswordToken(resetPasswordRequest.getToken());    	
	    	if(user == null) {
	    		//return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    		return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Invalid Token"));
	    	}else {
	    		service.updatePassword(user, resetPasswordRequest.getPassword());
	    		return new ResponseEntity<>(HttpStatus.OK);
	    	}
	    }
}
