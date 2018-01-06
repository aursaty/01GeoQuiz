package ua.alex.a24122017geoquiz;

/**
 * Created by ursan on 12/24/2017.
 */

public class TrueFalse {
    private int mQuestion;

    private boolean mIsTrueQuestion;

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isAnswerTrue() {
        return mIsTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mIsTrueQuestion = trueQuestion;
    }

    public TrueFalse(int question, boolean trueQuestion) {
        mQuestion = question;
        mIsTrueQuestion = trueQuestion;
    }

}
