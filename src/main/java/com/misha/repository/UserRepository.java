package com.misha.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.misha.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
		
	String countDistanceQuery = "(3959 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(latitude)) * COS(RADIANS(longitude) - RADIANS(:longitude)) + SIN(RADIANS(:latitude)) * SIN(RADIANS(latitude))))";

	@Query(value = "SELECT a.*, " + countDistanceQuery + " AS distance FROM user a WHERE a.address LIKE %:address% HAVING distance < 25 ORDER BY distance LIMIT 0, 20" , nativeQuery = true)
	public List<User> getNearsetLoactionOfSitter(String address, String latitude, String longitude);
	 
	Optional<User> findByEmail(String email);
	
	  
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	@Query("SELECT c FROM User c WHERE c.email = ?1")
	public User findByEmailToken(String email); 
	 
	public User findByResetPasswordToken(String token);
	
	@Query("SELECT c FROM User c WHERE c.address LIKE %:address%")
	List<User> findAllByAddress(String address);
	
	void deleteByIdIn(List<Integer> id);
 	   
}
