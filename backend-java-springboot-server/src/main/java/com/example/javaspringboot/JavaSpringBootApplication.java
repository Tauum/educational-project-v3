package com.example.javaspringboot;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.Security.Model.EnumRole;
import com.example.javaspringboot.Security.Model.Role;
import com.example.javaspringboot.Security.Repository.RoleRepository;
import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.Security.Service.MyUserDetailsService;
import com.example.javaspringboot.Security.Service.TestService;
import com.example.javaspringboot.User.Model.Registration;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses =  UserRepository.class)
public class JavaSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringBootApplication.class, args);
    }

    @Value("${test.mode}")
    private boolean testMode;

    @Bean
    CommandLineRunner run(MyUserDetailsService myUserDetailsService,
        RoleRepository roleRepository, TestService testService
    ){ // MOCK DATA
        return args -> {

            if (roleRepository.findAll().size() < 4){ // BELOW ONLY RUNS IF MISSING ROLES
                if (roleRepository.findByName(EnumRole.ROLE_UNDEFINED) == null){ roleRepository.save(new Role(EnumRole.ROLE_UNDEFINED)) ; }
                if (roleRepository.findByName(EnumRole.ROLE_STUDENT) == null){ roleRepository.save(new Role(EnumRole.ROLE_STUDENT)) ; }
                if (roleRepository.findByName(EnumRole.ROLE_STAFF) == null){ roleRepository.save(new Role(EnumRole.ROLE_STAFF)) ; }
                if (roleRepository.findByName(EnumRole.ROLE_ADMIN) == null){ roleRepository.save(new Role(EnumRole.ROLE_ADMIN)) ; }
                if (roleRepository.findByName(EnumRole.ROLE_SYSTEM) == null){ roleRepository.save(new Role(EnumRole.ROLE_SYSTEM)) ; }
            }
            System.out.println(
                "\n*****************************\n"+ "TESTMODE IS: " + testMode + "\n*****************************\n");
            if(testMode){
                try{
                    String systemEmail = "system@test.com";
                    Registration system = new Registration(
                        systemEmail,
                        "abcd1234!",
                        LocalDate.now().minusYears(20),
                        true);

                    EnumResult userResult = testService.addUser(system);
                    System.out.println("\n*****************************\n" + systemEmail + " - " + userResult + "\n*****************************\n");
                    if (userResult == EnumResult.ACCEPTED){
                        Credentials credentials = testService.getCredentialsByEmail(systemEmail);
                        testService.addRoleToUser(credentials.getId().toString(),EnumRole.ROLE_SYSTEM.toString() );
                    }
                }
                catch (Error e){ System.out.println(e); }
            }
        };
    }
}
