package com.example.pial.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.pial.task.R.layout.row_store_list;


public class StoreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOOTER_VIEW = 1;
    private ArrayList<Store> data;
    private static int pos,flag=0;
    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StoreListAdapter storeListAdaptery;
    private int noOfFeatch=10,count=0;
    private ArrayList<Store> storage=new ArrayList<Store>();
    public StoreListAdapter(ArrayList<Store> data,Context context,RecyclerView recyclerView,LinearLayoutManager linearLayoutManager,int flag,int count) {
        this.data=data;
        this.context=context;
        this.recyclerView=recyclerView;
        this.linearLayoutManager=linearLayoutManager;
        this.flag=flag;
        this.count=count;

    }
    // Define a view holder for Footer view

    public class FooterViewHolder extends ViewHolder{
        public FooterViewHolder(final View itemView) {
            super(itemView);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    if (flag==1)
                    {

                    }
                    else
                    {
                        noOfFeatch = data.size()+10;

                        if (noOfFeatch < count) {

                            StoreManeger storeManeger = new StoreManeger(context);
                            storage = storeManeger.getAllContact(noOfFeatch);
                            data.clear();
                            for (int i=0;i<storage.size();i++)
                            {
                                data.add(storage.get(i));

                            }
                            recyclerView.setLayoutManager(linearLayoutManager);
                            StoreListAdapter adapter = new StoreListAdapter(data, context, recyclerView, linearLayoutManager,0,count);
                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(data.size());
                        } else {
                            int temp = noOfFeatch - count;
                            noOfFeatch = noOfFeatch - temp;
                            StoreManeger storeManeger = new StoreManeger(context);
                            storage = storeManeger.getAllContact(noOfFeatch);
                            data.clear();
                            for (int i=0;i<storage.size();i++)
                            {
                                data.add(storage.get(i));

                            }
                            recyclerView.setLayoutManager(linearLayoutManager);
                            StoreListAdapter adapter = new StoreListAdapter(data, context, recyclerView, linearLayoutManager, 1,count);
                            recyclerView.setAdapter(adapter);
                            recyclerView.scrollToPosition(data.size());
                        }

                        }


                }
            });

            if (flag==1)
            {
                btn.setText("No more data");
            }


        }


    }

    // Now define the viewholder for Normal list item
    public class NormalViewHolder extends ViewHolder {
        public NormalViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

// And now in onCreateViewHolder you have to pass the correct view
// while populating the list item.

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if (viewType == FOOTER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_row, parent, false);

            FooterViewHolder vh = new FooterViewHolder(v);

            return vh;
        }

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_store_list, parent, false);

        NormalViewHolder vh = new NormalViewHolder(v);

        return vh;
    }

    // Now bind the viewholders in onBindViewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            if (holder instanceof NormalViewHolder) {
                NormalViewHolder vh = (NormalViewHolder) holder;

                vh.bindView(position);
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }

        if (data.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return data.size() + 1;
    }

// Now define getItemViewType of your own.

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,number;
        Button btn;
        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.nameTV);
            number= (TextView) itemView.findViewById(R.id.phoneTV);
            btn= (Button) itemView.findViewById(R.id.btnLoad);
            // Find view by ID and initialize here
        }

        public void bindView(int position) {
            // bindView() method to implement actions
            name.setText(data.get(position).getStoreName());
            number.setText(data.get(position).getStorePhoneNo());
            pos = position;

        }
    }
}