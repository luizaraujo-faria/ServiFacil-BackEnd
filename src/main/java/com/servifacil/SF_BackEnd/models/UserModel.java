package com.servifacil.SF_BackEnd.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Entity
@Table(name = "tbUsers")
public class UserModel {

    @Id
    @Column(name = "User_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "User_Name")
    @NotBlank
    private String userName;

    @Column(name = "Email")
    @NotBlank
    private String email;

    @Column(name = "User_Password")
    @NotBlank
    private String userPassword;

    @Column(name = "CPF")
    @NotBlank
    private String cpf;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "Telephone")
    @NotBlank
    private String telephone;

    @Column(name = "Birth_Date")
    @NotBlank
    private Date birthDate = new Date();

    @Column(name = "RG")
    @NotBlank
    private String rg;

    @Column(name = "User_Type")
    private UserType userType;

    @Column(name = "Profession")
    @NotBlank
    private String profession;

    @Column(name = "Address_ID")
    private int addressId;

    enum UserType {
        CLIENT, PROFESSIONAl;
    }

    // GETTERS & SETTERS

    public int getUserId(){ return this.userId; }

    public String getUserName(){ return this.userName; }
    public void setUserName(String userName){ this.userName = userName; }

    public String getEmail(){ return this.email; }
    public void setEmail(String email){ this.email = email; }

    public String getUserPassword(){ return this.userPassword; }
    public void setUserPassword(){ this.userPassword = userPassword; }

    public String getCpf(){ return this.cpf; }
    public void setCpf(String cpf){ this.cpf = cpf; }

    public String getCnpj(){ return this.cnpj; }
    public void setCnpj(String cnpj){ this.cnpj = cnpj; }

    public String getTelephone(){ return this.telephone; }
    public void setTelephone(String telephone){ this.telephone = telephone; }

    public Date getBirthDate(){ return this.birthDate; }
    public void setBirthDate(Date birthDate){ this.birthDate = birthDate; }

    public String getRg(){ return this.rg; }
    public void setRg(String rg){ this.rg = rg; }

    public UserType getUserType(){ return this.userType; }
    public void setUserType(UserType userType){ this.userType = userType; }

    public String getProfession(){ return this.profession; }
    public void setProfession(String profession){ this.profession = profession; }

    public int getAddressId(){ return this.addressId; }
    public void setAddressId(int addressId){ this.addressId = addressId; }
}
