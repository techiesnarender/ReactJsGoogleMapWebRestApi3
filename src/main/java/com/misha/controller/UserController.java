package com.misha.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.misha.model.User;
import com.misha.payload.request.ChangePasswordRequest;
import com.misha.payload.response.MessageResponse;
import com.misha.repository.UserPaginationRepository;
import com.misha.repository.UserRepository;
import com.misha.services.UserService;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserPaginationRepository paginationRepository;
	
	@GetMapping(value = {"/userhome"})
	public String UserPage() {
		return "Hello! User Controller";
	}
	
	@GetMapping("/admin/users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> list = userService.getAllUser();
			
			if (list.isEmpty() || list.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllPublicUsers() {
		try {
			List<User> list = userService.getAllUser();
			
			if (list.isEmpty() || list.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id) {	
		try {
			Optional<User> user = userService.getUser(id);			
			if (user.isPresent()) {
				return new ResponseEntity<User>(user.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@RequestBody User user){
			
		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
		} catch (Exception e) {
			
			return new ResponseEntity<User>( HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//@PutMapping("/users/{id}")
	@RequestMapping(value = "/users/edit/{id}", method = RequestMethod.POST)
	public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user){
		try {
			Optional<User> usersData = userService.getUser(id);
			
			if(usersData.isPresent()) {
				User users = usersData.get();
				users.setContactname(user.getContactname());
				users.setEmail(user.getEmail());
				users.setAddress(user.getAddress());
				users.setChargesperhour(user.getChargesperhour());
				users.setOpen(user.getOpen());
				users.setClose(user.getClose());
				users.setCompany(user.getCompany());
				
				  return new ResponseEntity<User>(userService.saveUser(users), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@DeleteMapping("/users/{id}")
	@RequestMapping(value = "/users/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
		try {		
			Optional<User> usersData = userService.getUser(id);
			if(usersData.isPresent()) {
				userService.deleteUser(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/search")
	public ResponseEntity<List<User>> searchNearestLocation(@RequestParam String address, @RequestParam String latitude, @RequestParam String longitude){
			if(!address.isEmpty() && !latitude.isEmpty() && !longitude.isEmpty()) {				
				try {
					List<User> list = userService.getNearsetLoactionOfSitter(address, latitude, longitude);
					
					if( list.isEmpty() || list.size() == 0 ) {
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					}		
					return new ResponseEntity<>(list, HttpStatus.OK);			
				}catch(Exception e) {
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}			
			}else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}		
	}
	
	@PostMapping("/users/changepassword")
	public ResponseEntity<?> changeUserPassword(@RequestBody ChangePasswordRequest changePasswordRequest){
		try {
			User currentUser = userRepository.getUserByEmail(changePasswordRequest.getEmail());
			
			if(bCryptPasswordEncoder.matches(changePasswordRequest.getOldpassword(), currentUser.getPassword()))
			{		
				//Change your password
				currentUser.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewpassword()));
				userRepository.save(currentUser);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Wrong! old password"));	
			}
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	 private Sort.Direction getSortDirection(String direction) {
		    if (direction.equals("asc")) {
		      return Sort.Direction.ASC;
		    } else if (direction.equals("desc")) {
		      return Sort.Direction.DESC;
		    }

		    return Sort.Direction.ASC;
		  }
	
	  @GetMapping("/users/paging")
	  public ResponseEntity<Map<String, Object>> getAllUserWithPaging(
		  @RequestParam(required = false) String address,
	      @RequestParam(defaultValue = "0") int page,
	      @RequestParam(defaultValue = "5") int size,
	      @RequestParam(defaultValue = "id,desc") String[] sort) {

	    try {
	      List<Order> orders = new ArrayList<Order>();

	      if (sort[0].contains(",")) {
	          // will sort more than 2 fields
	          // sortOrder="field, direction"
	          for (String sortOrder : sort) {
	            String[] _sort = sortOrder.split(",");
	            orders.add(new Order(getSortDirection(_sort[1]),  _sort[0]));
	          }
	        } else {
	          // sort=[field, direction]
	          orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	        }

	      List<User> users = new ArrayList<User>();
	      Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

	      Page<User> pageTuts;
	       
	      if (address == null)
	      pageTuts = paginationRepository.findAll(pagingSort);
	      else
	          pageTuts = paginationRepository.findByAddressContaining(address, pagingSort);
	      
	      users = pageTuts.getContent();

	      if (users.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      Map<String, Object> response = new HashMap<>();
	      response.put("user", users);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());

	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }


}
	
