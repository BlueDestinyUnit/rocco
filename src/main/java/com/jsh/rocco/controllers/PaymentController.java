package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Customer;
import com.jsh.rocco.domains.entities.Payment;
import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.domains.enums.results.Result;
import com.jsh.rocco.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/payment/",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getPayment(FindHotel findHotel, Customer customer, Payment payment,long[] roomArray){
        System.out.println(Arrays.toString(roomArray));
        System.out.println(payment);
        System.out.println(findHotel);
        System.out.println(customer);

        Map<String, Object> response = new HashMap<>();
        Result result = this.paymentService.addPayment(findHotel,customer, payment, roomArray);
        response.put("success", result.name().toLowerCase());
        return ResponseEntity.ok().body(response);
    }

}
