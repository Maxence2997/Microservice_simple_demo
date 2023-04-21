package idv.laborLab.miscellaneous.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationSO implements Serializable {

    private UserSO userSO;
    private UserSecurityInfoSO userSecurityInfoSO;
}
