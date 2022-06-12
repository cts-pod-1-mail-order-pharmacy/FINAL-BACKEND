package com.cts.subscriptionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
    //userid
    private String uid;
    //username
    private String name;
    private boolean isValid;
}
