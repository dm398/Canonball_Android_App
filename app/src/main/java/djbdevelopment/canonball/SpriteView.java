package djbdevelopment.canonball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class SpriteView extends View {
    CanonActivity controller;

    static String tag = "Bubble Sprite View: ";


    static Paint paintWhite;

    static int width, height;

    static {
        float textSize = 40;
        paintWhite = new Paint();
        paintWhite.setColor(Color.WHITE);
        paintWhite.setStyle(Paint.Style.FILL);
        paintWhite.setAntiAlias(true);
        paintWhite.setTextSize(textSize);



    }
    public void onDraw(Canvas g) {
        DisplayMetrics metrics = new DisplayMetrics();
        controller.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        height = metrics.heightPixels;
        width = metrics.widthPixels;

        // get the model
        List<Sprite> sprites = controller.getModel().sprites;
        // System.out.println(tag + "onDraw: " + sprites.get(0).v + " : " + sprites.get(0).s);
        for (Sprite sprite : sprites) {

            sprite.draw(g);
        }

        g.drawText( ( Integer.toString(controller.model.score)), 50, 50, paintWhite);
        g.drawText( ( Integer.toString(controller.model.timeRemaining)), width - 170 , 50, paintWhite);
    }

    public SpriteView(Context context) {
        super(context);
        this.controller = (CanonActivity) context;
    }

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.controller = (CanonActivity) context;
    }

    public SpriteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.controller = (CanonActivity) context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        List<Sprite> sprites = controller.getModel().sprites;

        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
             controller.getModel().click(x, y);

        }
        return super.onTouchEvent(event);
    }
}
