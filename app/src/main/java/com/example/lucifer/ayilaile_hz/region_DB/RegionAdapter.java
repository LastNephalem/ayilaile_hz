package com.example.lucifer.ayilaile_hz.region_DB;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lucifer.ayilaile_hz.R;

import java.util.List;

/**
 * Created by quan on 2017/3/25.
 */
public class RegionAdapter extends BaseQuickAdapter<RegionModel> {

    public RegionAdapter(List<RegionModel> data) {
        super(R.layout.item_list_region, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, RegionModel regionModel) {
        holder.setText(R.id.name, regionModel.getName());
    }
}
