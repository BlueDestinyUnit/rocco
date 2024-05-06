package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Customer;
import com.jsh.rocco.domains.entities.Payment;
import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/payment/",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getPayment(@RequestBody Map<String,Object> reparam){
        System.out.println(reparam);

//        FindHotel findHotel, Customer customer, Payment payment,long[] roomArray
//        System.out.println(payment);
        Map<String, Object> response = new HashMap<>();
//        this.paymentService.addPayment(findHotel,customer, payment, roomArray);
        response.put("success", CommonResult.SUCCESS.name().toLowerCase());
        return ResponseEntity.ok().body(response);
    }

}
