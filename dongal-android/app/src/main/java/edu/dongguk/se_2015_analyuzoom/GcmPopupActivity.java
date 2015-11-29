package edu.dongguk.se_2015_analyuzoom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Freddi on 15. 11. 29..
 */
public class GcmPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_dialog);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD//잠금 화면 위에 뜨게하기.
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON// 켜진 화면 유지 면안꺼지게하
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);// 화면 깨우기.

        WakeLocker.acquire(this);

        WakeLocker.release();

    }
}
