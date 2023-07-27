package com.example.affablebeanui.service;

import com.example.affablebeanui.entity.Product;
import com.example.affablebeanui.entity.Products;
import com.example.affablebeanui.model.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ProductService {

    public static final int DELIVERY_CHARGE = 3;
    private  final    CartService cartService;

    private List<Product> products;


    public ResponseEntity saveCartItem(){
        return restTemplate.getForEntity("http://localhost:9000/transport/save",String.class);  //transpost sii ka lr  tr
    }


    record TransferData(String from_email,String to_email,double amount){}

 /*   @Value("${backend.url")
    private String baseUrl;*/
    private RestTemplate restTemplate=new RestTemplate();


    public ResponseEntity transfer(String from_email, String to_email, double amount){
        var data=new TransferData(from_email,to_email,amount+ DELIVERY_CHARGE);

     return   restTemplate.postForEntity("http://localhost:8090/account/transfer",data,String.class); //JSon type String
    }


    public ProductService (final  CartService cartService){
        this.cartService = cartService;
        var productsResponseEntity=   restTemplate.getForEntity("http://localhost:8095/backend/products", Products.class);
        if(productsResponseEntity.getStatusCode().is2xxSuccessful()){
            products=productsResponseEntity.getBody().getProducts();
            return;
        }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    }

    public List<Product> showAllProducts(){
        return products;
    }


    public List<Product> findProductByCategory(int categoryId){

        return products.stream().
                filter(p->p.getCategory().getId()==categoryId).collect(Collectors.toList());

    }
    private Product findProduct(int id){
        return  products.stream()
                .filter(p->p.getId()==id)
                .findAny()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public Product  purchaseProduct(int id){
        Product product=findProduct(id);
        cartService.addToCart(findProduct(id));

        return product;
    }

}
