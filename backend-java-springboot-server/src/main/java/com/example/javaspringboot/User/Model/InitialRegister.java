package com.example.javaspringboot.User.Model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitialRegister {

    private String firstName;
    private String lastName;
    @Nullable
    private String userInstitutionId;
    @Nullable
    private Integer avatar;

    @Nullable
    private List<String> modulesSelected;

    private boolean initialRegister;
    private boolean student;

}
