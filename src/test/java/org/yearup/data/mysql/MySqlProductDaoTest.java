package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MySqlProductDaoTest extends BaseDaoTestClass
{
    private MySqlProductDao dao;
    private Product testProduct;

    @BeforeEach
    public void setup() {
        dao = new MySqlProductDao(dataSource);
        testProduct = new Product()
        {{
            setName("testBlahduct_plsIgn0r");
            setPrice(new BigDecimal("98.21"));
            setCategoryId(1);
            setDescription("nondescript");
            setColor("nope");
            setStock(3);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};
    }

    @Test
    public void getById_succeeds() {
        //arrange
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

        //act
        var actual = dao.getById(productId);

        //assert
        assertEquals(expected.getPrice(), actual.getPrice(), "Because I tried to get product 1 from the database.");
    }

    @Test
    void getById_noMatch() {
        //arrange

        //act
        var product = dao.getById(-2);

        //assert
        assertNull(product);
    }

    @Test
    void search_succeeds() {
        //arrange

        //act
        var products = dao.search(2, new BigDecimal(75), new BigDecimal(150), "");

        //assert
        assertEquals(false, products.isEmpty());
    }

    @Test
    void search_noMatches() {
        //arrange

        //act
        var products = dao.search(-2, new BigDecimal(-150), new BigDecimal(-75), "v0idyllic");

        //assert
        assertEquals(true, products.isEmpty());
    }

    @Test
    void listByCategoryId_succeeds() {
        //arrange

        //act
        var products = dao.listByCategoryId(1);

        //assert
        assertEquals(false, products.isEmpty());
    }

    @Test
    void listByCategoryId_noMatches() {
        //arrange

        //act
        var products = dao.listByCategoryId(-2);

        //assert
        assertEquals(true, products.isEmpty());
    }

    @Test
    void create_succeeds() {
        //arrange
        Product expectedProduct = new Product()
        {{
            setName("testBlahduct_plsIgn0r");
            setPrice(new BigDecimal("98.21"));
            setCategoryId(1);
            setDescription("nondescript");
            setColor("nope");
            setStock(3);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};

        //act
        var createdProductId = dao.create(expectedProduct).getProductId();

        //assert
        assertEquals(expectedProduct.getName(), dao.getById(createdProductId).getName());
    }

    @Test
    void update_succeeds() {
        //arrange

        //act
        dao.update(1, testProduct);

        //assert
        assertEquals(testProduct.getName(), dao.getById(1).getName());
    }

    @Test
    void update_doesntAffectOtherProducts() {
        //arrange

        //act
        dao.update(1, testProduct);

        //assert
        assertNotEquals(testProduct.getName(), dao.getById(2).getName());
    }

    @Test
    void delete_succeeds() {
        //arrange

        //act
        dao.delete(1);

        //assert
        assertNull(dao.getById(1));
    }

    @Test
    void delete_doesntAffectOtherProducts() {
        //arrange

        //act
        dao.delete(1);

        //assert
        assertNotNull(dao.getById(2));
    }

}