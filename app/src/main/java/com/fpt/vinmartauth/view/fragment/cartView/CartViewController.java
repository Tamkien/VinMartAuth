package com.fpt.vinmartauth.view.fragment.cartView;

import android.app.Activity;
import android.util.Log;

import com.fpt.vinmartauth.entity.Cart;
import com.fpt.vinmartauth.entity.CartItem;
import com.fpt.vinmartauth.entity.Product;
import com.fpt.vinmartauth.model.CartItemModel;

import java.util.List;

public class CartViewController {
    private CartView view;
    private final CartItemModel cartItemModel = new CartItemModel();

    void setView(CartView view) {
        this.view = view;
    }

    void fetchCartItems(String cartID, Activity activity) {
        cartItemModel.getAllCartItem(cartID, activity,new CartItemModel.GetAllCartsCallbacks() {

            @Override
            public void onSuccess(List<CartItem> items) {
                view.setCartItems(items);
            }

            @Override
            public void onFailure() {}
        });
    }

    void fetchCartItemsTotal(String cartID, Activity activity) {
        cartItemModel.getTotalItemsPrice(cartID, activity, new CartItemModel.GetTotalPricesCallbacks() {
            @Override
            public void onSuccess(int cartTotals) {
                view.setTotal(cartTotals);
            }
        });
    }

    void fetchCartAfterDelete(String cartID, String itemID, Activity activity) {
        cartItemModel.getCartAfterDelete(cartID, itemID, activity,new CartItemModel.GetAllCartsCallbacks() {
            @Override
            public void onSuccess(List<CartItem> items) {
                view.setCartItems(items);
            }

            @Override
            public void onFailure() {}
        });
    }

    void doCartItemsUpdate(String cartID, List<CartItem> items, Activity activity) {
        cartItemModel.updateCartItemsQuantity(cartID, items, activity,new CartItemModel.GetAllCartsCallbacks() {
            @Override
            public void onSuccess(List<CartItem> items) {
                view.setCartItems(items);
            }

            @Override
            public void onFailure() {}
        });
    }

    // invoke on main activity started
    void fetchUserCart(String UID, Activity activity) {
        Log.d("SESSION", "Cart fetching");
        cartItemModel.getCurrentUserCart(UID, activity,new CartItemModel.GetCurrentUserCartCallbacks() {
            @Override
            public void onSuccess(Cart cart) {
                view.setCart(cart);
            }
        });
    }

    void doCartCheckout(String cartID, List<CartItem> items) {
        // Use Java 8 Stream
        int totalCart = items.stream().mapToInt(i -> Integer.parseInt(i.getProductPrice()) * Integer.parseInt(i.getQuantity())).sum();
        // Get Current cart
        cartItemModel.updateCartForCheckout(cartID, totalCart, new CartItemModel.UpdateCartForCheckoutCallbacks() {
            @Override
            public void onSuccess(String successMessage) {
                // set checkout success message
                view.setMessage(successMessage);
            }

            @Override
            public void onFailure(String failureMessage) {
                // set checkout failure message
                view.setMessage(failureMessage);
            }
        });
    }

    public void doAddProductItem(Product product, String cartID) {
        cartItemModel.addProductToCart(product, cartID, new CartItemModel.UpdateCartForCheckoutCallbacks() {
            @Override
            public void onSuccess(String successMessage) {
                view.setMessage(successMessage);
            }

            @Override
            public void onFailure(String failureMessage) {
                view.setMessage(failureMessage);
            }
        });
    }

}
