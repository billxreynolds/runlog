package com.admitone.ordermgmt.repository;

import org.springframework.data.repository.CrudRepository;

import com.admitone.ordermgmt.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

  UserEntity findByUserId (Integer userId);

  UserEntity findByUsername (String username);

}
