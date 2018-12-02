package cn.com.jinshangcheng.widget;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.adapter.LicenseAdapter;
import cn.com.jinshangcheng.config.ConstParams;
import cn.com.jinshangcheng.listener.OnItemViewClickListener;
import cn.com.jinshangcheng.ui.mine.AddCarActivity;

//选择车牌省缩写Dialog
public class LicenseDialog extends DialogFragment {

    RecyclerView recyclerView;
    private LicenseAdapter adapter;
    OnItemViewClickListener listener;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_license, null);
        Window window = this.getDialog().getWindow();
        //去掉dialog默认的padding
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.CENTER;
        //设置dialog的动画
        lp.windowAnimations = android.R.anim.fade_in;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable());
        recyclerView = rootView.findViewById(R.id.rv);
        initRecyclerView();
        return rootView;
    }




    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        adapter = new LicenseAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setListener(new OnItemViewClickListener() {
            @Override
            public void onViewClick(int position, View view) {
                AddCarActivity activity = (AddCarActivity) getActivity();
                activity.selectCity(ConstParams.cityArray[position]);
            }
        });
    }

}
