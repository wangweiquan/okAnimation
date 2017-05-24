package okanimation.simpledemo.com.okanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * 对勾动画
 * Created by wangwq.2017/5/10
 */

public class OkAnimation extends View {

    /**
     * 绘制对勾（√）动画
     */
    private ValueAnimator animator_draw_ok;

    /**
     * 是否开始绘制
     */
    private boolean isStartDraw = false;

    /**
     * 动画集
     */
    private AnimatorSet animatorSet = new AnimatorSet();

    /**
     * 对路径处理实现绘制动画效果
     */
    private PathEffect effect;

    /**
     * 获取路径长度
     */
    private PathMeasure pathMeasure;

    /**
     * 对勾画笔
     */
    private Paint okPaint;

    /**
     * 路径-用来获取对勾的路径
     */
    private Path path = new Path();

    private okAnimationListener okAnimationListener;

    public void setOkAnimationListener(okAnimationListener listener) {
        okAnimationListener = listener;
    }

    public OkAnimation(Context context) {
        this(context, null);
    }

    public OkAnimation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OkAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (okAnimationListener != null) {
                    okAnimationListener.onClickListener();
                }
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (okAnimationListener != null) {
                    okAnimationListener.animationFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initPaint() {
        okPaint = new Paint();
        okPaint.setStrokeWidth(7);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.WHITE);
        okPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        DrawOKAnimation();
        animatorSet.play(animator_draw_ok);
    }

    /**
     * 绘制对勾动画
     */
    private void DrawOKAnimation() {
        animator_draw_ok = ValueAnimator.ofFloat(1,0);//初始值与结束值之间的平滑过渡
        animator_draw_ok.setDuration(1000);//动画执行时间(ms)
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                isStartDraw = true;
                float value = (Float) valueAnimator.getAnimatedValue();
                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();//重新绘制调用者本身
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initOK(w, h);
        initAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isStartDraw) {
            canvas.drawPath(path, okPaint);
        }
    }

    /**
     * 绘制对勾路线
     */
    private void initOK(int width, int height) {
        path.moveTo((width - height) / 2 + height / 8 * 3, height / 2);
        path.lineTo((width - height) / 2 + height / 2, height / 5 * 3);
        path.lineTo((width - height) / 2 + height / 3 * 2, height / 5 * 2);

        pathMeasure = new PathMeasure(path, true);
    }

    /**
     * 启动动画
     */
    public void start() {
        animatorSet.start();
    }

    /**
     * 借口回调
     */
    public interface okAnimationListener {
        /**
         * 按钮点击事件
         */
        void onClickListener();

        /**
         * 动画完成回调
         */
        void animationFinish();
    }
}
