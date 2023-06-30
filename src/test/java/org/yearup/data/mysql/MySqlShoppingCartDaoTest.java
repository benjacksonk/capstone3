package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySqlShoppingCartDaoTest extends BaseDaoTestClass
{
    private MySqlShoppingCartDao dao;

    @BeforeEach
    void setUp() {
        dao = new MySqlShoppingCartDao(dataSource);
        dao.create(1, 1, 5);
    }

    @Test
    void getByUserId_succeeds() {
        //arrange

        //act
        var shoppingCart = dao.getByUserId(1);

        //assert
        assertNotNull(shoppingCart);
    }

    @Test
    void create_succeeds() {
        //arrange

        //act
        dao.create(2, 1, 7);

        //assert
        assertNotNull(dao.getByUserId(2));
    }

    @Test
    void update_succeeds() {
        //arrange
        int expectedQuantity = 14;

        //act
        dao.update(1, 1, expectedQuantity);

        //assert
        assertEquals(expectedQuantity, dao.getByUserId(1).get(1).getQuantity());
    }

    @Test
    void clearCart_succeeds() {
        //arrange

        //act
        dao.clearCart(1);

        //assert
        assertEquals(true, dao.getByUserId(1).getItems().isEmpty());
    }
}