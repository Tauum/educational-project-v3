package com.example.javaspringboot.User.Model;

import com.example.javaspringboot.Additional.Model.Note;
import com.example.javaspringboot.User.UserUtility;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "User") @Builder
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "user_notes",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "note_id"))
    private Set<Note> notes = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Credentials credentials;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private PersonalInformation personalInformation;

    @Column(name = "terms_and_conditions")
    private boolean termsAndConditions = false;

    @Column(name = "initial_register")
    private boolean initialRegister = false;

    private boolean enabled=true;
    private boolean hidden=false;

    private LocalDateTime creation;

    @Column(nullable = true)
    public LocalDateTime last_updated;

    @Version
    public Long version = 0L;

    public User(Credentials credentials,
        PersonalInformation personalInformation,
        boolean termsAndConditions,
        LocalDateTime creation)
    {
        this.credentials = credentials;
        this.personalInformation = personalInformation;
        this.termsAndConditions = termsAndConditions;
        this.enabled = true;
        this.creation = creation;
    }
}

//    public User(String email, String password, LocalDate dob, Date createdOn, boolean termsAndConditions) {
//        this.email = email;
//        this.password = password;
//        this.dob = dob;
//        this.createdOn = createdOn;
//        this.termsAndConditions = termsAndConditions;
//    }
//
//    public User(String email, String password, String userInstitutionId, String firstName, String lastName, Date createdOn, Date updatedOn, LocalDate dob, boolean termsAndConditions, boolean initialRegister, Integer avatar, boolean enabled, Set<Role> roles) {
//        this.email = email;
//        this.password = password;
//        this.userInstitutionId = userInstitutionId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.createdOn = createdOn;
//        this.updatedOn = updatedOn;
//        this.dob = dob;
//        this.termsAndConditions = termsAndConditions;
//        this.initialRegister = initialRegister;
//        this.avatar = avatar;
//        this.enabled = enabled;
//        this.roles = roles;
//    }

