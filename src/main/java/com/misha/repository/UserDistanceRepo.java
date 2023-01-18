package com.misha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.misha.model.UserDistance;

@Repository
public interface UserDistanceRepo extends CrudRepository<UserDistance, Integer> {
	
	@Procedure(procedureName ="getAllNearUserDb")
	public List<UserDistance> getNearsetLoactionOfSitters(String address, String latitude, String longitude);
	
}
