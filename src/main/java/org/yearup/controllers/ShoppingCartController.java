package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.data.mysql.MySqlShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions

@RestController
@RequestMapping("/cart")
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;
    private MySqlShoppingCartDao mySqlShoppingCartDao;




    @Autowired

    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao, MySqlShoppingCartDao mySqlShoppingCartDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
        this.mySqlShoppingCartDao = mySqlShoppingCartDao;
    }





    // each method in this controller requires a Principal object as a parameter

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return this.shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    //@PostMapping("/addToCart/{productID}")
    @PostMapping ("product/{productId}")
    //@PreAuthorize("hasRole('User')")
   //@PreAuthorize("permitAll()")
    public ShoppingCart create (@PathVariable int productId, Principal principal, @RequestParam int quantity){
        Product product = productDao.getById(productId);

        try{
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            //int userId2 = user.getId();

            int userId2 = user.getId();
            shoppingCartDao.create(userId2, productId, quantity);
            return shoppingCartDao.getByUserId(userId2);


            //ShoppingCart shoppingCart = shoppingCartDao.getByUserId(userId);


          //  return this.shoppingCartDao.create(userId, productId);




        }catch( Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Sorry?");
        }




    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/product/{productId}")
    @PreAuthorize("permitAll()")
    public ShoppingCart updateCart(Principal principal, @PathVariable int productId) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();

        ShoppingCart shoppingCart = shoppingCartDao.getByUserId(userId);
        ShoppingCartItem existingItem = shoppingCart.get(productId);

        if (existingItem != null){
           existingItem.setQuantity(existingItem.getQuantity()+1);
           shoppingCartDao.update(userId,productId,existingItem.getQuantity());
        }
         return shoppingCart;

       // shoppingCartDao.update(userId, productId, quantity);
       // return shoppingCartDao.getByUserId(userId);
    }


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("{id}")
    public void clearCart(Principal principal) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();
        shoppingCartDao.getByUserId(userId);
    }












    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart

}
