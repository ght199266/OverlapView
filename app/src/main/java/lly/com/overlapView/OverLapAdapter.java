package lly.com.overlapView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lly.com.overlapView.view.LolEntity;

/**
 * OverLapAdapter[v 1.0.0]
 * classes:lly.com.overlapView.OverLapAdapter
 *
 * @author lileiyi
 * @date 2015/10/27
 * @time 9:41
 * @description
 */
public class OverLapAdapter {

    private Context mContext;
    private List<LolEntity> entityList;

    public OverLapAdapter(Context context, List<LolEntity> list) {
        this.mContext = context;
        this.entityList = list;
    }

    public int getCount() {
        return entityList.size();
    }

    public View getView(int position, ViewGroup viewGroup) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
        View view1 = view.findViewById(R.id.v_container);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ImageView img_lol = (ImageView) view.findViewById(R.id.img_lol);
        TextView tv_type = (TextView) view.findViewById(R.id.textView);
        if (position == 0) {
            view1.setAlpha(0f);
            tv_title.setTextSize(24);
        } else {
            tv_title.setTextSize(16);
        }
        img_lol.setImageURI(Uri.parse(entityList.get(position).getLeagueImage()));
//        img_lol.setBackgroundResource(R.mipmap.lei);
        tv_title.setText(entityList.get(position).getLeagueName());
        tv_type.setText(entityList.get(position).getLeagueType());
        return view;
    }
}
