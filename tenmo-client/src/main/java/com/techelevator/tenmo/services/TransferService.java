package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public TransferService(String url, AuthenticatedUser currentUser) {
        this.baseUrl = url;
        this.currentUser = currentUser;
    }

    public BigDecimal getBalance(String userToken) {
        BigDecimal userBalance =  null;
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(API_BASE_URL + "account/balance", HttpMethod.GET, makeAuthEntity(userToken), BigDecimal.class);
            userBalance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return userBalance;
    }
    public List<Transfer> listAllTransfers(String userToken) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfer/list", HttpMethod.GET, makeAuthEntity(userToken), Transfer[].class);
            transfers = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(transfers);
    }

    public List<User> listOfUsers(String userToken) {
        User[] usersList = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "account/listofusers", HttpMethod.GET, makeAuthEntity(userToken), User[].class);
            usersList = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(usersList);

    }

    public Transfer getTransfer(String userToken) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.GET, makeAuthEntity(userToken), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public Transfer updatedBalances(String userToken){
        Transfer transfer = null;
        try{
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.GET, makeAuthEntity(userToken), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;

    }

    public BigDecimal addToBalance(BigDecimal amountToAdd, int id) {
        return null;
    }
    public BigDecimal subtractFromBalance(BigDecimal amountToSubtract, int id) {
        return null;
    }


    private HttpEntity<Void> makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

}
