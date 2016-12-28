package pl.nowakprojects.mvp;

/**
 * Created by Mateusz on 27.12.2016.
 */

public interface MvpPresenter<V extends MvpView> {

   void attachView(V view);

   //void detachView();
}
