package com.jinshangcheng.listener;

import android.view.View;

/**
 * RecyclerView 所有组件点击回调
 * 包括条目被点击和子组件被点击 可以根据Item的Id来判断被点击组件
 */
public interface OnItemViewClickListener {
    void onViewClick(int position, View view);
}
