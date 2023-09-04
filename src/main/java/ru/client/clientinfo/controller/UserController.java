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
import org.springframework.web.server.ServerErrorException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import ru.client.clientinfo.dto.ContactsDto;
import ru.client.clientinfo.dto.UserDto;
import ru.client.clientinfo.entity.ContactsEntity;
import ru.client.clientinfo.entity.UserEntity;
import ru.client.clientinfo.errors.ErrorResponse;
import ru.client.clientinfo.interfaces.IContacts;
import ru.client.clientinfo.interfaces.IUser;
import ru.client.clientinfo.shema.Contacts;
import ru.client.clientinfo.shema.User;

@RestController
@RequestMapping(path = "/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final IUser user;
    private final IContacts contacts;

    @Tag(name="Создание пользователя")
    @PostMapping("/create")
    @Operation(summary = "create user")
    @ApiResponse(responseCode = "201", description = "user created")
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(
        responseCode = "409", description = "request conflict (user exist)", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> addClient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "body for create user", 
            content = @Content(schema = @Schema(implementation = User.class))) 
            @Valid @RequestBody UserEntity userEntity) {
        user.addUser(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Tag(name="Создание контактов пользователя")
    @PostMapping("/add-contacts/{id}")
    @Operation(summary = "add contacts user")
    @ApiResponse(responseCode = "201", description = "contacts created")
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "404", description = "not found", 
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> addContact(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "body for create contacts user", 
                content = @Content(schema = @Schema(implementation = Contacts.class))) 
                @Valid @RequestBody ContactsEntity contactsEntity,
            @Parameter(description = "id of the user") @PathVariable String id) {
        contacts.addContacts(contactsEntity, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Tag(name="Получение всех пользователей")
    @Operation(summary = "get list users")
    @ApiResponse(
        responseCode = "200", description = "request successful", 
        content = @Content(mediaType = "application/json", 
        array = @ArraySchema(schema = @Schema(implementation = UserEntity.class))))
    @ApiResponse(
        responseCode = "404", description = "not found", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping
    public ResponseEntity<List<UserEntity>> getUsers() throws ServerErrorException {
        return ResponseEntity.ofNullable(user.getUsers());
    }

    @Tag(name="Получение пользователя по идентификатору")
    @Operation(summary = "get user by id")
    @ApiResponse(
        responseCode = "200", description = "request successful", 
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(
        responseCode = "404", 
        description = "not found", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(
            @Parameter(description = "id of the user") @PathVariable String id) {
        return ResponseEntity.ofNullable(user.getUser(id));
    }

    @Tag(name="Получение списка контактов пользователя")
    @Operation(summary = "get list all user contacts by id")
    @ApiResponse(
        responseCode = "200", 
        description = "request successful", 
        content = @Content(mediaType = "application/json", 
        array = @ArraySchema(schema = @Schema(implementation = ContactsDto.class))))
    @ApiResponse(
        responseCode = "404", 
        description = "not found", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/contacts/{id}")
    public ResponseEntity<List<ContactsDto>> getUserContacts(
            @Parameter(description = "id of the user") @PathVariable String id) {
        return ResponseEntity.ofNullable(user.getUserContacts(id));
    }

    @Tag(name="Получение списка контактов заданного типа пользователя")
    @Operation(summary = "get list of client contacts of a given type by id")
    @ApiResponse(responseCode = "200", description = "request successful")
    @ApiResponse(
        responseCode = "404", 
        description = "not found", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/contact/{id}")
    public ResponseEntity<List<String>> getUserContactsFromType(
            @Parameter(description = "id of the user") @PathVariable String id,
            @Parameter(description = "type of the contact") 
            @NotBlank @RequestParam String type) {
        return ResponseEntity.ofNullable(user.getUserContactsFromType(id, type));
    }

}
