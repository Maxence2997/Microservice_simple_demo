package idv.laborLab.userService.api;

import idv.laborLab.userService.business.UserBusinessService;
import idv.laborLab.userService.dto.UserDTO;
import idv.laborLab.userService.dto.UserGeneralRequestDTO;
import idv.laborLab.userService.dto.UserLogInDTO;
import idv.laborLab.userService.dto.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserBusinessService userBusinessService;

    // register user
    @PostMapping("/register")
    public ResponseEntity<Long> registerUser(@RequestBody @NonNull UserRegistrationDTO userRegistrationDTO) {

        log.info("register user process start");

        long newUserId = userBusinessService.registerUser(userRegistrationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUserId);
    }

    // get specific user
    @PostMapping("/search")
    public ResponseEntity<UserDTO> searchUser(@RequestBody @NonNull UserGeneralRequestDTO userGeneralRequestDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.searchUser(userGeneralRequestDTO));
    }

    // update user info
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @NonNull UserDTO userDTO) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userBusinessService.updateUser(userDTO));
    }

    //    // validate user
    //    @PostMapping("/validate")
    //    public ResponseEntity<Boolean> validateUser(@RequestBody SearchUserRequestDTO searchUserRequestDTO) {
    //
    //        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.validateUser(searchUserRequestDTO));
    //    }

    @PostMapping("/log-in")
    public ResponseEntity<Boolean> logIn(@RequestBody @NonNull UserLogInDTO userLogInDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.logInUser(userLogInDTO));
    }

    // reset password - temporary
    //    @PostMapping("/reset-pwd")
    //    public ResponseEntity<?> resetPassword(@RequestBody ResetUserPasswordDTO resetUserPasswordDTO) {
    //
    //        userBusinessService.resetPassword(resetUserPasswordDTO);
    //        return ResponseEntity.status(HttpStatus.OK).build();
    //    }

    //    @PostMapping("/check")
    //    public ResponseEntity<Boolean> checkUserExistence(@RequestBody UserGeneralRequestDTO userGeneralRequestDTO) {
    //
    //        return ResponseEntity.status(HttpStatus.OK).body(userBusinessService.checkUserExistence(userGeneralRequestDTO));
    //    }
}
