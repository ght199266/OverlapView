package lly.com.overlapView.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
                int currentItem = Math.round(scrollY / 240.0f);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(scrollY, currentItem * 240);
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
//                valueAnimator.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                    }
//                });
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
        overLapView = (OverLapView) view.findViewById(R.id.fragment);
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

    public void setAdapter(OverLapAdapter overLapAdapter) {
        if (overLapAdapter == null) {
            new NullPointerException("mOverLapAdapter is null");
        }
        overLapView.setOverLapAdapter(overLapAdapter);

    }

    public interface onOverLapItemOnClickListener {
        void onItemClickListener(int position, View v);
    }
}
