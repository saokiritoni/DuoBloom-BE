package POT.DuoBloom.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnswerDto {
    private Long answerId;
    private Long userId;
    private Long questionId;
    private String content;
    private LocalDateTime createdAt;
}
