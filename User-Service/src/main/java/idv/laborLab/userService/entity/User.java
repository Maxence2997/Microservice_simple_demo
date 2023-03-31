package idv.laborLab.userService.entity;

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
               @Index(name = "phone_number", columnList = "phoneNumber", unique = true)
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private long addressId;
}
