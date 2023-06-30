package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Profile;
import org.yearup.models.User;

import static org.junit.jupiter.api.Assertions.*;

class MySqlProfileDaoTest extends BaseDaoTestClass
{
    private MySqlProfileDao dao;
    private Profile testProfile;

    @BeforeEach
    void setUp() {
        dao = new MySqlProfileDao(dataSource);

        User createdUser = new MySqlUserDao(dataSource).create(
                new User()
                {{
                    setUsername("testUsrNm_plsIgn0r");
                    setPassword("testPssWrd_plsIgn0r");
                    setAuthorities("ROLE_USER");
                }}
        );

        testProfile = new Profile()
        {{
            setUserId(createdUser.getId());
            setFirstName("feauxregard");
            setLastName("mcsomebody");
            setPhone("0123456789");
            setEmail("dont@me.thx");
            setCity("somecity");
            setState("somestate");
            setAddress("2 That St.");
            setZip("00005");
        }};
    }

    @Test
    void create_succeeds() {
        //arrange

        //act
        var createdProfile = dao.create(testProfile);

        //assert
        assertEquals(testProfile.getFirstName(), createdProfile.getFirstName());
    }
}