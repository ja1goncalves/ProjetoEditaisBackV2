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
public class ResponseExceptionDTO implements Serializable {

    private static final long serialVersionUID = 3304894498594875906L;

    private int statusCode;
    private String error;
    private String timestamp;
    private String path;
    private String message;


}
