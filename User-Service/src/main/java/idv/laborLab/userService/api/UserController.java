package idv.laborLab.userService.api;

import idv.laborLab.userService.business.UserBusinessService;
import idv.laborLab.userService.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserBusinessService userBusinessService;

    // register user
    @PostMapping("/register")
    public ResponseEntity<Long> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {

        log.info("=========================== register user process start ==============================");

        long newUserId = userBusinessService.registerUser(userRegistrationDTO);

        log.info("=========================== register user process terminate ==============================");

        return ResponseEntity.status(HttpStatus.CREATED).body(newUserId);
    }

    // get specific user
    @PostMapping("/search")
    public ResponseEntity<UserDTO> searchUser(@RequestBody UserGeneralRequestDTO userGeneralRequestDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.searchUser(userGeneralRequestDTO));
    }

    // update user info
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userBusinessService.updateUser(userDTO));
    }

    //    // validate user
    //    @PostMapping("/validate")
    //    public ResponseEntity<Boolean> validateUser(@RequestBody SearchUserRequestDTO searchUserRequestDTO) {
    //
    //        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.validateUser(searchUserRequestDTO));
    //    }

    @PostMapping("/log-in")
    public ResponseEntity<Boolean> logIn(@RequestBody UserLogInDTO userLogInDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.logInUser(userLogInDTO));
    }

    // reset password - temporary
    @PostMapping("/reset-pwd")
    public ResponseEntity<?> resetPassword(@RequestBody ResetUserPasswordDTO resetUserPasswordDTO) {

        userBusinessService.resetPassword(resetUserPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkUserExistence(@RequestBody UserGeneralRequestDTO userGeneralRequestDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.checkUserExistence(userGeneralRequestDTO));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeUser(@RequestBody UserGeneralRequestDTO userGeneralRequestDTO) {

        userBusinessService.removeUser(userGeneralRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
