package idv.laborLab.userService.entity;

import idv.laborLab.sharedLibrary.objects.UserRegistrationSO;
import idv.laborLab.sharedLibrary.objects.UserSO;
import idv.laborLab.userService.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_table",
        indexes = {
                @Index(name = "full_name", columnList = "firstName, lastName"),
                @Index(name = "user_name", columnList = "userName", unique = true),
                @Index(name = "email", columnList = "email", unique = true),
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private long id;
    @Column(unique = true)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private long addressId;

    public UserDTO convertToUserDTO() {

        return new UserDTO(this.id, this.userName, this.firstName, this.lastName, this.email, "", this.phoneNumber, this.dateOfBirth);
    }

    public static User buildFromUserRegistrationSO(UserRegistrationSO userRegistrationSO) {

        UserSO userSO = userRegistrationSO.getUserSO();

        return User.builder()
                   .id(userSO.getId())
                   .userName(userSO.getUserName())
                   .firstName(userSO.getFirstName())
                   .lastName(userSO.getLastName())
                   .email(userSO.getEmail())
                   .phoneNumber(userSO.getPhoneNumber())
                   .dateOfBirth(userSO.getDateOfBirth())
                   .addressId(0)                           //temp
                   .build();
    }
}
