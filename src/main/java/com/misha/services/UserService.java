package com.misha.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.misha.model.User;
import com.misha.model.UserDistance;
import com.misha.repository.UserDistanceRepo;
import com.misha.repository.UserRepository;

@Service
@Transactional
public class UserService {

		@Autowired
		private final UserRepository userRepository;
//		
		public UserService(UserRepository userRepository) {
			super();
			this.userRepository = userRepository;
		}

		public List<User> getAllUser() {
			return  (List<User>) userRepository.findAll();
		}
		
		public Optional<User> findByEmail(String email){
		       return userRepository.findByEmail(email);
		    }
		
//		public List<User> getAllUsers() {
//			return  (List<User>) userRepository.findAll();
//		}
//		
		public User saveUser(User user) {
			
			return userRepository.save(user);
		}
		
		public Optional<User> getUser(Integer id) {
			return userRepository.findById(id);
		}
		
		public void deleteUser(Integer id) {
			if (getUser(id).isPresent()) {
				userRepository.delete(getUser(id).get());
			}
		}
		
		public void deleteManyById(List<Integer> id) {
		    userRepository.deleteByIdIn(id);
		}
		
		public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
			User user = userRepository.findByEmailToken(email);
			if(user != null) {
				user.setResetPasswordToken(token);
				userRepository.save(user);
			}else {
				throw new UserNotFoundException("Could not find any customer with the email " + email);
			}
		}
		
		public User getByResetPasswordToken(String token) {
			return userRepository.findByResetPasswordToken(token);
		}
		
		public void updatePassword(User user, String newPassword) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encodedPassword);
			
			user.setResetPasswordToken(null);
			userRepository.save(user);
		}

		public List<User> getUser(List<Integer> id) {
			 return (List<User>) userRepository.findAllById(id);
		}
}
		
		
