package ru.client.clientinfo.shema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "user contacts")
public class Contacts {

    @Schema(description = "user telephone")
    private String telephone;
    @Schema(description = "user email")
    private String email;

}
