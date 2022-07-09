package hackathon.jackpot.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostDeleteEmojiReq {
    private int userIdx;
    private int postIdx;
    private int emojiIdx;
}
