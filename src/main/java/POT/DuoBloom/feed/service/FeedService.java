package POT.DuoBloom.feed.service;

import POT.DuoBloom.board.dto.BoardResponseDto;
import POT.DuoBloom.board.service.BoardService;
import POT.DuoBloom.emotion.dto.EmotionResponseDto;
import POT.DuoBloom.emotion.service.EmotionService;
import POT.DuoBloom.feed.dto.FeedResponseDto;
import POT.DuoBloom.feed.dto.QuestionWithAnswersDto;
import POT.DuoBloom.feed.dto.QuestionDto;
import POT.DuoBloom.user.UserRepository;
import POT.DuoBloom.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final EmotionService emotionService;
    private final BoardService boardService;
    private final FeedQuestionService feedQuestionService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public FeedResponseDto getDailyFeed(LocalDate date, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // coupleUser 가져오기
        User coupleUser = user.getCoupleUser();
        if (coupleUser == null) {
            throw new IllegalStateException("커플 연결이 필요합니다.");
        }

        // Emotion 조회
        Optional<EmotionResponseDto> userEmotion = emotionService.findByDateAndUsers(date, user);
        Optional<EmotionResponseDto> coupleEmotion = emotionService.findByDateAndUsers(date, coupleUser);

        // Board 조회
        List<BoardResponseDto> userBoards = boardService.getBoardsByDateAndUser(date, user);
        List<BoardResponseDto> coupleBoards = boardService.getBoardsByDateAndUser(date, coupleUser);

        // Question + Answer 조회 후 변환
        List<QuestionWithAnswersDto> questionsWithAnswers = feedQuestionService.getQuestionsWithAnswerStatus(date, user.getUserId())
                .stream()
                .map(questionDto -> new QuestionWithAnswersDto(
                        questionDto.getQuestionId(),
                        questionDto.getContent(),
                        questionDto.getMyAnswerStatus(),
                        questionDto.getCoupleAnswerStatus(),
                        questionDto.getAnswers()
                ))
                .collect(Collectors.toList());

        return new FeedResponseDto(
                date,
                userEmotion.orElse(null),
                coupleEmotion.orElse(null),
                userBoards,
                coupleBoards,
                questionsWithAnswers
        );
    }
}
