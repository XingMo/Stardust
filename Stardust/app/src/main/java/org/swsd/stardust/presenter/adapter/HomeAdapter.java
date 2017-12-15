package org.swsd.stardust.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.swsd.stardust.R;
import org.swsd.stardust.model.bean.NoteBean;
import org.swsd.stardust.view.activity.LoginActivity;
import org.swsd.stardust.view.activity.MainActivity;
import org.swsd.stardust.view.activity.NoteActivity;

import java.util.List;
import java.util.Random;

/**
 *    author     :  张昭锡
 *    time       :  2017/11/12
 *    description:  主页适配器
 *    version:   :  1.0
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private static final String TAG = "HomeAdapter";

    private List<NoteBean>mNoteList;
    private Context mContext;
    private int[] mLightspotImages = {R.drawable.iv_home_lightspot_small,R.drawable.iv_home_lightspot_medium,
                                        R.drawable.iv_home_lightspot_big,R.drawable.iv_home_lightspot_large};

    static class ViewHolder extends RecyclerView.ViewHolder{
        View lightSpotView;
        ImageView lightSpotImage;
        TextView lightSportTextUpper;
        TextView lightSportTextLower;

        public ViewHolder(View view){
            super(view);
            lightSpotView = view;
            lightSpotImage = (ImageView)view.findViewById(R.id.iv_home_lightspot);
            lightSportTextUpper = (TextView)view.findViewById(R.id.tv_home_lightspot_upper);
            lightSportTextLower = (TextView)view.findViewById(R.id.tv_home_lightspot_lower);
        }
    }

    public HomeAdapter(Context context, List<NoteBean>noteList){
        mContext = context;
        mNoteList = noteList;

        //随机没有记录实体的记录
        int size = 8 - mNoteList.size();
        Random random = new Random();
        for (int i = 0;i < size;i++){
            NoteBean note = new NoteBean();
            note.setId(-1);
            int index = random.nextInt(mNoteList.size() + 1);
            mNoteList.add(index,note);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lightspot,parent,false);
        final ViewHolder holder = new ViewHolder(view);


        holder.lightSpotImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position= holder.getAdapterPosition();
                NoteBean note = mNoteList.get(position);
                Intent intent = new Intent(mContext, NoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note",note);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int t;
        int sum = mNoteList.size();

        //根据记录总数将记录分成四部分，选择记录在主页光点的显示大小
        if (position <= sum / 4)   t = 3;
        else if (position > sum / 4 && position <= 2 * (sum / 4)) t = 2;
        else if (position > 2 * (sum / 4) && position < 3*(sum / 4))  t = 1;
        else t = 0;

        NoteBean note = mNoteList.get(position);

        //设置记录光点不规则排列
        if (position < 4) {
            int length = (position == 0 || position == 3) ? 7 : 4;
            holder.lightSpotImage.setImageResource(mLightspotImages[t]);
            holder.lightSportTextUpper.setText(getRandomLengthNameUpper("\n", length));
        } else{
            int length = (position == 4 || position == 5) ? 8 : 2;
            holder.lightSpotImage.setImageResource(mLightspotImages[t]);
            holder.lightSportTextUpper.setText(getRandomLengthNameUpper("\n",length));

        }


        //隐藏随机生成的没有记录实体的记录
        holder.lightSpotView.setVisibility(note.getId() == -1?View.INVISIBLE:View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }


    /**
     *    author     :  张昭锡
     *    time       :  2017/11/12
     *    description:  随机生成textview行数，使瀑布流不规则排列
     *    version:   :  1.0
     */
    private String getRandomLengthNameUpper(String name,int length){
//        Random random = new Random();
//        int length = random.nextInt(2);
        StringBuilder builder = new StringBuilder();
        for (int i = 0;i < length;i++){
            builder.append(name);
        }
        return builder.toString();
    }

    private String getRandomLengthNameLower(String name){
        Random random = new Random();
        int length = random.nextInt(1);

        StringBuilder builder = new StringBuilder();
        for (int i = 0;i < length;i++){
            builder.append(name);
        }
        return builder.toString();
    }

}