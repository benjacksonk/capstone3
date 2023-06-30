package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Category;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCategoryDaoTest extends BaseDaoTestClass
{
    private MySqlCategoryDao dao;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        dao = new MySqlCategoryDao(dataSource);
        testCategory = new Category()
        {{
            setName("testCategory_plsIgn0r");
            setDescription("nondescript");
        }};
    }

    @Test
    void getAllCategories_succeeds() {
        //arrange

        //act
        var categories = dao.getAllCategories();

        //assert
        assertEquals(false, categories.isEmpty());
    }

    @Test
    void getById_succeeds() {
        //arrange

        //act
        var category = dao.getById(1);

        //assert
        assertNotNull(category);
    }

    @Test
    void getById_noMatch() {
        //arrange

        //act
        var category = dao.getById(-1);

        //assert
        assertNull(category);
    }

    @Test
    void create_succeeds() {
        //arrange

        //act
        var categoryId = dao.create(testCategory).getCategoryId();

        //assert
        assertEquals(testCategory.getName(), dao.getById(categoryId).getName());
    }

    @Test
    void update_succeeds() {
        //arrange

        //act
        dao.update(1, testCategory);

        //assert
        assertEquals(testCategory.getName(), dao.getById(1).getName());
    }

    @Test
    void delete_succeeds() {
        //arrange
        var categoryId = dao.create(testCategory).getCategoryId();

        //act
        dao.delete(categoryId);

        //assert
        assertNull(dao.getById(categoryId));
    }
}