package com.example.plasion.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.plasion.R;
import com.example.plasion.activities.DetailActivity;
import com.example.plasion.models.Listing;
import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingViewHolder> {

    private ArrayList<Listing> dataList;
    private Context context;

    public ListingAdapter(Context context, ArrayList<Listing> dataList) {
        this.context = context;
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
        String bloodText = "Golongan Darah " + dataList.get(position).getUserBlood();

        holder.nameDisplay.setText(dataList.get(position).getUserFullName());
        holder.locationDisplay.setText(dataList.get(position).getUserLocation());
        holder.bloodDisplay.setText(bloodText);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("LISTING_KEY", dataList.get(position).getListingId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ListingViewHolder extends RecyclerView.ViewHolder {
        private TextView nameDisplay, locationDisplay, bloodDisplay;
        private LinearLayout linearLayout;

        public ListingViewHolder(View itemView) {
            super(itemView);
            nameDisplay = (TextView) itemView.findViewById(R.id.name_display);
            locationDisplay = (TextView) itemView.findViewById(R.id.location_display);
            bloodDisplay = (TextView) itemView.findViewById(R.id.blood_display);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item);
        }
    }
}
