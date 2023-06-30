package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.User;

import static org.junit.jupiter.api.Assertions.*;

class MySqlUserDaoTest extends BaseDaoTestClass
{
    private MySqlUserDao dao;
    private User testUser;

    @BeforeEach
    void setUp() {
        dao = new MySqlUserDao(dataSource);
        testUser = new User()
        {{
            setUsername("testUsrNm_plsIgn0r");
            setPassword("testPssWrd_plsIgn0r");
            setAuthorities("ROLE_USER");
        }};
    }

    @Test
    void create_succeeds() {
        //arrange

        //act
        var createdUserId = dao.create(testUser).getId();

        //assert
        assertEquals(testUser.getUsername(), dao.getUserById(createdUserId).getUsername());
    }

    @Test
    void getAll_succeeds() {
        //arrange

        //act
        var users = dao.getAll();

        //assert
        assertEquals(false, users.isEmpty());
    }

    @Test
    void getUserById_succeeds() {
        //arrange

        //act
        var user = dao.getUserById(1);

        //assert
        assertNotNull(user);
    }

    @Test
    void getUserById_noMatch() {
        //arrange

        //act
        var user = dao.getUserById(-1);

        //assert
        assertNull(user);
    }

    @Test
    void getByUserName_succeeds() {
        //arrange
        String username = "admin";

        //act
        var user = dao.getByUserName(username);

        //assert
        assertEquals(username, user.getUsername());
    }

    @Test
    void getByUserName_noMatch() {
        //arrange
        String username = "a%#BH_sk927V";

        //act
        var user = dao.getByUserName(username);

        //assert
        assertNull(user);
    }

    @Test
    void getIdByUsername_succeeds() {
        //arrange

        //act
        var userId = dao.getIdByUsername("admin");

        //assert
        assertEquals(2, userId);
    }

    @Test
    void getIdByUsername_noMatch() {
        //arrange

        //act
        var userId = dao.getIdByUsername("a%#BH_sk927V");

        //assert
        assertEquals(-1, userId);
    }

    @Test
    void exists_succeeds() {
        //arrange

        //act
        boolean exists = dao.exists("admin");

        //assert
        assertEquals(true, exists);
    }

    @Test
    void exists_noMatch() {
        //arrange

        //act
        boolean exists = dao.exists("a%#BH_sk927V");

        //assert
        assertEquals(false, exists);
    }
}