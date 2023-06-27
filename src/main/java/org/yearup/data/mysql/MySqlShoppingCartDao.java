package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MySqlShoppingCartDao extends  MySqlDaoBase implements ShoppingCartDao {


    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String query = "SELECT * FROM shopping_cart\n" +
                "JOIN products on shopping_cart.product_id = products.product_id\n" +
                "WHERE user_id = ?";

        ShoppingCart shoppingCart = new ShoppingCart();
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        Product product = new Product();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    product = new Product(
                           resultSet.getInt("product_id"),
                            resultSet.getString("name"),
                            resultSet.getBigDecimal("price"),
                            resultSet.getInt("category_id"),
                            resultSet.getString("description"),
                            resultSet.getString("color"),
                            resultSet.getInt("Stock"),
                            resultSet.getBoolean("featured"),
                            resultSet.getString("image_url")


                    );
                    shoppingCartItem.setProduct(product);
                    shoppingCart.add(shoppingCartItem);
                }


            }



        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return shoppingCart;



    }


    //  @Override
   // public ShoppingCart










   /* @Override
    public List<ShoppingCartItem> getAllShoppingCartItems(){

    }*/

}
