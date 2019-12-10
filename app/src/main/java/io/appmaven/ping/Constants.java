package io.appmaven.ping;

        import android.content.res.Resources;

public class Constants {
    public static final String EXTRA_TYPE = "io.appmaven.ping.TYPE";

    public static final int PADDLE_MARGIN = 30;

    public static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public static final long TARGET_FPS = 60;
}
