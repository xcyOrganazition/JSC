package cn.com.jinshangcheng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import platform.cston.httplib.bean.CarModelResult.DataEntity;
import platform.cston.httplib.bean.CarTypeResult.DataEntity.CarTypesEntity;

public class CarTypeAdapter extends BaseExpandableListAdapter {

    private List<CarTypesEntity> carTypeList;//汽车车型  父条目
    private List<List<DataEntity>> carModelListGroup;//车款列表组
    private Context mContext;


    public CarTypeAdapter(List<CarTypesEntity> carTypeList, List<List<DataEntity>> carModelListGroup, Context mContext) {
        this.carTypeList = carTypeList;
        this.carModelListGroup = carModelListGroup;
        this.mContext = mContext;
    }

    public void refreshList(List<CarTypesEntity> carTypeList, List<DataEntity> carModelList, int parentPosition) {
        this.carTypeList = carTypeList;
        List<DataEntity> curModelList = this.carModelListGroup.get(parentPosition);
        if (curModelList == null) {
            curModelList = new ArrayList<>();
        }
        curModelList.addAll(carModelList);
        this.notifyDataSetChanged();
    }

    public void refreshList(List<CarTypesEntity> carTypeList, List<List<DataEntity>> carModelListGroup) {
        this.carTypeList = carTypeList;
        this.carModelListGroup = carModelListGroup;
        com.orhanobut.logger.Logger.w("newData = " + carModelListGroup);
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return carTypeList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (ArrayUtils.hasContent(carModelListGroup) && ArrayUtils.hasContent(carModelListGroup.get(groupPosition))) {
            return carModelListGroup.get(groupPosition).size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return carTypeList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return carModelListGroup.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_car_type, null);
            viewHolder = new ParentViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ParentViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(carTypeList.get(groupPosition).typeName);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_car_model, null);
            viewHolder = new ChildViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(carModelListGroup.get(groupPosition).get(childPosition).modelName);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ChildViewHolder {
        TextView tv;

        public ChildViewHolder(View view) {
            tv = view.findViewById(R.id.tv_carModel);
        }
    }

    public class ParentViewHolder {
        TextView tv;
        TextView tvTittle;

        public ParentViewHolder(View view) {

            tv = view.findViewById(R.id.tv_carType);
            tvTittle = view.findViewById(R.id.tv_carTypeTittle);
        }
    }

}
