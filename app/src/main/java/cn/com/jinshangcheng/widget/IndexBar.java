package cn.com.jinshangcheng.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.utils.DensityUtil;
import com.orhanobut.logger.Logger;


/**
 * 首字母索引View
 */
public class IndexBar extends View {

    private static final String[] LETTERS = new String[]{"A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private float mTextHeight;//字体高度
    private int mCellWidth;
    private int mCellHeight;
    private Paint paint;
    private int currentIndex;//当前选中的字母index
    private OnLetterChangeListener onLetterChangeListener;

    public IndexBar(Context context) {
        this(context, null);
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener) {
        this.onLetterChangeListener = onLetterChangeListener;
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.textBlack));
        int size = DensityUtil.sp2px(getContext(), 14);
        paint.setTextSize(size);
        //消除锯齿
        paint.setAntiAlias(true);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        mTextHeight = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCellWidth = getMeasuredWidth();
        mCellHeight = getMeasuredHeight() / LETTERS.length;
    }

    /**
     * 绘制字母
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < LETTERS.length; i++) {
            String letter = LETTERS[i];
            float mTextWidth = paint.measureText(letter);
            float x = (mCellWidth - mTextWidth) * 0.5f;
            float y = (mCellHeight + mTextHeight) * 0.5f + mCellHeight * i;
            if (i == currentIndex) {
                paint.setColor(getResources().getColor(R.color.textBlack));
            } else {
                paint.setColor(getResources().getColor(R.color.textBlack));
            }
            canvas.drawText(letter, x, y, paint);
        }
    }

    /**
     * 处理 按下 移动 手指抬起
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("我按下了!!");
                int downY = (int) event.getY();
                //获取当前索引
                currentIndex = downY / mCellHeight;
                if (currentIndex < 0 || currentIndex > LETTERS.length - 1) {

                } else {
//                   ToastUtil.showToast(getContext(),LETTERS[currentIndex]);
                    if (onLetterChangeListener != null) {
                        onLetterChangeListener.onLetterChange(LETTERS[currentIndex]);
                    }
                }
                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("我移动了!!");
                int moveY = (int) event.getY();
                //获取当前索引
                currentIndex = moveY / mCellHeight;
                if (currentIndex < 0 || currentIndex > LETTERS.length - 1) {

                } else {
//                    ToastUtil.showToast(getContext(),LETTERS[currentIndex]);
                    if (onLetterChangeListener != null) {
                        onLetterChangeListener.onLetterChange(LETTERS[currentIndex]);
                    }
                }
                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("我手指抬起了!!");
                currentIndex = -1;
                //手动刷新
                invalidate();
                //表示手指抬起了
                if (onLetterChangeListener != null) {
                    onLetterChangeListener.onReset();
                }
                break;
        }
        // 为了 能够接受  move+up事件
        return true;
    }

    /**
     * 字母变化监听
     */
    public interface OnLetterChangeListener {
        void onLetterChange(String letter);

        //手指抬起
        void onReset();
    }
}
