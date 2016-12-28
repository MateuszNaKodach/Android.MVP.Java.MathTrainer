package pl.nowakprojects.mathtrainer.game;

import java.util.ArrayList;

import pl.nowakprojects.mvp.MvpView;

/**
 * Created by Mateusz on 27.12.2016.
 */

public interface GameView extends MvpView{
    void showTaskText(String task);
    void showAnswers(ArrayList<String> answersString);
    void showCorrectAnswerText();
    void showIncorrectAnswerText();
    void updateAnswersStats(int correctAnswers, int allQuestions);
    void startTimer(int timeInMilis, int timerTickInMilis);
    void showScoresAtTheEnd();
    void enableGameBoard();
    void disableGameBoard();
    void showPlayAgainButton();
    void hidePlayAgainButton();
    void saveHighScore(int correctAnswers, int allQuestions);
}
