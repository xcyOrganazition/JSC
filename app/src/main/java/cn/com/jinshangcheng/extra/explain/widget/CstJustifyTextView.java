

/*
 * Copyright (c) 2016.  CST.All Rights Reserved
 *
 * @author:zhouzunlai
 *
 * @date: 2016.5.7.
 *
 */

package cn.com.jinshangcheng.extra.explain.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;

public class CstJustifyTextView extends android.support.v7.widget.AppCompatTextView {

    private String text;

    private float textSize;

    private float paddingLeft;

    private float paddingRight;

    private int textColor;

    private Paint paint1 = new Paint();

    private float textShowWidth;

    public CstJustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        text = this.getText().toString();
        textSize = this.getTextSize();
        textColor = this.getTextColors().getDefaultColor();
        paddingLeft = this.getPaddingLeft();
        paddingRight = this.getPaddingRight();
        paint1.setTextSize(textSize);
        paint1.setColor(textColor);
        paint1.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        textShowWidth = this.getMeasuredWidth() - paddingLeft - paddingRight;
        int lineCount = 0;
        text = this.getText().toString();
        if (text == null) {
            return;
        }
        char[] textCharArray = text.toCharArray();
        float drawedWidth = 0;
        float charWidth;
        for (int i = 0; i < textCharArray.length; i++) {
            charWidth = paint1.measureText(textCharArray, i, 1);
            if (textCharArray[i] == '\n') {
                lineCount++;
                drawedWidth = 0;
                continue;
            }
            if (textShowWidth - drawedWidth < charWidth) {
                lineCount++;
                drawedWidth = 0;
            }
            canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth,
                    (lineCount + 1) * textSize, paint1);
            drawedWidth += charWidth;
        }

        Layout layout = getLayout();
        int lines = layout.getLineCount();
        float lineHeight = layout.getHeight() / lines;
        setHeight((int)((lineCount + 1) * lineHeight));
    }
}
