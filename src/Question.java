
import java.io.Serializable;

public class Question implements Serializable {
    //this just holds the question and subject strings and has a constructor that sets them
    String question;
    String subject;

    public Question(String question, String subject) {
        this.question = question;
        this.subject = subject;
    }


}
