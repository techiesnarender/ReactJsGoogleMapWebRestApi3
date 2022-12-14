package com.misha.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.misha.model.User;

public interface UserPaginationRepository  extends JpaRepository<User, Integer>{
	
	 Page<User> findByAddressContaining(String address, Pageable pageable);
}
