package com.cognizant.drugs.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
