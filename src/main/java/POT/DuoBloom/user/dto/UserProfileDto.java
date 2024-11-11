package POT.DuoBloom.user.dto;

import POT.DuoBloom.user.entity.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private String nickname;
    private String email;
    private Sex sex;
    private LocalDate birth;
    private Integer balance;
    private Integer coupleBalance;
    private String profilePictureUrl;
    private String region;
}

