package com.xfj.frameworktest.controller;

import com.xfj.frameworktest.service.CartService;
import simple.xfj.framework.annotation.Autowired;
import simple.xfj.framework.annotation.Controller;

/**
 * Created by asus on 2017/4/18.
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
