package POT.DuoBloom.domain.hospital.controller;

import POT.DuoBloom.common.exception.CustomException;
import POT.DuoBloom.common.exception.ErrorCode;
import POT.DuoBloom.domain.hospital.dto.request.ScrapRequestDto;
import POT.DuoBloom.domain.hospital.dto.response.HospitalListDto;
import POT.DuoBloom.domain.hospital.service.HospitalScrapService;
import POT.DuoBloom.domain.user.entity.User;
import POT.DuoBloom.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospital-scrap")
public class HospitalScrapController {

    private final HospitalScrapService hospitalScrapService;
    private final UserService userService;

    // 병원 스크랩 추가
    @PostMapping
    public void scrapHospital(HttpSession session, @RequestBody ScrapRequestDto requestDto) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new CustomException(ErrorCode.SESSION_USER_NOT_FOUND);
        }
        User user = userService.findById(userId);
        hospitalScrapService.scrapHospital(user, requestDto.getHospitalId());
    }

    // 스크랩한 병원 조회
    @GetMapping
    public List<HospitalListDto> getHospitalScraps(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new CustomException(ErrorCode.SESSION_USER_NOT_FOUND);
        }
        User user = userService.findById(userId);
        return hospitalScrapService.getHospitalScraps(user); // 반환 타입 변경
    }

    // 병원 스크랩 취소
    @DeleteMapping
    public void unsaveHospital(HttpSession session, @RequestBody ScrapRequestDto requestDto) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new CustomException(ErrorCode.SESSION_USER_NOT_FOUND);
        }
        User user = userService.findById(userId);
        hospitalScrapService.unsaveHospital(user, requestDto.getHospitalId());
    }
}