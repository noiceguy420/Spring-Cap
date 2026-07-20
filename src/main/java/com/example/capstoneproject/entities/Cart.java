package com.example.capstoneproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cart", schema = "capstone")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public BigDecimal getTotalPrice(){
        /*BigDecimal sum = new BigDecimal(0);
        for(CartItem i: cartItems)
            sum = sum.add(i.getTotalPrice());
        return sum*/
        return cartItems.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public CartItem getItem(Long pid){
        return this.getCartItems().stream().filter(c -> c.getProduct().getId().equals(pid)).findFirst().orElse(null);
    }

    public CartItem addItem(Product product){
        var cartItem = getItem(product.getId());
        if(cartItem != null)
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        else{
            cartItem = new CartItem();
            cartItem.setCart(this);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            this.getCartItems().add(cartItem);
            //2 ways use existing cartRepo or create a new cartItemRepo
            //since cartItems are weak entities to represent the product cart many-to-many relationship it should not have its own lifecycle
        }
        return cartItem;
    }
    
    public void removeItem(Long pid){
        CartItem item = getItem(pid);
        if(item == null)
            return;
        cartItems.remove(item);
        item.setCart(null);
    }

    public void clearCart(){
        cartItems.clear();
    }
}