package com.example.spring_project.presentation.controller;

import java.beans.ConstructorProperties;

import com.example.spring_project.domain.model.user.User;

public record EditUserForm(
        User user
) {
    @ConstructorProperties({"user.id", "user.name","user.address", "user.tel", "user.country"})
    public EditUserForm {
    }

    public static EditUserForm fromDomainEntity(User user) {
        return new EditUserForm(user);
    }
    public User toDomainEntity() {
        return new User(this.user.id(), this.user.name(), this.user.address(), this.user.tel(), this.user.country());
    }
} 
































