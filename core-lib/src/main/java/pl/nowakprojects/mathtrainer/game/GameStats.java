package pl.nowakprojects.mathtrainer.game;


import com.udojava.evalex.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import pl.nowakprojects.mathtrainer.util.Const;


/**
 * Created by Mateusz on 28.12.2016.
 */
public class GameStats {

    private int allQuestions=0;
    private int correctAnswers=0;
    private String currentExpression;
    private ArrayList<Integer> answersList;


    private static GameStats ourInstance = new GameStats();

    public static GameStats getInstance() {
        return ourInstance;
    }


    void generateNewTask(){
        Random rand = new Random();
        currentExpression = Integer.valueOf(rand.nextInt(100)).toString()+"+"+Integer.valueOf(rand.nextInt(100)).toString();
        generateAnswers();
    }

    String getTask(){
        return currentExpression;
    }

    public ArrayList<Integer> getAnswersList() {
        return answersList;
    }

    public ArrayList<String> getAnswersString(){
        ArrayList<String> answersStrings = new ArrayList<>();
        for(Integer answer: answersList)
            answersStrings.add(answer.toString());

        return answersStrings;
    }

    private void generateAnswers(){
        answersList = new ArrayList<>();
        answersList.add(getCorrectAnswer());

        Random rand = new Random();

        int newAnswer=0;
        for(int i=0;i< Const.INCORRECT_ANSWERS_PER_QUESTION;) {
            newAnswer = rand.nextInt(getCorrectAnswer() + 100);
            if(newAnswer!=getCorrectAnswer()) {
                answersList.add(newAnswer);i++;
            }
        }

        Collections.shuffle(answersList);
    }

    int getCorrectAnswer(){
        Expression exp = new Expression(currentExpression);
        return exp.eval().intValue();
    }

    int getCorrectAnswerId(){
        for(int i=0;i<answersList.size();i++)
            if(isCorrectAnswer(i))
                return i;

        return -1;
    }

    boolean isCorrectAnswer(int answerId){
        return answersList.get(answerId)==getCorrectAnswer();
    }

    void answeredCorrect(){
        taskAnswered();
        correctAnswers++;
    }

    void answeredIncorrect(){
        taskAnswered();
    }

    private void taskAnswered(){
        allQuestions++;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getAllQuestions() {
        return allQuestions;
    }

    public void refreshStats() {
        allQuestions=0;
        correctAnswers=0;
    }
}
