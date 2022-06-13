package io.tenants.database.saas.model;

import io.tenants.database.BaseTenant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Post extends BaseTenant {

    @Column
    private String content;

    @Column
    private boolean published;
}
