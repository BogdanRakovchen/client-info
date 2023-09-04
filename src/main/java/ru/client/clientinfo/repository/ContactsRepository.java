package ru.client.clientinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.client.clientinfo.entity.ContactsEntity;

public interface ContactsRepository extends JpaRepository<ContactsEntity, Integer> {

}
