package com.portafolio.my_portafolio_backend.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PersonalInfo {

    private Long id;//clave primaria

    @NotBlank(message="El nombre no puede estar vacio")
    private String firstName;
    @NotBlank(message="El apellido no puede estar vacio")
    private String lastName;
    @NotBlank(message="El titulo no puede estar vacio")
    private String title;//ej full stack developer
    @NotBlank(message="La descripción no puede estar vacio")
    private String profileDescription;
    @NotBlank(message="El imagen no puede estar vacio")
    private String profileImageUrl;
    @Min(value=0, message = "Los años de experiencia no pueden ser negativos")
    private Integer yearsOfExperience;
    @Email(message = "El email no es válido")
    private String email;
    @NotBlank(message="El telefono no puede estar vacio")
    private String phone;
    @NotBlank(message="El LinkedIn es una red obligatoria")
    private String linkedinUrl;
    @NotBlank(message="El GitHub es una red obligatoria")
    private String githubUrl;






}
