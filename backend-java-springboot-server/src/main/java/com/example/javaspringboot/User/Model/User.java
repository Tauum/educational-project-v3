package com.example.javaspringboot.User.Model;

import com.example.javaspringboot.Additional.Model.Note;
import com.example.javaspringboot.Security.Model.Credentials;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Data @NoArgsConstructor @AllArgsConstructor @Entity //needed for database mapping
@Table(name = "User") @Builder
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "user_notes",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "note_id"))
    private Set<Note> notes = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Credentials credentials;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private PersonalInformation personalInformation;

    private boolean termsAndConditions = false;

    private boolean initialRegister = false;

    private boolean hidden=false;

    @CreationTimestamp
    private LocalDateTime creation;

    @Column(nullable = true)
    @UpdateTimestamp
    public LocalDateTime last_updated;

    @Version
    public Long version = 0L;


    public User(String email, String password)
    {
        this.credentials = new Credentials();
        this.credentials.setPassword(password);
        this.credentials.setCurrentEmail(email);
    }

    public User(Credentials credentials,
        PersonalInformation personalInformation,
        boolean termsAndConditions)
    {
        this.credentials = credentials;
        this.personalInformation = personalInformation;
        this.termsAndConditions = termsAndConditions;
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

