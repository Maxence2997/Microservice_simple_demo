package idv.laborLab.userService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_table",
       indexes = {
               @Index(name = "full_name", columnList = "first_name, last_name"),
               @Index(name = "user_name", columnList = "user_name", unique = true),
               @Index(name = "email", columnList = "email", unique = true),
               @Index(name = "phone_number", columnList = "phone_number", unique = true)
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
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
