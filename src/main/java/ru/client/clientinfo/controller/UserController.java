package ru.client.clientinfo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerErrorException;

import lombok.AllArgsConstructor;
import ru.client.clientinfo.dto.ContactsDto;
import ru.client.clientinfo.dto.UserDto;
import ru.client.clientinfo.entity.ContactsEntity;
import ru.client.clientinfo.entity.UserEntity;
import ru.client.clientinfo.interfaces.IContacts;
import ru.client.clientinfo.interfaces.IUser;

@RestController
@RequestMapping(path = "/user/v1/")
@AllArgsConstructor
public class UserController {

    private final IUser user;
    private final IContacts contacts;

    @PostMapping("/create")
    public ResponseEntity<Void> addClient(@RequestBody UserEntity userEntity) {
        user.addClient(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Void> addContact(@RequestBody ContactsEntity contactsEntity, @PathVariable String id) {
        contacts.addContact(contactsEntity, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserEntity>> getUsers() throws ServerErrorException {
        return ResponseEntity.ofNullable(user.getUsers());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        return ResponseEntity.ofNullable(user.getUser(id));
    }

    @GetMapping("/read/contacts/{id}")
    public ResponseEntity<List<ContactsDto>> getUserContacts(@PathVariable String id) {
        return ResponseEntity.ofNullable(user.getUserContacts(id));
    }

    @GetMapping("/read/contact/{id}")
    public ResponseEntity<List<String>> getUserContactsFromType(@PathVariable String id,
            @RequestParam String type) {
        return ResponseEntity.ofNullable(user.getUserContactsFromType(id, type));
    }

}
