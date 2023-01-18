package com.misha.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misha.model.UserDistance;
import com.misha.repository.UserDistanceRepo;

@Service
@Transactional
public class UserDistanceService {
	
	@Autowired
	private final UserDistanceRepo distanceRepo;

	public UserDistanceService(UserDistanceRepo distanceRepo) {
		super();
		this.distanceRepo = distanceRepo;
	}
	
	
	public List<UserDistance> getNearsetLoactionOfSitters(String address, String latitude, String longitude){
		return distanceRepo.getNearsetLoactionOfSitters(address, latitude, longitude);	
	}
	
}
