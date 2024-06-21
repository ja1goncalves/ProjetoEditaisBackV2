package br.com.vitrine.edital.config;

import br.com.vitrine.edital.service.DBService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigInitiate {

    private final DBService dbService;

    public ConfigInitiate(DBService dbService) {
        this.dbService = dbService;
    }

    @PostConstruct
    public void iniciaPerfis() {
        dbService.criarPerfis();
    }
}

