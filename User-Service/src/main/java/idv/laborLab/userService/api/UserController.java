package idv.laborLab.userService.api;

import idv.laborLab.userService.business.UserBusinessService;
import idv.laborLab.userService.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserBusinessService userBusinessService;

    // register user
    @PostMapping("/register")
    public ResponseEntity<Long> registerUser(UserRegistrationDTO userRegistrationDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userBusinessService.registerUser(userRegistrationDTO));
    }

    // get specific user
    @PostMapping("/search")
    public ResponseEntity<UserDTO> searchUser(SearchUserRequestDTO searchUserRequestDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.searchUser(searchUserRequestDTO));
    }

    // update user info
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(UpdateUserInfoDTO updateUserInfoDTO) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userBusinessService.updateUser(updateUserInfoDTO));
    }

    // validate user
    @PostMapping("/validate")
    public ResponseEntity<Boolean> updateUser(SearchUserRequestDTO searchUserRequestDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.validateUser(searchUserRequestDTO));
    }

    // reset password - temporary
    @PostMapping("/reset-pwd")
    public ResponseEntity resetPassword(ResetUserPasswordDTO resetUserPasswordDTO) {

        userBusinessService.resetPassword(resetUserPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
