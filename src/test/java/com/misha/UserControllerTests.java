package com.misha;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.misha.controller.UserController;
import com.misha.jwt.AuthEntryPointJwt;
import com.misha.jwt.JwtUtils;
import com.misha.model.User;
import com.misha.repository.UserPaginationRepository;
import com.misha.repository.UserRepository;
import com.misha.services.UserDetailsServiceImpl;
import com.misha.services.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTests {
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private BCryptPasswordEncoder crypt;
	
	@MockBean
	private JwtUtils jwtUtils; 
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private UserPaginationRepository paginationRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthEntryPointJwt authEntryPointJwt;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private ObjectMapper objectMapper;
	

	@Test
	  void shouldCreateUser() throws Exception {
		String password= "1234";
	    User user = new User(1, "Xyz", "xyz@gmail.com",password , "Xyz Company", "Laxmi Nagar",
				"28.74", "72.64", "10:30", "06:30", 150, "null",
				true , "null");
	    	
	   // user.setPassword(crypt.encode(password));
	    mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(user)))
	        .andExpect(status().isCreated())
	        .andDo(print());
	  }
}
