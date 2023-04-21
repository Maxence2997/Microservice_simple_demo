package idv.laborLab.userService.api;

import idv.laborLab.userService.business.UserBusinessService;
import idv.laborLab.userService.dto.FindUsersResponseDTO;
import idv.laborLab.userService.dto.UserGeneralRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-admin")
public class AdminController {

    private final UserBusinessService userBusinessService;

    @PostMapping("/remove")
    public ResponseEntity<?> removeUser(@RequestBody @NonNull UserGeneralRequestDTO userGeneralRequestDTO) {

        log.info("remove user with: {}", userGeneralRequestDTO);
        userBusinessService.removeUser(userGeneralRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // update user credential info e.g. userName, firstName, lastName, dateOfBirth...

    @GetMapping("/{page}/{size}")
    public ResponseEntity<FindUsersResponseDTO> findUserByPage(@PathVariable("page") int page, @PathVariable("size") int size) {

        log.info("find users with page: {}, size: {}", page, size);
        return ResponseEntity.ok(userBusinessService.findUsers(page, size));
    }
}
