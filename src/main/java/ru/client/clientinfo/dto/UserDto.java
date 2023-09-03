package ru.client.clientinfo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.client.clientinfo.entity.ContactsEntity;

@Data
@AllArgsConstructor
public class UserDto {

    public UserDto() {
    }

    private Integer id;
    private String name;

    private List<ContactsDto> contacts;

    public UserDto(String name, List<ContactsDto> contacts) {
        this.name = name;
        this.contacts = contacts;
    }

}
