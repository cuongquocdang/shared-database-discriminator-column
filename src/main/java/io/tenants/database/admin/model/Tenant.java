package io.tenants.database.admin.model;

import io.tenants.database.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tenants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tenant extends BaseEntity {

    @Column(length = 100, unique = true)
    private String name;
}
