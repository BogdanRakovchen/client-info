package ru.client.clientinfo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.client.clientinfo.service.UserServiceImpl;

@SpringBootTest
class ClientInfoApplicationTests {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Test
	void contextLoads() {
	}

	/**
	 * проверка того, что сервис реализует интерфейс
	 */
	@Test
	public void checkUserServiceImplImplementationIUserInterface() {
		Assertions.assertTrue(userServiceImpl instanceof UserServiceImpl);
	}

}
