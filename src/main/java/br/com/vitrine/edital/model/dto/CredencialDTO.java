package br.com.vitrine.edital.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CredencialDTO implements Serializable {

    private static final long serialVersionUID = 357996558893594381L;

    @EqualsAndHashCode.Include
    private String login;

    private String senha;
}
