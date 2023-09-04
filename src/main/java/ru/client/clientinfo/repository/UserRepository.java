package ru.client.clientinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.client.clientinfo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
