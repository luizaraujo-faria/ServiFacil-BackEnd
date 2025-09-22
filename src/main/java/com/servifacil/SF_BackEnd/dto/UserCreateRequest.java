package com.servifacil.SF_BackEnd.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class UserCreateRequest {
    // Dados do usuário
    @NotBlank(message = "Nome é obrigatório")
    private String userName;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
    private String userPassword;

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    private String rg;

    @NotBlank(message = "Telefone é obrigatório")
    private String telephone;

    private String cnpj;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data deve ser no passado")
    private LocalDate birthDate;

    private String userType = "CLIENT";
    private String profession;

    // Dados do endereço
    @NotBlank(message = "CEP é obrigatório")
    private String zipCode;

    @NotBlank(message = "Rua é obrigatória")
    private String street;

    @NotBlank(message = "Número é obrigatório")
    private String houseNumber;

    private String complement;

    @NotBlank(message = "Bairro é obrigatório")
    private String neighborhood;

    @NotBlank(message = "Cidade é obrigatória")
    private String city;

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    private String state;

    // Construtor padrão (obrigatório para desserialização JSON)
    public UserCreateRequest() {
    }

    // Construtor com todos os campos
    public UserCreateRequest(String userName, String email, String userPassword,
                             String cpf, String rg, String telephone, String cnpj,
                             LocalDate birthDate, String userType, String profession,
                             String zipCode, String street, String houseNumber,
                             String complement, String neighborhood, String city,
                             String state) {
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
        this.cpf = cpf;
        this.rg = rg;
        this.telephone = telephone;
        this.cnpj = cnpj;
        this.birthDate = birthDate;
        this.userType = userType;
        this.profession = profession;
        this.zipCode = zipCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

    // GETTERS E SETTERS (obrigatórios sem Lombok)
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

    public String getComplement() { return complement; }
    public void setComplement(String complement) { this.complement = complement; }

    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}