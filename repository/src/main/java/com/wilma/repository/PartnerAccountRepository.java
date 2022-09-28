package com.wilma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilma.entity.users.Partner;

@Repository
public interface PartnerAccountRepository extends JpaRepository<Partner, Integer>{
    
}


