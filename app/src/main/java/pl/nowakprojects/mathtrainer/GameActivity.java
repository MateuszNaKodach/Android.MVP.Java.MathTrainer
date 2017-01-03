package pl.nowakprojects.mathtrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pl.nowakprojects.mathtrainer.game.GamePresenter;
import pl.nowakprojects.mathtrainer.game.GameView;

public class GameActivity extends AppCompatActivity implements GameView{

    private GamePresenter presenter;

    private CountDownTimer timer;

    private GridLayout answersBoard;
    private TextView taskTextView;
    private TextView resultTextView;
    private TextView pointsTextView;
    private TextView timerTextView;
    private Button answerButton0;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initPresenter();
        initUserInterface();

        presenter.startNewGame();
    }

    @Override
    public void initPresenter() {
        presenter = new GamePresenter(this);
    }

    void initUserInterface(){
        answersBoard = (GridLayout) findViewById(R.id.answersBoard);
        taskTextView = (TextView) findViewById(R.id.taskTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        answerButton0 = (Button) findViewById(R.id.answerButton0);
        answerButton1 = (Button) findViewById(R.id.answerButton1);
        answerButton2 = (Button) findViewById(R.id.answerButton2);
        answerButton3 = (Button) findViewById(R.id.answerButton3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        setupButtonsListeners();
    }

    private void setupButtonsListeners(){
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startNewGame();
            }
        });
    }

    @Override
    public void showTaskText(String task) {
        taskTextView.setText(task);
    }

    @Override
    public void showAnswers(ArrayList<String> answersString) {
        answerButton0.setText(answersString.get(0));
        answerButton1.setText(answersString.get(1));
        answerButton2.setText(answersString.get(2));
        answerButton3.setText(answersString.get(3));
    }

    @Override
    public void showCorrectAnswerText() {
        resultTextView.setText(R.string.R_string_correct_answer);
    }

    @Override
    public void showIncorrectAnswerText() {
        resultTextView.setText(R.string.R_string_incorrect_answer);
    }

    @Override
    public void updateAnswersStats(int correctAnswers, int allQuestions) {
        pointsTextView.setText(getString(R.string.answers_stats,correctAnswers,allQuestions));
    }


    @Override
    public void startTimer(int timeinMilis, final int timerTickInMils) {
        timer = new CountDownTimer(timeinMilis,timerTickInMils) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(getString(R.string.timer_stat,String.valueOf(l/1000)));
            }

            @Override
            public void onFinish() {
                timerTextView.setText(getString(R.string.timer_stat,String.valueOf(0)));
                presenter.finishGame();
            }
        };

        timer.start();
    }

    @Override
    public void showScoresAtTheEnd() {
        resultTextView.setText(getString(R.string.game_results,pointsTextView.getText()));
    }

    @Override
    public void enableGameBoard() {
        answersBoard.setEnabled(true);
    }

    @Override
    public void disableGameBoard() {
        answersBoard.setEnabled(false);
    }

    @Override
    public void showPlayAgainButton() {
        playAgainButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlayAgainButton() {
        playAgainButton.setVisibility(View.GONE);
    }

    @Override
    public void saveHighScore(int correctAnswers, int allQuestions) {
        SharedPreferences sharedPref = this.getSharedPreferences("pl.nowakprojects.mathtrainer",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int currentHighScore = sharedPref.getInt(getString(R.string.high_score_correct), 0);
        if(correctAnswers>currentHighScore) {
            editor.putInt(getString(R.string.high_score_correct), correctAnswers);
            editor.putInt(getString(R.string.high_score_all), allQuestions);
        }

        editor.apply();
    }

    public void chooseAnswer(View view){
        presenter.choseAnswerAndShowNew(Integer.valueOf(view.getTag().toString()));
    }
}
