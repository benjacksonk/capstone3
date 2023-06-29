package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

    ShoppingCart create (int userID, int productId, int quantity);
// ShoppingCart addToCart(int userID, Product product);

    ShoppingCart update (int userId, int productId, int quantity);

    ShoppingCart clearCart(int userId);


}
