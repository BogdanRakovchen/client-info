package ru.client.clientinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.client.clientinfo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
