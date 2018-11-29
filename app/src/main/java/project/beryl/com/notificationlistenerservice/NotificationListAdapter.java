package project.beryl.com.notificationlistenerservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beryl on 28-Nov-18.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Map> data;


    public NotificationListAdapter(Context context,ArrayList<Map> data){
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public NotificationListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListAdapter.MyViewHolder viewHolder, int position) {


        String title = (String) data.get(position).get("title");
        CharSequence text = (CharSequence) data.get(position).get("text");
        CharSequence subtext = (CharSequence) data.get(position).get("subtext");
        Bitmap largeIcon = (Bitmap) data.get(position).get("largeIcon");

        if (title!=null){
            viewHolder.title.setText(title);
        } if (text!=null){
            viewHolder.text.setText(text.toString());
        } if (subtext!=null){
            viewHolder.subtext.setVisibility(View.VISIBLE);
            viewHolder.subtext.setText(subtext.toString());
        }else {
            viewHolder.subtext.setVisibility(View.GONE);
        }
        if (largeIcon!=null){
            viewHolder.largeIcon.setImageBitmap(largeIcon);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView text;
        protected TextView subtext;
        protected ImageView largeIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.nt_title);
            text = (TextView) itemView.findViewById(R.id.nt_text);
            subtext = (TextView) itemView.findViewById(R.id.nt_subtext);
            largeIcon = (ImageView) itemView.findViewById(R.id.nt_largeicon);
        }
    }
}
