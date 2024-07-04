package br.com.vitrine.edital.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static DateTimeFormatter obterFormatoDataHora() {
        return FORMATO_DATA_HORA;
    }

    public static DateTimeFormatter obterFormataData() {
        return FORMATO_DATA;
    }

    public static String formatarDataHora(LocalDateTime ldt) {
        return ldt.format(FORMATO_DATA_HORA);
    }

    public static LocalDate formatarLocalDate(LocalDateTime ldt) {
        return LocalDate.parse(ldt.format(FORMATO_DATA), FORMATO_DATA);
    }

    public static String formatarData(LocalDate ld) {
        return ld.format(FORMATO_DATA);
    }

    public static String formatarData(LocalDateTime ldt) {
        return ldt.format(FORMATO_DATA);
    }

    public static LocalDateTime obterLocalDateTime(String dataHora) {
        return LocalDateTime.parse(dataHora, FORMATO_DATA_HORA);
    }

    public static LocalDate obterLocalDate(String data) {
        return LocalDate.parse(data, FORMATO_DATA);
    }

}
