package com.example.springbootproject.entities;

import com.example.springbootproject.entities.template.AbsEntity;
import com.example.springbootproject.enums.VoteType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vote voteType = (Vote) o;
        return id != null && Objects.equals(id, voteType.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
