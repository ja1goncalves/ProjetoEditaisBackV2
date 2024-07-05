package br.com.vitrine.edital.utils;

import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.nonNull;

@Component
public class Utils {

    public Boolean isValidString(String string) {
        return nonNull(string) && !string.isEmpty();
    }

    public boolean isValidNumber(Long number) {
        return nonNull(number) && number != 0;
    }

    public boolean isValidByteArray(byte[] arquivo) {
        return nonNull(arquivo) && arquivo.length > 0;
    }

    public boolean isValidCollection(Collection list) {
        return nonNull(list) && !list.isEmpty();
    }


}

