package com.example.springbootproject.entities;

import com.example.springbootproject.entities.template.AbsEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Community extends AbsEntity {

    @NotNull(message = "The name of the community should not be empty")
    @Column(unique = true, name = "name")
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Community subTopic = (Community) o;
        return id != null && Objects.equals(id, subTopic.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
