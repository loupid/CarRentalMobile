package com.example.carrentalmobile.recyclerview;

import android.view.animation.AnimationUtils;

import com.example.carrentalmobile.R;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAnimator extends DefaultItemAnimator {
    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        return super.animateRemove(holder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        //call this method when a new car is added

        holder.itemView.setAnimation(AnimationUtils.loadAnimation(
                holder.itemView.getContext(),
                R.anim.viewholder_add_anim
        ));
        return super.animateAdd(holder);
    }

    @Override
    public long getRemoveDuration() {
        return 500;
    }

    @Override
    public long getAddDuration() {
        return 500;
    }
}
