package br.com.vitrine.edital.config;

import br.com.vitrine.edital.service.DBService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitiateConfig {

    private final DBService dbService;

    public InitiateConfig(DBService dbService) {
        this.dbService = dbService;
    }

    @PostConstruct
    public void iniciaPerfis() {
        dbService.criarPerfis();
    }
}

