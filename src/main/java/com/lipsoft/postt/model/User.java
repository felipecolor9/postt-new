package com.lipsoft.postt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter @Setter
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    private String password;
    private String email;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastAccessDate;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user") @LazyCollection(LazyCollectionOption.FALSE)
    @NotAudited
    private Collection<Postit> postits;
    @OneToMany(fetch = FetchType.LAZY) @LazyCollection(LazyCollectionOption.FALSE)
    @NotAudited
    private Collection<Role> roles;
}
