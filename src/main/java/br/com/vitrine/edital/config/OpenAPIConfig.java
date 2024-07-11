package br.com.vitrine.edital.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Configuration
public class OpenAPIConfig {

    @Value("${edital.openapi.dev-url}")
    private String devUrl;

    @Value("${edital.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        List<Server> servers = getServers();

        Contact contact = new Contact();
        contact.setName("UPE/POLI");
        contact.setUrl("https://poli.br/");

        Info info = new Info()
                .title("API de Gerencimento de Editais")
                .version("1.0.0")
                .contact(contact)
                .description("Esta API expõe os endpoints para o gerenciamentos da plataforma Dashboard de Editais");

        return new OpenAPI().info(info).servers(servers);
    }

    private List<Server> getServers() {
        List<Server> servers = new ArrayList<>();

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor do ambiente de desenvolvimento");
        servers.add(devServer);

        if (nonNull(prodUrl) && !prodUrl.isBlank()) {
            Server prodServer = new Server();
            prodServer.setUrl(prodUrl);
            prodServer.setDescription("URL do servidor do ambiente de produção");
            servers.add(prodServer);
        }
        return servers;
    }
}
