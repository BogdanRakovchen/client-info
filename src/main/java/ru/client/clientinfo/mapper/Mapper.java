package ru.client.clientinfo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import ru.client.clientinfo.dto.ContactsDto;
import ru.client.clientinfo.dto.UserDto;
import ru.client.clientinfo.entity.ContactsEntity;
import ru.client.clientinfo.entity.UserEntity;

@Component
public class Mapper {

    ModelMapper modelMapper = new ModelMapper();

    public UserDto mappingUserEntityToUserDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    public ContactsDto mappingContactsEntityToContactsDto(ContactsEntity contactsEntity) {
        return modelMapper.map(contactsEntity, ContactsDto.class);
    }

}
