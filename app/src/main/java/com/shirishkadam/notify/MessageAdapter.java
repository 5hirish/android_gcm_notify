package com.shirishkadam.notify;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by felix on 12/1/16.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    ArrayList <MessageActivity.MessageData> messageDatas;

    public MessageAdapter(Context applicationContext, ArrayList<MessageActivity.MessageData> message_dataset) {
        context = applicationContext;
        messageDatas = message_dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view,parent,false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(messageDatas.get(position).Title);
        holder.message.setText(messageDatas.get(position).Message);

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = messageDatas.get(position).Id;
                Intent in = new Intent(context,DetailActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("MessageId",id);
                in.putExtra("Intent","Detail");
                context.startActivity(in);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return messageDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        RelativeLayout rl;
        TextView title;
        TextView message;

        public ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.message_card_view);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            title = (TextView) itemView.findViewById(R.id.title);
            message = (TextView) itemView.findViewById(R.id.body);

        }
    }
}
