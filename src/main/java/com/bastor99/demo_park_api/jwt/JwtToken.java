package com.bastor99.demo_park_api.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Objeto resposta do token
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtToken {
    private String token;
}
