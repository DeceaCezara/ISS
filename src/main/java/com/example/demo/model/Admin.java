package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import com.example.demo.utils.enums.AdminFunction;

@Entity
@Table(name = "admins")
public class Admin extends Employee {
    @Enumerated(EnumType.ORDINAL)
    private AdminFunction adminFunction;

    public Admin() {}

    public Admin(String username, String password, String name, AdminFunction adminFunction) {
        super(username, password, name);
        this.adminFunction = adminFunction;
    }

    public AdminFunction getAdminFunction() {
        return adminFunction;
    }

    public void setAdminFunction(AdminFunction adminFunction) {
        this.adminFunction = adminFunction;
    }
}
