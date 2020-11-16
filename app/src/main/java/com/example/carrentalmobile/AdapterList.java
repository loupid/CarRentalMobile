package com.example.carrentalmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.carrentalmobile.Model.Cars;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {

    List<Cars> carsList;


    public AdapterList(List<Cars> carsList) {
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(inflater.inflate(R.layout.car_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.carName.setText(carsList.get(position).getCarname());
        holder.description.setText(carsList.get(position).getDescription());
        holder.numberPlace.setText(carsList.get(position).getSeatcount());
        holder.pricePerDay.setText(carsList.get(position).getTypename());
        holder.location.setText(carsList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView carName, description, numberPlace, pricePerDay, location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            carName = itemView.findViewById(R.id.CarName);
            description = itemView.findViewById(R.id.Description);
            numberPlace = itemView.findViewById(R.id.NumberPlace);
            pricePerDay = itemView.findViewById(R.id.PricePerDay);
            location = itemView.findViewById(R.id.Location);
        }
    }
}
