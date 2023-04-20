package idv.laborLab.userService.dto;

import idv.laborLab.sharedLibrary.miscellaneous.objects.UserRegistrationSO;
import idv.laborLab.sharedLibrary.miscellaneous.objects.UserSO;
import idv.laborLab.sharedLibrary.miscellaneous.objects.UserSecurityInfoSO;
import idv.laborLab.userService.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDTO {

    @NonNull
    private String userName;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String phoneNumber;
    @NonNull
    private LocalDate dateOfBirth;

    public User convertToUserEntity() {

        return User.builder()
                   .userName(this.userName)
                   .firstName(this.firstName)
                   .lastName(this.lastName)
                   .email(this.email)
                   .phoneNumber(this.phoneNumber)
                   .dateOfBirth(this.dateOfBirth)
                   .build();
    }

    public UserRegistrationSO buildUserRegistrationSO(long newId, String encryptedPassword) {

        UserSO userSO = UserSO.builder()
                              .id(newId)
                              .userName(this.userName)
                              .firstName(this.firstName)
                              .lastName(this.lastName)
                              .email(this.email)
                              .phoneNumber(this.phoneNumber)
                              .dateOfBirth(this.dateOfBirth)
                              .build();
        UserSecurityInfoSO userSecurityInfoSO = UserSecurityInfoSO.builder()
                                                                  .userId(newId)
                                                                  .password(encryptedPassword)
                                                                  .build();
        return UserRegistrationSO.builder()
                                 .userSO(userSO)
                                 .userSecurityInfoSO(userSecurityInfoSO)
                                 .build();
    }
}
