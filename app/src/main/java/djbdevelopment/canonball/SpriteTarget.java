package djbdevelopment.canonball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;


public class SpriteTarget extends View {
    static Paint paintBlack;

    static int width, height;

    static {
        float textSize = 40;
        paintBlack = new Paint();
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStyle(Paint.Style.FILL);
        paintBlack.setAntiAlias(true);
        paintBlack.setTextSize(textSize);
    }



    public SpriteTarget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    CanonActivity controller;
    public SpriteTarget(Context context) {
        super(context);
        this.controller = (CanonActivity) context;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DisplayMetrics metrics = new DisplayMetrics();
        controller.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        height = metrics.heightPixels;
        width = metrics.widthPixels;


        canvas.drawText( ( Integer.toString(controller.model.score)), 50, 50, paintBlack);
        canvas.drawText( ( Integer.toString(controller.model.timeRemaining)), width - 170 , 50, paintBlack);

        Paint red = new Paint();
        red.setARGB(255, 255, 0, 0);

        Paint white = new Paint();
        white.setARGB(255, 255, 255, 255);

        int w = (canvas.getWidth()) / 5, h = (canvas.getHeight()) / 5; // target should be about 1/5 of screen size

        for (int i = 0; i < 5; i ++) {
            canvas.drawOval(new RectF( w * i/10 , h*i/10, w*(10-i)/10, h*(10-i)/10), i% 2 == 0? red: white);
        }
    }
}
