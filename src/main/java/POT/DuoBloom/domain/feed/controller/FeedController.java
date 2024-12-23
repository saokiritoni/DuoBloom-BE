package POT.DuoBloom.domain.feed.controller;

import POT.DuoBloom.domain.feed.dto.FeedResponseDto;
import POT.DuoBloom.domain.feed.service.FeedService;
import POT.DuoBloom.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final UserRepository userRepository;

    @Operation(summary = "특정 날짜의 피드 조회", description = "지정된 날짜의 피드를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "피드 조회 성공"),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @GetMapping("/{date}")
    public ResponseEntity<FeedResponseDto> getDailyFeed(
            @PathVariable LocalDate date,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        FeedResponseDto feedResponse = feedService.getDailyFeed(date, userId);
        return ResponseEntity.ok(feedResponse);
    }

}
