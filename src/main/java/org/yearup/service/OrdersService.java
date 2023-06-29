package org.yearup.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;

@Component
public class OrdersService {

    private ShoppingCartDao shoppingCartDao;
    private ProfileDao profileDao;


    @Autowired

    public OrdersService(ShoppingCartDao shoppingCartDao, ProfileDao profileDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.profileDao = profileDao;
    }


    public void checkout (int userId){
        //get userid from profile dao profileDao.
        //take the userprofile and send to the orderdao
    }

}
