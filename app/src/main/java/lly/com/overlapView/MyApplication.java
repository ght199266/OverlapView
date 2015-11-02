package lly.com.overlapView;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * MyApplication[v 1.0.0]
 * classes:lly.com.overlapView.MyApplication
 *
 * @author lileiyi
 * @date 2015/11/2
 * @time 13:36
 * @description
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
    }
}
