package POT.DuoBloom.domain.board.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private String content;
    private List<String> photoUrls;
}
