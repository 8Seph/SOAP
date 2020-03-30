package ru.geekbrains.supershop.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import ru.geekbrains.supershop.persistence.entities.enums.Role;
import ru.geekbrains.supershop.persistence.entities.utils.PersistableEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Shopuser extends PersistableEntity {

    private String phone;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "shopuser")
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "shopuser")
    private List<Review> reviews;

}