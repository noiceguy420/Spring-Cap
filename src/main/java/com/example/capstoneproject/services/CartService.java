package com.example.capstoneproject.services;

import com.example.capstoneproject.exceptions.CartItemNotFoundException;
import com.example.capstoneproject.exceptions.CartNotFoundException;
import com.example.capstoneproject.exceptions.ProductNotFoundException;
import com.example.capstoneproject.dtos.CartDto;
import com.example.capstoneproject.dtos.CartItemDto;
import com.example.capstoneproject.entities.Cart;
import com.example.capstoneproject.entities.CartItem;
import com.example.capstoneproject.entities.Product;
import com.example.capstoneproject.mappers.CartMapper;
import com.example.capstoneproject.repositories.CartRepository;
import com.example.capstoneproject.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDto getCart(UUID cid){
        Cart cart = cartRepository.getCartWithItems(cid).orElse(null);
        if(cart == null)
            throw new CartNotFoundException();
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cid, Long pid) {
        Cart cart = cartRepository.getCartWithItems(cid).orElse(null);
        if(cart == null)
            throw new CartNotFoundException();
        Product product = productRepository.findById(pid).orElse(null);
        if(product == null)
            throw new ProductNotFoundException();
        var cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartItemDto updateItem(UUID cid, Long pid, Integer quantity){
        Cart cart = cartRepository.getCartWithItems(cid).orElse(null);
        if(cart == null)
            throw new CartNotFoundException();
        CartItem item = cart.getItem(pid);
        if(item == null)
            throw new CartItemNotFoundException();
        item.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(item);
    }

    public void removeItem(UUID cid, Long pid){
        Cart cart = cartRepository.getCartWithItems(cid).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        cart.removeItem(pid);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cid){
        Cart cart = cartRepository.getCartWithItems(cid).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        cart.clearCart();
        cartRepository.save(cart);
    }
}
