package com.example.javaspringboot.User.Service;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.Security.Model.EnumRole;
import com.example.javaspringboot.Security.Model.Role;
import com.example.javaspringboot.Security.Service.CredentialsService;
import com.example.javaspringboot.Security.Service.RoleService;
import com.example.javaspringboot.User.Model.PersonalInformation;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.User.Model.User;
import com.example.javaspringboot.User.Model.UserProfile;
import com.example.javaspringboot.User.Repository.UserRepository;
import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.Utility.UserUtility;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private CredentialsService credentialsService;
    private final UserRepository userRepo;
//    private ModuleService moduleService;
//
//    private SubmittedShifterService ssService;
//    private SubmittedQuizService sQService;
//    private SubmittedHangmanService sHService;
//
    public UserService(UserRepository userRepo, CredentialsService credentialsService
//                      ,@Lazy ModuleService moduleService,
//                       SubmittedShifterService ssService, SubmittedQuizService sQService, SubmittedHangmanService sHService
     ){
        this.userRepo = userRepo;
        this.credentialsService = credentialsService;
//        this.moduleService = moduleService;
//        this.ssService = ssService;
//        this.sQService = sQService;
//        this.sHService = sHService;
    }
//

  public EnumResult addUser(Registration registration) {

   EnumResult validation = UserUtility.validateRegistration(registration);
   if (validation != EnumResult.ACCEPTED) return validation;
   Credentials existingCredentials = credentialsService.findCredentialsByCurrentEmail(registration.getEmail());

    if (existingCredentials != null) return EnumResult.DUPLICATE;

    var createdCredentials = credentialsService.create(registration);
    if(createdCredentials.getKey() != EnumResult.ACCEPTED || createdCredentials.getValue() == null) return createdCredentials.getKey();

    User user = new User(
        (Credentials) createdCredentials.getValue(),
        new PersonalInformation(
            registration.getDateOfBirth()),
          registration.isTermsAndConditions());

    userRepo.save(user);
    return EnumResult.ACCEPTED;
  }

  public Credentials findCredentialsByCurrentEmail(String email){
      return credentialsService.findCredentialsByCurrentEmail(email);
  }

//  public Credentials verifyCredentials(LoginRequest loginRequest) {
//    Credentials credentials = credentialsService.findCredentialsByCurrentEmail(
//        loginRequest.getEmail());
//    if (credentials != null &&
//        credentials.getPassword() != null && credentials.getCurrentEmail() != null &&
//        // TODO : VERIFY THIS BELOW WHEN I CAN BE FUCKING BOTHERED
//        Objects.equals(credentials.getPassword(), loginRequest.getPassword()) &&
//        Objects.equals(credentials.getCurrentEmail(), loginRequest.getEmail())
//    ){
//      return credentials;
//    }
//    return null;
//  }

  public UserProfile findUserProfilebyMyUserDetails(UUID credentialsId) {
//    Credentials foundCredentials = verifyCredentials() // TODO: PART OF ABOVE
    User foundUser = userRepo.findByCredentials_Id(credentialsId);
    if(foundUser == null) return null;
    UserProfile userProfile = new UserProfile();
    return userProfile.buildFromUser(foundUser);
  }

  public List<User> findAll() {
      return userRepo.findAll();
  }


//    public boolean initialRegister(InitialRegister initialRegister, Long id) {
//        User find = findUserById(id);
//        if (find != null){
//            find.setFirstName(initialRegister.getFirstName());
//            find.setLastName(initialRegister.getLastName());
//            if (initialRegister.getUserInstitutionId() != null){ find.setUserInstitutionId(initialRegister.getUserInstitutionId()); }
//            if (initialRegister.getAvatar() != null){ find.setAvatar(initialRegister.getAvatar()); }
//            else{ find.setAvatar(0); }
//            find.setInitialRegister(true);
//
//            userRepo.save(find);
//            if (initialRegister.getModulesSelected() != null){ // this doesnt work
//                for (String moduleSelected:  initialRegister.getModulesSelected() ) {
//                    Module foundModule = moduleService.findFirstModuleContainingCode(moduleSelected);
//                    if (foundModule != null) { moduleService.addStudentToModule(foundModule.getId(), find.getId()); }
//                }
//            }
//
//            if (initialRegister.isStudent()){
//                Role undefinedRole = roleRepo.findByName(EnumRole.ROLE_UNDEFINED);
//                Role studentRole = roleRepo.findByName(EnumRole.ROLE_STUDENT);
//                if (undefinedRole != null && undefinedRole != null){
//                    addRoleToUser(find.getId(),studentRole.getId());
//                    removeRoleFromUser(find.getId(),undefinedRole.getId());
//                }
//            }
//            return true;
//        }
//        else{  return false; }
//    }

//    public User updateUser(User user) {
//
//        User find = findUserById(user.getId());
//
//        if (find != null) {
//            try {
//                if (user.getFirstName() != null && user.getFirstName().length() > 1 && user.getFirstName() != find.getFirstName()) {
//                    find.setFirstName(user.getFirstName());
//                }
//            } catch (Exception e) {
//            }
//
//            try {
//                if (user.getLastName() != null && user.getLastName().length() > 1 && user.getLastName() != find.getLastName()) {
//                    find.setLastName(user.getLastName());
//                }
//            } catch (Exception e) {
//            }
//
//            try {
//                if (user.getEmail() != null && user.getEmail() != find.getEmail() && user.getEmail().length() > 3) {
//                    find.setEmail(user.getEmail());
//                }
//            } catch (Exception e) {
//            }
//            try {
//                if (user.getDob() != null && user.getDob() != find.getDob()){
//                    find.setDob(user.getDob());
//                }
//            }
//            catch (Exception e){ }
//
//            try {
//                if (user.getRoles() != null && user.getRoles() != find.getRoles()) {
//                    find.setRoles(user.getRoles());
//                }
//            } catch (Exception e) {
//            }
//
//            // REDO THIS BLOCK BELOW TO UPDATE THE USERS STUDYING MODULES AND ADMIN MODULES
////            try {
////                if (user.getModules() != null && user.getModules() != find.getModules()){
////                    find.setModules(user.getModules());
////                }
////            }
////            catch (Exception e){ }
//            return userRepo.save(find);
//        }
//        return null;
//    }

    //query method (auto generates method in spring back-backend)
  //TODO: RECURSIVELY REMOVE ROLES THEN DELETE EVERYTHING SAME WITH PERSONAL INFORMATION AND MODULES
    @Transactional
    public void deleteUser(UUID id) {
        User findUser = findUserById(id);
        if(findUser != null){
//            credentialsService.removeRoleFromUser();
            userRepo.save(findUser);
            userRepo.deleteUserById(id);
        }
        userRepo.deleteUserById(id);
    }

    public User findUserById(UUID id) {
        User user = userRepo.findUserById(id);
        if (user != null) {
            return user;
        }
        return null;
    }

//  public EnumResult addRoleToUser(String id, String roleName) {
//      return credentialsService.addRole(FastUuidParser.fromString(id), roleName);
//  }
//
//  public EnumResult removeRoleFromUser(String id, String roleName) {
//    return credentialsService.de(FastUuidParser.fromString(id), roleName);
//  }


  //fix this to not be plain text and more complex
//    public Boolean resetUserPassword(String email) {
//        User findUser = userRepo.findUserByEmail(email);
//
//        if (findUser != null && findUser.getLastName() != null){
//            findUser.setPassword(passwordEncoder.encode("PUT SOMETHING MORE SECURE HERE"));
//            userRepo.save(findUser);
//            return true;
//        }
//        return null;
//    }

    //fix this to not be plain text and more complex
//    public Boolean updatetUserPassword(User requestingUser, String newPassword) {
//        User findUser = userRepo.findUserByEmail(requestingUser.getEmail());
//        if(findUser != null && requestingUser == findUser && findUser.getPassword() != newPassword && newPassword.length() > 6){
//            findUser.setPassword(passwordEncoder.encode("PUT SOMETHING MORE SECURE HERE"));
//            userRepo.save(findUser);
//            return true;
//        }
//        return null;
//    }




//    public UserProfile findUserProfileUserById(Long id) {
//        User user = userRepo.findUserById(id);
//        if (user != null){
//            UserProfile userProfile = new UserProfile(
//                    user.getId(),  user.getEmail(), user.getUserInstitutionId(),
//                    user.getFirstName(), user.getLastName(),
//                    user.getCreatedOn(),  user.getUpdatedOn(), user.getDob(),
//                    user.isTermsAndConditions(), user.isInitialRegister(),
//                    user.getAvatar(), user.isEnabled(), user.getRoles()
//            );
//            List<ModuleRegisterDtoRole> modules  = moduleService.findAllModulesRegisterDTOForUser(user);
//            userProfile.setModules(modules);
//
//            return userProfile;
//        }
//        return null;
//    }

//    public UserProfileAndStats findUserProfileAndStatsByEmail(String email) {
//        UserProfile uP = findUserProfileUserByEmail(email);
//        if (uP != null){
//
//            UserProfileAndStats userProfileAndStats = new UserProfileAndStats(
//                    uP.getId(), uP.getEmail(), uP.getUserInstitutionId(),
//                    uP.getFirstName(), uP.getLastName(),
//                    uP.getCreatedOn(), uP.getUpdatedOn(), uP.getDob(),
//                    uP.isTermsAndConditions(), uP.isInitialRegister(),
//                    uP.getAvatar(), uP.isEnabled(), uP.getRoles(), uP.getModules()
//            );
//
//            List<Statistics> statisticsList = new ArrayList<>();
//            var shifters = ssService.getStatisticsForUser(uP.getId());
//            if (shifters != null){ statisticsList.add(shifters); }
//            var quizzes =  sQService.getStatisticsForUser(uP.getId());
//            if (quizzes != null){ statisticsList.add(quizzes); }
//            var hangmen =  sHService.getStatisticsForUser(uP.getId());
//            if (hangmen != null){ statisticsList.add(hangmen); }
//
//            userProfileAndStats.setStatisticsList(statisticsList);
//            return userProfileAndStats;
//        }
//        return null;
//    }

//    public UserProfileAndStats findUserProfileAndStatsById(Long id) {
//        UserProfile uP = findUserProfileUserById(id);
//        if (uP != null){
//            UserProfileAndStats userProfileAndStats = new UserProfileAndStats(
//                    uP.getId(), uP.getEmail(), uP.getUserInstitutionId(),
//                    uP.getFirstName(), uP.getLastName(),
//                    uP.getCreatedOn(), uP.getUpdatedOn(), uP.getDob(),
//                    uP.isTermsAndConditions(), uP.isInitialRegister(),
//                    uP.getAvatar(), uP.isEnabled(), uP.getRoles(), uP.getModules()
//            );
//            List<Statistics> statisticsList = new ArrayList<>();
//            var shifters = ssService.getStatisticsForUser(uP.getId());
//            if (shifters != null){ statisticsList.add(shifters); }
//            var quizzes =  sQService.getStatisticsForUser(uP.getId());
//            if (quizzes != null){ statisticsList.add(quizzes); }
//            var hangmen =  sHService.getStatisticsForUser(uP.getId());
//            if (hangmen != null){ statisticsList.add(hangmen); }
//
//            userProfileAndStats.setStatisticsList(statisticsList);
//            return userProfileAndStats;
//        }
//        return null;
//    }

//    public List<UserProfile> findAllUserProfiles() {
//        List<User> users = userRepo.findAll();
//        List<UserProfile> userProfiles = new ArrayList<>();
//        for(User user : users){
//            try {
//                UserProfile userProfile = new UserProfile(
//                        user.getId(),  user.getEmail(), user.getUserInstitutionId(),
//                        user.getFirstName(), user.getLastName(),
//                        user.getCreatedOn(),  user.getUpdatedOn(), user.getDob(),
//                        user.isTermsAndConditions(), user.isInitialRegister(),
//                        user.getAvatar(), user.isEnabled(), user.getRoles()
//                );
//                List<ModuleRegisterDtoRole> modules  = moduleService.findAllModulesRegisterDTOForUser(user);
//                userProfile.setModules(modules);
//                userProfiles.add(userProfile);
//            }
//            catch(Error err){}
//        }
//        return userProfiles;
//    }

//    public List<UserProfile> findAllUserProfiles() {
//        List<User> users = userRepo.findAll();
//        List<UserProfile> userProfiles = new ArrayList<>();
//        for(User user : users){
//            try {
//                UserProfile userProfile = new UserProfile(
//                        user.getId(),  user.getEmail(), user.getUserInstitutionId(),
//                        user.getFirstName(), user.getLastName(),
//                        user.getCreatedOn(),  user.getUpdatedOn(), user.getDob(),
//                        user.isTermsAndConditions(), user.isInitialRegister(),
//                        user.getAvatar(), user.isEnabled(), user.getRoles()
//                );
//                List<ModuleRegisterDto> modules  = moduleService.findAllModulesRegisterDTOForUserId(user.getId());
//                userProfile.setModules(modules);
//                userProfiles.add(userProfile);
//            }
//            catch(Error err){}
//        }
//        return userProfiles;
//    }
}