package ru.client.clientinfo.entity;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(description = "user entity")
public class UserEntity {

    public UserEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "name is required")
    @Size(min = 2, max = 15)
    private String name;

    @OneToMany(mappedBy = "user")
    private List<ContactsEntity> contacts;

    public UserEntity(String name, List<ContactsEntity> contacts) {
        this.name = name;
        this.contacts = contacts;
    }

    public UserEntity(Integer id, String name) {
        this.id = id;
        this.name = name;

    }

}
