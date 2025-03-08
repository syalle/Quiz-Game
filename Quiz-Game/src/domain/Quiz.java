package domain;

public class Quiz {
    private int id;
    private String text;
    private String correct_answer;
    private double score;
    private String user_answer;

    public Quiz(int id, String text, String correct_answer, double score, String user_answer) {
        this.id = id;
        this.text = text;
        this.correct_answer = correct_answer;
        this.score = score;
        this.user_answer = user_answer;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", score=" + score +
                ", user_answer='" + user_answer + '\'' +
                '}';
    }
}
