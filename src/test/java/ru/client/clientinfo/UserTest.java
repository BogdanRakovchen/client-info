package ru.client.clientinfo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import ru.client.clientinfo.dto.ContactsDto;
import ru.client.clientinfo.dto.UserDto;
import ru.client.clientinfo.entity.ContactsEntity;
import ru.client.clientinfo.entity.UserEntity;
import ru.client.clientinfo.repository.UserRepository;
import ru.client.clientinfo.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    ModelMapper modelMapper = new ModelMapper();

    @Mock
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    UserDto userDto;
    List<ContactsDto> contactsDtoList;
    UserEntity userEntity;
    List<ContactsEntity> contactsEntitiesList;

    @BeforeEach
    public void init() {
        contactsDtoList = new ArrayList<ContactsDto>();
        contactsEntitiesList = new ArrayList<ContactsEntity>();

        contactsDtoList.add(new ContactsDto("89991501517", "test@mail.ru"));
        userDto = new UserDto(1, "jon", contactsDtoList);

        userEntity = new UserEntity(1, "jon", contactsEntitiesList);
        contactsEntitiesList.add(new ContactsEntity("89991501517", "test@mail.ru", userEntity));
    }

    /**
     * проверка типов данных UserDto
     */

    @Test
    public void equalsTypeDtoFields() {
        assertTrue(userDto.getId() instanceof Integer && userDto.getName() instanceof String
                && userDto.getContacts() instanceof List<ContactsDto>);

    }

    /**
     * проверка типов данных UserEntity
     */

    @Test
    public void equalsTypeEntityFields() {
        assertTrue(userEntity.getId() instanceof Integer && userEntity.getName() instanceof String
                && userEntity.getContacts() instanceof List<ContactsEntity>);

    }

    /**
     * проверка модификаторов доступа UserDto
     */

    @Test
    public void equalsModifiersDto() {
        Class<?> c = userDto.getClass();
        Field fields[] = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            int mod = fields[i].getModifiers();
            assertTrue(Modifier.toString(mod).equals("private"));
        }
        // количество свойств класса = 3
        assertTrue(fields.length == 3);
    }

    /**
     * проверка модификаторов доступа UserEntity
     */

    @Test
    public void equalsModifiersEntity() {
        Class<?> c = userEntity.getClass();
        Field fields[] = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            int mod = fields[i].getModifiers();
            assertTrue(Modifier.toString(mod).equals("private"));
        }
        // количество свойств класса = 3
        assertTrue(fields.length == 3);
    }

    /**
     * проверка возвращаемого значения UserEntity
     */
    @Test
    public void shouldReturnUserEntityWhenCallingGetUsersMethod() {
        UserEntity userEntity = new UserEntity(1, "jon", contactsEntitiesList);
        List<UserEntity> list = new ArrayList<>();
        list.add(userEntity);
        lenient().when(userServiceImpl.getUsers()).thenReturn(list);
    }

    /**
     * проверка статуса и формат возвращаемого значения эндпоинта getUsers()
     * 
     * @throws Exception
     */
    @Test
    public void checkGetFetch() throws Exception {
        mockMvc.perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON));

    }

}
