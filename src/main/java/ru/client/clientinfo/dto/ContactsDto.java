package ru.client.clientinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Schema(description = "user contacts")
public class ContactsDto {

    private String telephone;
    private String email;

    public ContactsDto() {
    }

}
