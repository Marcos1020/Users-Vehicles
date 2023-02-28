package com.br.sanches.clientes.users.vehicle.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CpfUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String encrypt(String password) {
        return encoder.encode(password);
    }
    public static boolean match(String cpf, String encodeCpf) {
        return encoder.matches(cpf, encodeCpf);
    }

}
