package ru.client.clientinfo.shema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "user entity")
public class User {

    @Schema(description = "username")
    private String name;
}
