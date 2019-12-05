package com.griddynamics.cloud.learning.dao.domain;

import com.griddynamics.cloud.learning.dao.Permission;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.EnumSet;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "role")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_id_generator")
    @SequenceGenerator(name="common_id_generator", sequenceName = "common_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @Type(type = "jsonb")
    private EnumSet<Permission> permissions;
}
