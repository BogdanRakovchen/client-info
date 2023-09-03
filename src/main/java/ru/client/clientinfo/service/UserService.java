package ru.client.clientinfo.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerErrorException;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.client.clientinfo.dto.ContactsDto;
import ru.client.clientinfo.dto.UserDto;
import ru.client.clientinfo.entity.ContactsEntity;
import ru.client.clientinfo.entity.UserEntity;
import ru.client.clientinfo.errors.NoSuchElementFoundException;
import ru.client.clientinfo.errors.UserAlreadyExistsException;
import ru.client.clientinfo.interfaces.IContacts;
import ru.client.clientinfo.interfaces.IUser;
import ru.client.clientinfo.mapper.Mapper;
import ru.client.clientinfo.repository.ContactsRepository;
import ru.client.clientinfo.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService implements IUser, IContacts {

    private final UserRepository userRepository;
    private final ContactsRepository contactsRepository;
    private final Mapper mapper;

    @Override
    public void addClient(UserEntity userEntity) {
        UserEntity user = userRepository.findById(userEntity.getId()).orElse(null);
        if (user == null) {
            userRepository.save(userEntity);
        }
        throw new UserAlreadyExistsException("такой пользователь уже существует");

    }

    @Override
    public void addContact(ContactsEntity contactsEntity, String id) {
        UserEntity getUser = userRepository.findById(Integer.parseInt(id)).orElseThrow(() -> {

            throw new NoSuchElementFoundException(String.format("Пользователя с идентификатором %s нет", id));
        });
        ContactsEntity contacts = new ContactsEntity(
                contactsEntity.getTelephone(),
                contactsEntity.getEmail(),
                getUser);

        contactsRepository.save(contacts);
    }

    @Override
    @Cacheable(cacheNames = "usersCache")
    @Retry(name = "users", fallbackMethod = "usersFallbackRetry")
    @Bulkhead(name = "users")
    @RateLimiter(name = "users")
    public List<UserEntity> getUsers() throws ServerErrorException {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    public List<?> usersFallbackRetry(ServerErrorException exception) {
        return Collections.emptyList();
    }

    @Override
    public UserDto getUser(String id) {
        UserEntity userEntity = userRepository.findById(Integer.parseInt(id))
                .orElseThrow(

                        () ->

                        new NoSuchElementFoundException(
                                String.format("Пользователя с идентификатором %s нет", id)));
        return mapper.mappingUserEntityToUserDto(userEntity);

    }

    @Override
    public List<ContactsDto> getUserContacts(String id) {
        UserEntity userEntity = userRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("Пользователя с идентификатором %s нет", id)));

        return userEntity
                .getContacts()
                .stream()
                .map(contact -> mapper.mappingContactsEntityToContactsDto(contact)).toList();
    }

    @Override
    public List<String> getUserContactsFromType(String id, String type) {
        UserEntity userEntity = userRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("Пользователя с идентификатором %s нет", id)));

        if (type.contains("telephone")) {
            return userEntity.getContacts().stream().map(i -> i.getTelephone()).toList();
        }

        return userEntity.getContacts().stream().map(i -> i.getEmail()).toList();

    }
}
