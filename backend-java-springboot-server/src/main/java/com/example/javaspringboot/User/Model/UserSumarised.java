package com.example.javaspringboot.User.Model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSumarised {
    private long id;

    private String userInstitutionId;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private boolean enabled=true;

}
