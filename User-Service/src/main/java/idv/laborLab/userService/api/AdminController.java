package idv.laborLab.userService.api;

import idv.laborLab.userService.business.UserBusinessService;
import idv.laborLab.userService.dto.UserGeneralRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-admin")
public class AdminController {

    private final UserBusinessService userBusinessService;

    @PostMapping("/remove")
    public ResponseEntity<?> removeUser(@RequestBody @NonNull UserGeneralRequestDTO userGeneralRequestDTO) {

        userBusinessService.removeUser(userGeneralRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // update user credential info e.g. userName, firstName, lastName, dateOfBirth...
}
