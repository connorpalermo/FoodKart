package com.foodkart.service;

import com.foodkart.entity.Users;
import com.foodkart.exception.UserDoesNotExistException;
import com.foodkart.repository.UsersRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsersServiceMockTest {

    @Mock
    private UsersRepository userRepoMock;
    @InjectMocks
    private UsersService usersServiceMock;

    private static Users user;

    @BeforeAll
    public static void setup(){
        user = createTestUser();
    }

    @Test
    public void testRegisterUser(){
        when(usersServiceMock.registerUser(user)).thenReturn(user);

        usersServiceMock.registerUser(user);

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        verify(userRepoMock).save(captor.capture());
        assertEquals(captor.getValue().getName(), user.getName());
        assertEquals(captor.getValue().getGender(), user.getGender());
        assertEquals(captor.getValue().getUsername(), user.getUsername());
        assertEquals(captor.getValue().getPhone(), user.getPhone());
        assertEquals(captor.getValue().getPincode(), user.getPincode());
    }

    @Test
    public void testLoginUser() throws UserDoesNotExistException {
        when(userRepoMock.findByUsername("cpalermo")).thenReturn(user);

        usersServiceMock.loginUser(user.getUsername());

        verify(userRepoMock).findByUsername("cpalermo");
        assertEquals(usersServiceMock.getActiveUser(), user);
    }

    @Test
    public void testLoginUserInvalid()  {
        when(userRepoMock.findByUsername("username1234")).thenReturn(null);

        UserDoesNotExistException thrown = assertThrows(
                UserDoesNotExistException.class,
                () -> usersServiceMock.loginUser("username1234"),
                "Expected updateQuantity to throw, but it didn't");

        verify(userRepoMock).findByUsername("username1234");
        assertNotNull(thrown);
        assertTrue(thrown.getMessage().contains("does not exist."));
    }

    private static Users createTestUser(){
        Users user = new Users();
        user.setGender("M");
        user.setUsername("cpalermo");
        user.setName("Connor");
        user.setPhone("123456789");
        user.setPincode("NYC");

        return user;
    }
}
