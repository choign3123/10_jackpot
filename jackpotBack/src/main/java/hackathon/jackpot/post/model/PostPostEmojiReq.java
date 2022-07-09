package hackathon.jackpot.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPostEmojiReq {
    private int userIdx;
    private int postIdx;
    private int emojiIdx;
}
