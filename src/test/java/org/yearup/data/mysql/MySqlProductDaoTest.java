package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlProductDaoTest extends BaseDaoTestClass
{
    private MySqlProductDao dao;

    @BeforeEach
    public void setup() {
        dao = new MySqlProductDao(dataSource);
    }

    //not being used anymore
    /*
    @Test
    public void getById_shouldReturn_theCorrectProduct()
    {
        // arrange
        int productId = 1;
        Product expected = new Product()
        {{
            setProductId(1);
            setName("Smartphone");
            setPrice(new BigDecimal("499.99"));
            setCategoryId(1);
            setDescription("A powerful and feature-rich smartphone for all your communication needs.");
            setColor("Black");
            setStock(50);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};

        // act
        var actual = dao.getById(productId);

        // assert
        assertEquals(expected.getPrice(), actual.getPrice(), "Because I tried to get product 1 from the database.");
    }
    */

    @Test
    void search_matches() {
        List<Product> products = dao.search(2, new BigDecimal(75), new BigDecimal(150), "");

        assertEquals(false, products.isEmpty());
    }

    @Test
    void search_noMatches() {
        List<Product> products = dao.search(-2, new BigDecimal(-150), new BigDecimal(-75), "v0idyllic");

        assertEquals(true, products.isEmpty());
    }

    @Test
    void listByCategoryId_matches() {
        List<Product> products = dao.listByCategoryId(1);

        assertEquals(false, products.isEmpty());
    }

    @Test
    void listByCategoryId_noMatches() {
        List<Product> products = dao.listByCategoryId(-2);

        assertEquals(true, products.isEmpty());
    }

    @Test
    void getById_match() {
        Product product = dao.getById(1);

        assertInstanceOf(Product.class, product);
    }

    @Test
    void getById_noMatch() {
        Product product = dao.getById(-2);

        assertNull(product);
    }
}