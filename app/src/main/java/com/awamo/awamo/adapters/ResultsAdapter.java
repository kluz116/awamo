package com.awamo.awamo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.awamo.awamo.R;
import com.awamo.awamo.models.Results;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Results> resultList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView numberone, numbertwo,response, expected,pased;
        public ImageView  overflow;
        CardView card_view_result;

        public MyViewHolder(View view) {
            super(view);
            card_view_result = view.findViewById(R.id.card_view_result);
            numberone = view.findViewById(R.id.number1);
            numbertwo = view.findViewById(R.id.number2);
            response = view.findViewById(R.id.response);
            expected = view.findViewById(R.id.expected);
            pased= view.findViewById(R.id.passed);
            overflow = view.findViewById(R.id.overflow);
        }
    }


    public ResultsAdapter(Context mContext, List<Results> albumList) {
        this.mContext = mContext;
        this.resultList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.results_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Results res= resultList.get(position);
        holder.numberone.setText("Number one : "+res.getNumber_one());
        holder.numbertwo.setText("Number two : "+res.getNumber_two());
        holder.expected.setText("Expected : "+res.getExpected());
        holder.response.setText("Response : "+res.getResponse());
        holder.pased.setText("Passed : "+res.getPassed());

        String str = "No";

        if(res.getPassed().equals(str)){
            holder.card_view_result.setCardBackgroundColor(Color.RED);
        }else{
            holder.card_view_result.setCardBackgroundColor(Color.YELLOW);
        }

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), resultList.size());
            }
        });


    }
    @Override
    public int getItemCount() {
        return resultList.size();
    }
}
