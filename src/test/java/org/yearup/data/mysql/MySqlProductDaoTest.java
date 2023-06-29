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

    @Test
    public void getById_shouldReturn_theCorrectProduct() {
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
    public void search_matches() {
        //arrange

        //act
        var products = dao.search(2, new BigDecimal(75), new BigDecimal(150), "");

        //assert
        assertEquals(false, products.isEmpty());
    }

    @Test
    public void search_noMatches() {
        //arrange

        //act
        var products = dao.search(-2, new BigDecimal(-150), new BigDecimal(-75), "v0idyllic");

        //assert
        assertEquals(true, products.isEmpty());
    }

    @Test
    public void listByCategoryId_matches() {
        //arrange

        //act
        var products = dao.listByCategoryId(1);

        //assert
        assertEquals(false, products.isEmpty());
    }

    @Test
    public void listByCategoryId_noMatches() {
        //arrange

        //act
        var products = dao.listByCategoryId(-2);

        //assert
        assertEquals(true, products.isEmpty());
    }

    @Test
    public void getById_match() {
        //arrange

        //act
        var product = dao.getById(1);

        //assert
        assertInstanceOf(Product.class, product);
    }

    @Test
    public void getById_noMatch() {
        //arrange

        //act
        var product = dao.getById(-2);

        //assert
        assertNull(product);
    }

    @Test
    public void create_addsProduct() {
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
        var createdProduct = dao.create(expectedProduct);

        //assert
        assertEquals(expectedProduct.getName(), createdProduct.getName());
    }

    @Test
    public void delete_removesProduct() {
        //arrange

        //act
        dao.delete(1);

        //assert
        assertNull(dao.getById(1));
    }

    @Test
    public void delete_removesOnlyTheSpecifiedProduct() {
        //arrange

        //act
        dao.delete(1);

        //assert
        assertInstanceOf(Product.class, dao.getById(2));
    }

}