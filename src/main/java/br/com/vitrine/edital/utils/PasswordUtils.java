package br.com.vitrine.edital.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    private final BCryptPasswordEncoder encoder;

    public PasswordUtils(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String encriptar(String senha) {
        return encoder.encode(senha);
    }

    public boolean isHashValido(String senha, String hash) {
        return encoder.matches(senha, hash);
    }

}
