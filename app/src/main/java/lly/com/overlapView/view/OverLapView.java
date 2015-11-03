package lly.com.overlapView.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import lly.com.overlapView.OverLapAdapter;
import lly.com.overlapView.R;
import lly.com.overlapView.Utils;

/**
 * OverLapView[v 1.0.0]
 * classes:OverLapView
 *
 * @author lileiyi
 * @date 2015/10/26
 * @time 15:27
 * @description
 */
public class OverLapView extends FrameLayout {

    //上下文对象
    private Context mContext;
    //item高度
    private int itemHeight = 240;
    //item数量
    private int itemCount = 10;
    //正常显示的TextSize大小
    private static final float NORMALTEXTSIZE = 24f;
    //隐藏Item的字体大小
    private static final float HIDETEXTSIZE = 16f;
    //屏幕的宽
    private int screenWeigh;
    //当前滑动的距离
    private int currentScrollY;
    //当前可用容器的高度（减去状态栏和Titlebar）
    private int scrollViewHeight = 0;
    //底部最后一个View
    private View lastView;
    //适配器
    private OverLapAdapter overLapAdapter;
    //点击事件处理回调
    private OverlapScrollView.onOverLapItemOnClickListener onOverLapItemOnClickListener;

    public OverLapView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public OverLapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    //初始化
    private void init() {
        screenWeigh = Utils.getWindowWidth(mContext);
        Log.v("leizi", "W:" + screenWeigh);
        Log.v("leizi", "H:" + Utils.getWindowHeight(mContext));

        Log.v("leizi", "DP.TO PX:" + Utils.dip2px(mContext, 240));
    }

    public void setScrollViewHeight(int scrollViewHeight) {
        this.scrollViewHeight = scrollViewHeight;
    }

    public void setOverLapAdapter(OverLapAdapter overLapAdapter) {
        this.overLapAdapter = overLapAdapter;
        addView();
    }

    public void setOnOverLapItemOnClickListener(OverlapScrollView.onOverLapItemOnClickListener onOverLapItemOnClickListener) {
        this.onOverLapItemOnClickListener = onOverLapItemOnClickListener;
    }

    /**
     * 添加View
     */
    public void addView() {
        for (int i = 0; i < overLapAdapter.getCount(); i++) {
            final View view = overLapAdapter.getView(i, this);
            FrameLayout.LayoutParams layoutParams = new LayoutParams(new ViewGroup.LayoutParams(screenWeigh, Utils.dip2px(mContext, 240)));
            view.setLayoutParams(layoutParams);
            final int index = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOverLapItemOnClickListener != null) {
                        onOverLapItemOnClickListener.onItemClickListener(index, view);
                    }
                }
            });
            this.addView(view);
        }
        lastView = LayoutInflater.from(mContext).inflate(R.layout.last_empty_layout, null);
        this.addView(lastView);
    }

    /**
     * 设置View的移动
     */
    public void setTranslation(int scrolly, boolean ischeck) {
        if (ischeck) {
            onCheckRapidSlide(scrolly, currentScrollY);
        }
        int index = (int) Math.ceil(scrolly / 240.0);
        View view = getChildAt(index);//获取当前需要滚动的View
        if (view == null || index == 0) {
            return;
        }
        currentScrollY = scrolly;
        setViewPropertyValue(index, view, scrolly);
        int newScroll = (int) (scrolly - ((index - 1) * 240.0));
        view.setTranslationY(-newScroll);

//        if (index == overLapAdapter.getCount() - 1) {
//            Log.v("leizi", "滑动最底部了。。" + scrolly);
//        }
    }

    /**
     * 检查是否滑动过快
     */
    public void onCheckRapidSlide(int scrolly, int oldscrolly) {
        if (scrolly > oldscrolly) {
            while (scrolly - oldscrolly > 20) {
                oldscrolly += 5;
                setTranslation(oldscrolly, false);
            }
        } else {
            while (oldscrolly - scrolly > 20) {
                oldscrolly -= 5;
                setTranslation(oldscrolly, false);
            }
        }
    }

    /**
     * 动态设置滑动过程中View变化
     *
     * @param index
     * @param v
     * @param scrollY
     */
    public void setViewPropertyValue(int index, View v, int scrollY) {
        if (-currentScrollY == scrollY) {
            return;
        }
        TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
        if (tv_title == null) {
            return;
        }
        float value = ((scrollY - (index - 1) * (itemHeight * 1f)) / (itemHeight * 1f));
        Log.v("leizi", "Value:=" + value);
        tv_title.setScaleX(1 + value / 2);
        tv_title.setScaleY(1 + value / 2);
        View view = v.findViewById(R.id.v_container);//改变透明度
        view.setAlpha(1 - value);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置底部View的高度
        int lastViewHeight = (scrollViewHeight - Utils.dip2px(mContext, 240));
        if (lastView != null) {
            FrameLayout.LayoutParams layoutParams1 = new LayoutParams(new ViewGroup.LayoutParams(screenWeigh, lastViewHeight));
            lastView.setLayoutParams(layoutParams1);
        }
        //设置FrameLayout的高度
        int frameLayoutHeight = (getChildCount() * Utils.dip2px(mContext, itemHeight / 2)) + lastViewHeight;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(screenWeigh, frameLayoutHeight));
        this.setLayoutParams(layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (i == 0) {
                v.layout(0, 0, screenWeigh, itemHeight * 2);
            } else {
                v.layout(0, itemHeight * (i + 1), screenWeigh, (itemHeight * 2) * (i + 1));
            }
        }
    }


}
