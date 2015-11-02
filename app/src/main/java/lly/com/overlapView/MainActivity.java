package lly.com.overlapView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import lly.com.overlapView.view.LolEntity;
import lly.com.overlapView.view.OverlapScrollView;

public class MainActivity extends AppCompatActivity {

    private OverlapScrollView overlapScrollView;
    private List<LolEntity> lolEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        overlapScrollView = (OverlapScrollView) findViewById(R.id.overlap_scrollview);
        OverLapAdapter overLapAdapter = new OverLapAdapter(MainActivity.this, lolEntities);
        overlapScrollView.setAdapter(overLapAdapter);
        overlapScrollView.setItemOnClickListener(new OverlapScrollView.onOverLapItemOnClickListener() {
            @Override
            public void onItemClickListener(int position, View v) {
//                Toast.makeText(MainActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        LolEntity lolEntity = new LolEntity();
        lolEntity.setLeagueImage("http://img4.imgtn.bdimg.com/it/u=1559068131,4087943183&fm=21&gp=0.jpg");
        lolEntity.setLeagueName("暴走萝莉金克斯");
        lolEntity.setLeagueType("ADC");
        lolEntities.add(lolEntity);

        LolEntity lolEntity1 = new LolEntity();
        lolEntity1.setLeagueImage("http://img0.imgtn.bdimg.com/it/u=682730998,1166285912&fm=21&gp=0.jpg");
        lolEntity1.setLeagueName("未来战士Ez");
        lolEntity1.setLeagueType("ADC");
        lolEntities.add(lolEntity1);

        LolEntity lolEntity2 = new LolEntity();
        lolEntity2.setLeagueImage("http://img3.imgtn.bdimg.com/it/u=343915624,107018927&fm=21&gp=0.jpg");
        lolEntity2.setLeagueName("木木");
        lolEntity2.setLeagueType("坦克");
        lolEntities.add(lolEntity2);


        LolEntity lolEntity3 = new LolEntity();
        lolEntity3.setLeagueImage("http://img1.imgtn.bdimg.com/it/u=819377616,3674482829&fm=21&gp=0.jpg");
        lolEntity3.setLeagueName("末日使者");
        lolEntity3.setLeagueType("打野");
        lolEntities.add(lolEntity3);


        LolEntity lolEntity4 = new LolEntity();
        lolEntity4.setLeagueImage("http://img2.imgtn.bdimg.com/it/u=786325352,505007748&fm=21&gp=0.jpg");
        lolEntity4.setLeagueName("瞎子");
        lolEntity4.setLeagueType("打野");
        lolEntities.add(lolEntity4);

        LolEntity lolEntity5 = new LolEntity();
        lolEntity5.setLeagueImage("http://img5.imgtn.bdimg.com/it/u=1954628066,718148193&fm=21&gp=0.jpg");
        lolEntity5.setLeagueName("剑圣");
        lolEntity5.setLeagueType("打野");
        lolEntities.add(lolEntity5);

        LolEntity lolEntity7 = new LolEntity();
        lolEntity7.setLeagueImage("http://img4.imgtn.bdimg.com/it/u=2799212575,3077174503&fm=21&gp=0.jpg");
        lolEntity7.setLeagueName("女警");
        lolEntity7.setLeagueType("打野");
        lolEntities.add(lolEntity7);
    }
}
