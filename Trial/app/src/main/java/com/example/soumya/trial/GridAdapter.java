package com.example.soumya.trial;

/**
 * Created by Soumya on 30-11-2015.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 28/02/2015.
 */
public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    List<EndangeredItem> mItems;

    public GridAdapter() {
        super();
        mItems = new ArrayList<EndangeredItem>();
        EndangeredItem species = new EndangeredItem();
        species.setName("Amur Leopard");
        species.setThumbnail(R.drawable.abc_ab_share_pack_mtrl_alpha);
        mItems.add(species);

        species = new EndangeredItem();
        species.setName("Black Rhino");
        species.setThumbnail(R.drawable.abc_btn_switch_to_on_mtrl_00001);
        mItems.add(species);

        species = new EndangeredItem();
        species.setName("Orangutan");
        species.setThumbnail(R.drawable.abc_ic_clear_mtrl_alpha);
        mItems.add(species);

        species = new EndangeredItem();
        species.setName("Sea Lions");
        species.setThumbnail(R.drawable.abc_textfield_search_material);
        mItems.add(species);

        species = new EndangeredItem();
        species.setName("Indian Elephant");
        species.setThumbnail(R.drawable.abc_list_selector_disabled_holo_light);
        mItems.add(species);

        species = new EndangeredItem();
        species.setName("Giant Panda");
        species.setThumbnail(R.drawable.abc_ic_menu_copy_mtrl_am_alpha);
        mItems.add(species);

        species = new EndangeredItem();
        species.setName("Snow Leopard");
        species.setThumbnail(R.drawable.abc_cab_background_top_mtrl_alpha);
        mItems.add(species);

        species = new EndangeredItem();
        species.setName("Dolphin");
        species.setThumbnail(R.drawable.abc_dialog_material_background_dark);
        mItems.add(species);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        EndangeredItem nature = mItems.get(i);
        viewHolder.tvspecies.setText(nature.getName());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvspecies;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvspecies = (TextView)itemView.findViewById(R.id.tv_species);
        }
    }
}