package pl.nowakprojects.mathtrainer.launch;

import pl.nowakprojects.mvp.MvpPresenter;

/**
 * Created by Mateusz on 27.12.2016.
 */

public class LaunchPresenter implements MvpPresenter<LaunchView>{

    LaunchView launchView;

    public LaunchPresenter(LaunchView launchView) {
        attachView(launchView);
    }


    @Override
    public void attachView(LaunchView view) {
        launchView = view;
    }


    public void startGame() {
        launchView.showGameView();
    }

    public void showHighScore(){
        launchView.showHighScore();
    }
}
