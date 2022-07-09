package hackathon.jackpot.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMyPostRes {
    private String id;
    private int numOfPost;
    private int numOfPoint;
    private List<GetPostRes> posts;
}
