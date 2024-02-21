package com.example.spring_project.presentation.controller;

import java.beans.ConstructorProperties;

import com.example.spring_project.domain.model.user.NewUser;

public record AddUserForm(
        NewUser newUser
) {
    @ConstructorProperties({"newUser.name","newUser.address", "newUser.tel", "newUser.countryCode"})
    public AddUserForm {
    }

    public static AddUserForm fromDomainEntity(NewUser newUser) {
        return new AddUserForm(newUser);
    }
    public NewUser toDomainEntity() {
        return new NewUser(
            this.newUser.name(), 
            this.newUser.address(), 
            this.newUser.tel(), 
            this.newUser.countryCode());
    }
}
 
































