package ua.alex.a24122017geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private static final String KEY_IS_ANSWERED = "isAnswered";
    private static final String KEY_IS_CHEATER = "isCheated";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextImageButton;
    private ImageButton mPreviousImageButton;
    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;
    private int[] mIsQuestionAnswered = new int[]{0, 0, 0, 0, 0};
    private boolean mIsCheater = false;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        Log.e("INDEX", mIsQuestionAnswered.toString());//[mCurrentIndex] + "");
        if (mIsQuestionAnswered[mCurrentIndex] != 0) return;

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                mIsQuestionAnswered[mCurrentIndex] = 1;
                messageResId = R.string.correct_toast;
            } else {
                mIsQuestionAnswered[mCurrentIndex] = -1;
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsQuestionAnswered = savedInstanceState.getIntArray(KEY_IS_ANSWERED);
            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER);
        }
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent =
                        CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mPreviousImageButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsCheater = false;
                if (mCurrentIndex > 0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });
        mNextImageButton = (ImageButton) findViewById(R.id.next_button);
        mNextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsCheater = false;
                if (mCurrentIndex < mQuestionBank.length - 1) {
                    mCurrentIndex = (mCurrentIndex + 1);
                    updateQuestion();
                } else {
                    int rightAnswerCounter = 0;
                    for (int i : mIsQuestionAnswered) {
                        if (i == 1)
                            rightAnswerCounter++;
                    }
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.result_toast) + (int) (((double) rightAnswerCounter / mQuestionBank.length) * 100), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putIntArray(KEY_IS_ANSWERED, mIsQuestionAnswered);
        savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
