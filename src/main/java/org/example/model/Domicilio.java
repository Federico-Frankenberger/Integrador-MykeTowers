package org.example.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"calle", "numero", "ciudad", "provincia", "pais", "codigoPostal"})
public class Domicilio {

    private Integer id;
    private String calle;
    private String numero;
    private String ciudad;
    private String provincia;
    private String pais;
    private String codigoPostal;
}
