package org.yearup.data.mysql;

import org.apache.ibatis.annotations.Update;
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
import java.sql.*;
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
                    ShoppingCartItem item = new ShoppingCartItem();
                    item.setProduct(product);
                    item.setQuantity(resultSet.getInt("quantity"));
                    shoppingCart.add(item);
                }


            }



        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return shoppingCart;



    }

    @Override
    public ShoppingCart create(int userId, int productId, int quantity){
    ShoppingCart shoppingCart = getByUserId(userId);

    if (shoppingCart.contains(productId)){
       ShoppingCartItem item = shoppingCart.get(productId);
        item.setQuantity(item.getQuantity() + 1);
        update(userId, productId, item.getQuantity());




        //update quantity by one
    } else {

        String query = "Insert into shopping_cart(user_id, product_id, quantity) " +
                "VALUES(?,?,?)"
                + "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,productId);
            preparedStatement.setInt(3,quantity);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()){
                    int cartItemId = generatedKeys.getInt(1);
                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

                    //updates shopping cart with the new stuff
                    shoppingCartItem.setProductId(productId);
                    shoppingCartItem.setQuantity(quantity);
                    shoppingCart.get(productId);
                    shoppingCart.add(shoppingCartItem);
                }
            }



        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }

        return shoppingCart;


        }


    @Override
    public ShoppingCart update(int userId, int productId, int quantity) {
        String query = "UPDATE shopping_cart set quantity = ? where product_id =? AND user_id = ?";
                //"UPDATE shopping_cart(user_id, product_id, quantity)" + "VALUES(?,?,?)" + "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(1, quantity);


           preparedStatement.executeUpdate();

            return getByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ShoppingCart clearCart(int userId) {
        String query = "DELETE FROM shopping_cart WHERE user_id = ? " ;

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);




            preparedStatement.executeUpdate();

            return getByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





   /* @Override
    public ShoppingCart addToCart(int userID, int productId) {


        if(getByUserId(userID).contains(productId)){
            
        }



        String query = "Insert into shopping_cart(user_id, product_id, quantity) " +
                "VALUES(?,?,?)";

        try(Connection connection = getConnection()){

            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1,userID);
            statement.setInt(2,product.getProductId());
            statement.setInt(3,1);

            int idk = statement.executeUpdate();








        }catch (SQLException e){

        }




        return null;
    }*/
















   /* @Override
    public List<ShoppingCartItem> getAllShoppingCartItems(){

    }*/

}
