package ru.client.clientinfo.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(description = "user")
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
