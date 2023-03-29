package idv.laborLab.userService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_security_info",
       indexes = {


       })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSecurityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String password;
    private long userId;    // one to one with user entity

}
