package com.example.javaspringboot.User.Model;

//import com.example.javaspringboot.Additional.Model.ModuleRegisterDto;
//import com.example.javaspringboot.Additional.Model.ModuleRegisterDtoRole;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    private UUID id;

    private String credentialsCurrentEmail;

    private PersonalInformation personalInformation;

    private boolean termsAndConditions = false;

    private boolean initialRegister = false;

    private boolean enabled=true;
    private boolean hidden=false;

    private Set<Role> roles = new HashSet<>();

    public UserProfile buildFromUser(User user){

        return UserProfile.builder()
            .id(user.getId())
            .personalInformation(user.getPersonalInformation())
            .credentialsCurrentEmail(user.getCredentials().getCurrentEmail())
            .roles(user.getRoles())
            .enabled(user.isEnabled())
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
