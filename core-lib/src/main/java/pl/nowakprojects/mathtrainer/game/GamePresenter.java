package pl.nowakprojects.mathtrainer.game;

import pl.nowakprojects.mathtrainer.util.Const;
import pl.nowakprojects.mvp.MvpPresenter;

/**
 * Created by Mateusz on 27.12.2016.
 */

public class GamePresenter implements MvpPresenter<GameView>{

    private GameView gameView;
    private GameStats gameStats;

    public GamePresenter(GameView gameView) {
        attachView(gameView);
        gameStats = GameStats.getInstance();
    }


    @Override
    public void attachView(GameView view) {
        gameView = view;
    }


    public void showTask() {
        gameView.showTaskText(gameStats.getTask());
    }

    public void showAnswers() {
        gameView.showAnswers(gameStats.getAnswersString());
    }

    public void generateNewTask() {
        gameStats.generateNewTask();
    }

    private void showNewTask() {
        generateNewTask();
        showTask();
        showAnswers();
    }

    private boolean isCorrectAnswer(int answerId) {
        return gameStats.isCorrectAnswer(answerId);
    }

    public void choseAnswerAndShowNew(int answerId) {
        if(isCorrectAnswer(answerId)) {
            gameStats.answeredCorrect();
            gameView.showCorrectAnswerText();
        }else {
            gameStats.answeredIncorrect();
            gameView.showIncorrectAnswerText();
        }
        gameView.updateAnswersStats(gameStats.getCorrectAnswers(),gameStats.getAllQuestions());

        showNewTask();
    }

    public void startNewGame(){
        gameStats.refreshStats();
        gameView.hidePlayAgainButton();
        gameView.enableGameBoard();
        showNewTask();
        gameView.startTimer(Const.DEFAULT_GAME_TIME_IN_MILISECONDS,
                Const.ONE_TIMER_TICK_IN_MILISECONDS);
    }

    public void finishGame(){
        gameView.showScoresAtTheEnd();
        gameView.disableGameBoard();
        gameView.showPlayAgainButton();
        gameView.saveHighScore(gameStats.getCorrectAnswers(),gameStats.getAllQuestions());
    }

}
