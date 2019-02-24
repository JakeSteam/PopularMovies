package uk.co.jakelee.popularmovies.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uk.co.jakelee.popularmovies.model.Trailer;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private final List<Trailer> trailerList;

    public TrailerAdapter(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new TrailerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer trailer = trailerList.get(position);
        holder.nameView.setText(trailer.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trailer.getSite().equals("YouTube")) {
                    String url = String.format(v.getContext().getString(R.string.youtube_url), trailer.getKey());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    static class TrailerViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;

        TrailerViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.trailerName);
        }
    }
}