package com.conceptual.nanodegreemovieapp1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.conceptual.nanodegreemovieapp1.R;
import com.conceptual.nanodegreemovieapp1.models.ReviewResult;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
   private final Context mContext;
    private List<ReviewResult> mReviewResults;

    public ReviewAdapter(List<ReviewResult> reviewResults, Context context) {
        mReviewResults = reviewResults;
        mContext = context;
    }

    @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.review_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewResult result = mReviewResults.get(position);
        holder.author.setText(result.getAuthor());
        holder.content.setText(result.getContent());

    }

    @Override
    public int getItemCount() {
        return mReviewResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
            TextView author;
            TextView content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
        }
    }
}
