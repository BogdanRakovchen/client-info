package ru.client.clientinfo.interfaces;

import java.util.List;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerErrorException;

import ru.client.clientinfo.dto.ContactsDto;
import ru.client.clientinfo.dto.UserDto;
import ru.client.clientinfo.entity.ContactsEntity;
import ru.client.clientinfo.entity.UserEntity;

public interface IUser {

    void addClient(UserEntity userEntity);

    List<UserEntity> getUsers() throws ServerErrorException;

    UserDto getUser(String id);

    List<ContactsDto> getUserContacts(String id);

    List<String> getUserContactsFromType(String id, String type);
}
