package com.conceptual.nanodegreemovieapp1.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.conceptual.nanodegreemovieapp1.R;
import com.conceptual.nanodegreemovieapp1.models.TrailerResult;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{
    private final Context mContext;
    private List<TrailerResult> mTrailerResults;

    public TrailerAdapter(List<TrailerResult> trailerResults, Context context) {
        mTrailerResults = trailerResults;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item_trailer, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position = position;
        TrailerResult result = mTrailerResults.get(position);
        String trailer = result.getType() +  " " + (position + 1);
        holder.trailer.setText(trailer);


    }

    @Override
    public int getItemCount() {
        return mTrailerResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
            TextView trailer;
            int position;
          ViewHolder(@NonNull View itemView) {
             super(itemView);
             trailer = itemView.findViewById(R.id.tvTrailer);
             trailer.setOnClickListener(view -> {
                 String uri = "https://www.youtube.com/watch?v="  +  mTrailerResults.get(position).getKey();
                 Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(uri));
                 mContext.startActivity(intent);

             });
         }
     }
}
