package pl.nowakprojects.mathtrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.nowakprojects.mathtrainer.launch.LaunchPresenter;
import pl.nowakprojects.mathtrainer.launch.LaunchView;


public class LaunchActivity extends AppCompatActivity implements LaunchView {

    LaunchPresenter presenter;

    Button startButton;
    TextView highScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initPresenter();
        initUserInterface();
        presenter.showHighScore();
    }


    private void initUserInterface(){
        setupButtons();
        highScoreTextView = (TextView) findViewById(R.id.highgScoreTextView);
    }

    private void setupButtons(){
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startGame();
            }
        });
    }

    @Override
    public void showGameView() {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void initPresenter() {
        this.presenter = new LaunchPresenter(this);
    }

    @Override
    public void showHighScore() {
        SharedPreferences sharedPref = this.getSharedPreferences("pl.nowakprojects.mathtrainer",Context.MODE_PRIVATE);
        highScoreTextView.setText(
                getString(R.string.current_high_score,
                sharedPref.getInt(getString(R.string.high_score_correct),0),
                sharedPref.getInt(getString(R.string.high_score_all),0)
                )
        );
    }

    @Override
    protected void onResume() {
        presenter.showHighScore();
    }
}
