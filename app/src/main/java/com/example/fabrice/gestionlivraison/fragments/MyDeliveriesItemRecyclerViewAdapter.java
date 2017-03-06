package com.example.fabrice.gestionlivraison.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fabrice.gestionlivraison.R;
import com.example.fabrice.gestionlivraison.fragments.MyDeliveriesItemFragment.OnListFragmentInteractionListener;
import com.example.fabrice.gestionlivraison.fragments.dummy.MyDeliveriesContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
class MyDeliveriesItemRecyclerViewAdapter extends RecyclerView.Adapter<MyDeliveriesItemRecyclerViewAdapter.ViewHolder> {
    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    MyDeliveriesItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_deliveries_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mSender.setText(mValues.get(position).sender);
        holder.mReceiver.setText(mValues.get(position).receiver);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener)
                {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mSender;
        final TextView mReceiver;
        DummyItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mSender = (TextView) view.findViewById(R.id.txtSender);
            mReceiver = (TextView) view.findViewById(R.id.txtReceiver);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
