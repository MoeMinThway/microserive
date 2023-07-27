package com.example.transportationservice.controller;

import com.example.transportationservice.dao.CartItemDao;
import com.example.transportationservice.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transport")
public class TransportationController {

    private final CartItemService cartItemService;

    private final CartItemDao cartItemDao;

 /*   win lr tr json ko entity list chg p save
 *  data controller ma yout ,rest so youyt tl
 * retrieve,subscribe  ko khaw ma lote // pay mhr twr yay
 * */

    // http://localhost:9000/transport/save
     @GetMapping("/save")
    public ResponseEntity<?> saveCartItems(){
        cartItemService.getAllCartItems()
                .subscribe(data -> {
            data.forEach(cartItemDao::save);
        });
        return  ResponseEntity.status(HttpStatusCode.valueOf(201)).body("success");  //body so build ma lo
    }



}
