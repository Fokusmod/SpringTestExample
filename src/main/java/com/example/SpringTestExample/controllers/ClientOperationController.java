package com.example.SpringTestExample.controllers;

import com.example.SpringTestExample.dto.WalletOperationRequest;
import com.example.SpringTestExample.dto.WalletResponse;
import com.example.SpringTestExample.services.ClientOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/v1")
public class ClientOperationController {


    @ResponseBody
    public String getHello() {
        return "Hello;";
    }
    @Autowired
    private ClientOperationService clientOperationService;


    @RequestMapping(value = "/wallets/{WALLET_UUID}" , method = RequestMethod.GET)
    @ResponseBody
    public String getWalletBalance(@PathVariable String WALLET_UUID) {
        return new WalletResponse(clientOperationService.getCurrentWallet(WALLET_UUID).getBalance()).toString();
    }

    @RequestMapping(value = "/wallet" , method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> getWalletBalance(@RequestBody WalletOperationRequest walletOperationRequest) {
       return clientOperationService.walletOperation(walletOperationRequest);
    }

}
