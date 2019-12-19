package com.griddynamics.cloud.learning.dao.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_data")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_id_generator")
    @SequenceGenerator(name = "common_id_generator", sequenceName = "common_id_seq", allocationSize = 1)
    private Long id;

    private String username;

    private String email;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Role role;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserArticle> userArticles = new HashSet<>();
}
