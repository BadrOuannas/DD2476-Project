74
https://raw.githubusercontent.com/harshalbenake/hbworkspace1-100/master/home%20pressed/home/src/com/example/home/MainActivity.java
package com.example.home;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	   long userInteractionTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

 

    @Override
    public void onUserInteraction() {
        userInteractionTime = System.currentTimeMillis();
        super.onUserInteraction();
        System.out.println("Interaction");
    }

    @Override
    public void onUserLeaveHint() {
        long uiDelta = (System.currentTimeMillis() - userInteractionTime);

        super.onUserLeaveHint();
        System.out.println("Last User Interaction = "+uiDelta);

        if (uiDelta < 100)
        {
        System.out.println("Home Key Pressed");
        }
        else
        {
        System.out.println("We are leaving, but will probably be back shortly!");
        }

    }
}
