package cn.fengyunxiao.nest.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LetterAndReply implements Serializable {
    private Letter letter;
    private List<Reply> replies = new ArrayList<>();

    public Letter getLetter() {
        return letter;
    }
    public void setLetter(Letter letter) {
        this.letter = letter;
    }
    public List<Reply> getReplies() {
        return replies;
    }
    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
