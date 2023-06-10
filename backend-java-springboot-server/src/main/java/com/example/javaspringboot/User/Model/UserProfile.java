package com.example.javaspringboot.User.Model;

import com.example.javaspringboot.Security.Model.Credentials;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    private UUID id;
    private Credentials credentials;
    private PersonalInformation personalInformation;

    private LocalDateTime createdOn;
    private LocalDateTime last_updated;
    private Long version;

    private boolean termsAndConditions = false;
    private boolean initialRegister = false;

    private boolean hidden=false;

    public UserProfile buildFromUser(User user){
        return UserProfile.builder()
            .id(user.getId())
            .credentials(user.getCredentials())
            .personalInformation(user.getPersonalInformation())
            .createdOn(user.getCreation())
            .last_updated(user.getLast_updated())
            .version(user.getVersion())
            .termsAndConditions(user.isTermsAndConditions())
            .initialRegister(user.isInitialRegister())
            .hidden(user.isHidden())
            .build();
    }



//    private List<ModuleRegisterDto> modules;

//    private List<ModuleRegisterDtoRole> modules;

//    public UserProfile(long id, String email, String userInstitutionId, String firstName, String lastName, Date createdOn, Date updatedOn, LocalDate dob, boolean termsAndConditions, boolean initialRegister, @Nullable Integer avatar, boolean enabled, Set<Role> roles) {
//        this.id = id;
//        this.email = email;
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
}
