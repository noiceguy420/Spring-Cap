package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.*;
import com.example.capstoneproject.exceptions.CartItemNotFoundException;
import com.example.capstoneproject.exceptions.CartNotFoundException;
import com.example.capstoneproject.exceptions.ProductNotFoundException;
import com.example.capstoneproject.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/carts")
@Tag(name = "carts")//names it in swagger
public class cartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){
        var cartDto = cartService.createCart();
        URI uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
        //return new ResponseEntity<>(cartDto, HttpStatus.CREATED);//alt way of creating when uri not needed
    }

    @GetMapping("/{id}")
    public CartDto getCart(@PathVariable("id") UUID cid){
        return cartService.getCart(cid);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "adds a product to a cart")//adds desc in swagger
    public ResponseEntity<CartItemDto> addToCart(@Parameter(description = "id of the cart") @PathVariable UUID cartId, @RequestBody AddItemRequest addItemRequest){
        CartItemDto cartItemDto = cartService.addToCart(cartId, addItemRequest.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    //known issues: no validation for quantity
    @PutMapping("/{cid}/items/{pid}")
    public CartItemDto updateItem(@PathVariable UUID cid, @PathVariable Long pid,@Valid @RequestBody UpdateCartItemRequest req){
        return cartService.updateItem(cid, pid, req.getQuantity());
    }

    @DeleteMapping("{cartId}/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable("cartId") UUID cid, @PathVariable("productId") Long pid){
        cartService.removeItem(cid, pid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{cid}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cid){
        cartService.clearCart(cid);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("cart not found"));
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Product not found"));
    }
    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartItemNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("item not found in cart"));
    }
}