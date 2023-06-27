package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

   // ShoppingCart addToCart(int userID, Product product);
}
