package ru.client.clientinfo.dto;

import lombok.Data;

@Data
public class ContactsDto {

    private Integer id;
    private String telephone;
    private String email;

    public ContactsDto() {

    }

    public ContactsDto(Integer id, String telephone, String email) {
        this.id = id;
        this.telephone = telephone;
        this.email = email;
    }
}
