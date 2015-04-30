package com.amulyakhare.textdrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * @author amulya
 * @datetime 14 Oct 2014, 3:53 PM
 */
public class TextDrawableMiddle extends ShapeDrawable {

    private final Paint textPaint;
    private final Paint borderPaint;
    private static final float SHADE_FACTOR = 0.9f;
    private final String text1;
    private final String text2;
    private final int color;
    private final RectShape shape;
    private final int height;
    private final int width;
    private final int fontSize;
    private final float radius;
    private final int borderThickness;

    private TextDrawableMiddle(Builder builder) {
        super(builder.shape);

        // shape properties
        shape = builder.shape;
        height = builder.height;
        width = builder.width;
        radius = builder.radius;

        // text and color
        text1 = builder.toUpperCase ? builder.text1.toUpperCase() : builder.text1;
        text2 = builder.toUpperCase ? builder.text2.toUpperCase() : builder.text2;
        color = builder.color;

        // text paint settings
        fontSize = builder.fontSize;
        textPaint = new Paint();
        textPaint.setColor(builder.textColor);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(builder.isBold);
        textPaint.setStyle(Paint.Style.FILL);
        //textPaint.setTypeface(builder.font);  Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

      //  textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(builder.borderThickness);

        // border paint settings
        borderThickness = builder.borderThickness;
        borderPaint = new Paint();
        borderPaint.setColor(getDarkerShade(color));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderThickness);

        // drawable paint color
        Paint paint = getPaint();
        paint.setColor(color);

    }

    private int getDarkerShade(int color) {
        return Color.rgb((int)(SHADE_FACTOR * Color.red(color)),
                (int)(SHADE_FACTOR * Color.green(color)),
                (int)(SHADE_FACTOR * Color.blue(color)));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Rect r = getBounds();


        // draw border
        if (borderThickness > 0) {
            drawBorder(canvas);
        }

        int count = canvas.save();
        canvas.translate(r.left, r.top);

        // draw text
        int width = this.width < 0 ? r.width() : this.width;
        int height = this.height < 0 ? r.height() : this.height;
//        int fontSize = this.fontSize < 0 ? (Math.min(width, height) / 2) : this.fontSize;
//        textPaint.setTextSize(fontSize);
//       canvas.drawText(text1, width / 2, height / 4 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);
//        int fontSize2 = this.fontSize < 0 ? (Math.min(width, height) *1/4) : this.fontSize;
//use decimal to fraction converters

        //int fontSize = this.fontSize < 0 ? (Math.min(width, height) *1/4) : this.fontSize;
        int fontSize = this.fontSize < 0 ? (Math.min(width, height) * 7/20) : this.fontSize;
        textPaint.setTextSize(fontSize);


        //height / 4 y axis hight  23/50 = 0.46
        canvas.drawText(text1, width / 2, height *23/50 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);



        int fontSize2 = this.fontSize < 0 ? (Math.min(width, height) *1/5) : this.fontSize;

        textPaint.setTextSize(fontSize2);
        //textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        //canvas.drawText(text2, width / 2, height *3/4 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);

        //height / 4 y axis hight 75%
        canvas.drawText(text2, width / 2, height *3/4 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);


        canvas.restoreToCount(count);

        //arrow triangle
       /* int mPointHeight=5;
        int mPointedHeight=5;

        Point a = new Point(0, 0);
        Point b = new Point(width, 0);
        Point c = new Point(width, height - mPointHeight);//mPointedHeight is the length of the triangle... in this case we have it dynamic and can be changed.

        Point d = new Point((width/2)+(mPointedHeight/2), height - mPointHeight);
        Point e = new Point((width/2), height);// this is the sharp point of the triangle
        Point f = new Point((width/2)-(mPointedHeight/2), height - mPointHeight);
        Point g = new Point(0, height - mPointHeight);

        Path path = new Path();
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(d.x, d.y);
        path.lineTo(e.x, e.y);
        path.lineTo(f.x, f.y);
        path.lineTo(g.x, g.y);
        canvas.drawPath(path, textPaint);*/


    }

    private void drawBorder(Canvas canvas) {
        RectF rect = new RectF(getBounds());
        rect.inset(borderThickness/2, borderThickness/2);

        if (shape instanceof OvalShape) {
            canvas.drawOval(rect, borderPaint);
        }
        else if (shape instanceof RoundRectShape) {
            canvas.drawRoundRect(rect, radius, radius, borderPaint);
        }
        else {
            canvas.drawRect(rect, borderPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        textPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    public static IShapeBuilder builder() {
        return new Builder();
    }

    public static class Builder implements IConfigBuilder, IShapeBuilder, IBuilder {

        private String text1;
        private String text2;

        private int color;

        private int borderThickness;

        private int width;

        private int height;

        private Typeface font;

        private RectShape shape;

        public int textColor;

        private int fontSize;

        private boolean isBold;

        private boolean toUpperCase;

        public float radius;

        private Builder() {
            text1 = "";
            text2 = "";
            color = Color.GRAY;
            textColor = Color.WHITE;
            borderThickness = 0;
            width = -1;
            height = -1;
            shape = new RectShape();
            font = Typeface.create("sans-serif-light", Typeface.NORMAL);
            fontSize = -1;
            isBold = false;
            toUpperCase = false;
        }

        public IConfigBuilder width(int width) {
            this.width = width;
            return this;
        }

        public IConfigBuilder height(int height) {
            this.height = height;
            return this;
        }

        public IConfigBuilder textColor(int color) {
            this.textColor = color;
            return this;
        }

        public IConfigBuilder withBorder(int thickness) {
            this.borderThickness = thickness;
            return this;
        }

        public IConfigBuilder useFont(Typeface font) {
            this.font = font;
            return this;
        }

        public IConfigBuilder fontSize(int size) {
            this.fontSize = size;
            return this;
        }

        public IConfigBuilder bold() {
            this.isBold = true;
            return this;
        }

        public IConfigBuilder toUpperCase() {
            this.toUpperCase = true;
            return this;
        }

        @Override
        public IConfigBuilder beginConfig() {
            return this;
        }

        @Override
        public IShapeBuilder endConfig() {
            return this;
        }

        @Override
        public IBuilder rect() {
            this.shape = new RectShape();
            return this;
        }

        @Override
        public IBuilder round() {
            this.shape = new OvalShape();
            return this;
        }

        @Override
        public IBuilder roundRect(int radius) {
            this.radius = radius;
            float[] radii = {radius, radius, radius, radius, radius, radius, radius, radius};
            this.shape = new RoundRectShape(radii, null, null);
            return this;
        }

        @Override
        public TextDrawableMiddle buildRect(String text, int color) {
            rect();
            return build(text, color);
        }

        @Override
        public TextDrawableMiddle buildRoundRect(String text, int color, int radius) {
            roundRect(radius);
            return build(text, color);
        }

        @Override
        public TextDrawableMiddle buildRound(String text, int color) {
            round();
            return build(text, color);
        }

        @Override
        public TextDrawableMiddle build(String text, int color) {
            this.color = color;
            this.text1 = text;
            return new TextDrawableMiddle(this);
        }
//=======================================
        @Override
        public TextDrawableMiddle buildRect(String text1,String text2, int color) {
            rect();
            return build(text1,text2, color);
        }

        @Override
        public TextDrawableMiddle buildRoundRect(String text1,String text2, int color, int radius) {
            roundRect(radius);
            return build(text1,text2,  color);
        }

        @Override
        public TextDrawableMiddle buildRound(String text1,String text2, int color) {
            round();
            return build(text1,text2, color);
        }

        @Override
        public TextDrawableMiddle build(String text1,String text2 ,int color) {
            this.color = color;
            this.text1 = text1;
            this.text2 = text2;
            return new TextDrawableMiddle(this);
        }
    }

    public interface IConfigBuilder {
        public IConfigBuilder width(int width);

        public IConfigBuilder height(int height);

        public IConfigBuilder textColor(int color);

        public IConfigBuilder withBorder(int thickness);

        public IConfigBuilder useFont(Typeface font);

        public IConfigBuilder fontSize(int size);

        public IConfigBuilder bold();

        public IConfigBuilder toUpperCase();

        public IShapeBuilder endConfig();
    }

    public static interface IBuilder {

        public TextDrawableMiddle build(String text1, String text2, int color);
        public TextDrawableMiddle build(String text, int color);
    }

    public static interface IShapeBuilder {

        public IConfigBuilder beginConfig();

        public IBuilder rect();

        public IBuilder round();

        public IBuilder roundRect(int radius);

        public TextDrawableMiddle buildRect(String text, int color);

        public TextDrawableMiddle buildRoundRect(String text, int color, int radius);

        public TextDrawableMiddle buildRound(String text, int color);
        //=============

        public TextDrawableMiddle buildRect(String text1, String text2, int color);
        public TextDrawableMiddle buildRoundRect(String text1, String text2, int color, int radius);

        public TextDrawableMiddle buildRound(String text1, String text2, int color);
    }
}