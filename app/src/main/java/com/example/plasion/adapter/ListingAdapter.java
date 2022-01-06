package com.example.plasion.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.plasion.R;
import com.example.plasion.models.Listing;
import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingViewHolder> {

    private ArrayList<Listing> dataList;

    public ListingAdapter(ArrayList<Listing> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listing_row, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListingViewHolder holder, int position) {
        holder.nameDisplay.setText(dataList.get(position).getUserFullName());
        holder.locationDisplay.setText(dataList.get(position).getUserLocation());
        holder.bloodDisplay.setText(dataList.get(position).getUserBlood());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ListingViewHolder extends RecyclerView.ViewHolder {
        private TextView nameDisplay, locationDisplay, bloodDisplay;

        public ListingViewHolder(View itemView) {
            super(itemView);
            nameDisplay = (TextView) itemView.findViewById(R.id.name_display);
            locationDisplay = (TextView) itemView.findViewById(R.id.location_display);
            bloodDisplay = (TextView) itemView.findViewById(R.id.blood_display);
        }
    }
}
