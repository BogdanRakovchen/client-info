package ru.client.clientinfo.service;

import java.util.Collections;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ServerErrorException;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
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
public class UserServiceImpl implements IUser, IContacts {

    private final UserRepository userRepository;
    private final ContactsRepository contactsRepository;
    private final Mapper mapper;

    /**
     * добавление пользователя
     */

    @Override
    public void addUser(UserEntity userEntity) {
        UserEntity newUser = new UserEntity(userEntity.getId(), userEntity.getName().trim().toLowerCase());

        if (userEntity.getId() != null) {
            UserEntity user = userRepository.findById(userEntity.getId()).orElse(null);
            if (user == null) {
                userRepository.save(newUser);
            } else if (userEntity.getId().equals(user.getId())) {
                throw new UserAlreadyExistsException("такой пользователь уже существует");
            }
        }

        userRepository.save(newUser);

    }

    /**
     * добавление контактов пользователя
     */

    @Override
    public void addContacts(ContactsEntity contactsEntity, String id) {
        UserEntity getUser = userRepository.findById(Integer.parseInt(id)).orElseThrow(() -> {
            throw new NoSuchElementFoundException(String.format("Пользователя с идентификатором %s нет", id));
        });
        ContactsEntity contacts = new ContactsEntity(contactsEntity.getTelephone().trim(),
                contactsEntity.getEmail().trim().toLowerCase(), getUser);

        contactsRepository.save(contacts);
    }

    /**
     * получение списка пользователей
     */

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

    /**
     * получение пользователя по id
     */

    @Override
    public UserDto getUser(String id) {
        UserEntity userEntity = userRepository.findById(Integer.parseInt(id)).orElseThrow(
                () -> new NoSuchElementFoundException(String.format("Пользователя с идентификатором %s нет", id)));
        return mapper.mappingUserEntityToUserDto(userEntity);

    }

    /**
     * получение списка контактов пользователя по id
     */

    @Override
    public List<ContactsDto> getUserContacts(String id) {
        UserEntity userEntity = userRepository.findById(Integer.parseInt(id)).orElseThrow(
                () -> new NoSuchElementFoundException(String.format("Пользователя с идентификатором %s нет", id)));

        return userEntity.getContacts().stream().map(contact -> mapper
                .mappingContactsEntityToContactsDto(new ContactsEntity(contact.getTelephone(), contact.getEmail())))
                .toList();
    }

    /**
     * получение списка контактов пользователя заданного типа по id и type (email or
     * telephone)
     */

    @Override
    public List<String> getUserContactsFromType(String id, String type) {
        UserEntity userEntity = userRepository.findById(Integer.parseInt(id)).orElseThrow(
                () -> new NoSuchElementFoundException(String.format("Пользователя с идентификатором %s нет", id)));
        if (type.contains("telephone")) {
            return userEntity.getContacts().stream().map(contact -> contact.getTelephone()).toList();
        }
        return userEntity.getContacts().stream().map(contact -> contact.getEmail()).toList();

    }
}
