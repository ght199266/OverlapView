package lly.com.overlapView.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ScrollView;

import lly.com.overlapView.OverLapAdapter;
import lly.com.overlapView.R;

/**
 * OverlapScrollView[v 1.0.0]
 * classes:OverlapScrollView
 *
 * @author lly
 * @date 2015/10/26
 * @time 15:20
 * @description
 */
public class OverlapScrollView extends ScrollView {
    //上下文
    private Context mContext;
    //最终滚动的坐标
    private int lastScrollY;
    //填充的View
    private OverLapView overLapView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int scrollY = OverlapScrollView.this.getScrollY();
            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if (lastScrollY != scrollY) {
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }
            if (overLapView != null) {
                float itemHeight = 0f;//获取第一个Item的高度
                if (itemHeight == 0f) {
                    itemHeight = overLapView.getChildAt(0).getHeight() / 2;
                }
                int currentItem = Math.round(scrollY / itemHeight);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(scrollY, currentItem * (int) itemHeight);
                valueAnimator.setDuration(200);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.start();
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        OverlapScrollView.this.smoothScrollTo(0, value);
                        overLapView.setTranslation(value, true);
                    }
                });
            }
        }
    };


    public OverlapScrollView(Context context) {
        super(context);
        mContext = context;
    }

    public OverlapScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public OverlapScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setItemOnClickListener(onOverLapItemOnClickListener itemOnClickListener) {
        overLapView.setOnOverLapItemOnClickListener(itemOnClickListener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(mContext).inflate(R.layout.overlap_layout, null);
        overLapView = (OverLapView) view.findViewById(R.id.over_container);
        this.addView(view);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        overLapView.setScrollViewHeight(h);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        //获取滑动距离，回调
        overLapView.setTranslation(lastScrollY = this.getScrollY(), true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 20);
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setAdapter(final OverLapAdapter overLapAdapter) {
        if (overLapAdapter == null) {
            new NullPointerException("mOverLapAdapter is null");
        }
        overLapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                overLapView.setOverLapAdapter(overLapAdapter);
                overLapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public interface onOverLapItemOnClickListener {
        void onItemClickListener(int position, View v);
    }
}
