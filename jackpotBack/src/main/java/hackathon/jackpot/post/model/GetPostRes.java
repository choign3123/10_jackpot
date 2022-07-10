package hackathon.jackpot.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostRes {
    private int postIdx;
    private String imgUrl;
    private String content;
    private String userName;
    private List<Boolean> checkEmoji;
}
