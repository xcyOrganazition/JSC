package cn.com.jinshangcheng.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseFragment;


/**
 * 我的Fragment
 */
public class MineFragment extends BaseFragment {


    private static MineFragment mineFragment;
    Unbinder unbinder;
    @BindView(R.id.iv_headImg)
    ImageView ivHeadImg;
    Unbinder unbinder1;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            synchronized (MineFragment.class) {
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
            }
        }
        return mineFragment;
    }


    @Override
    public View createView(LayoutInflater inflater) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, null, false);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    @Override
    public void showLoading() {

    }


    @OnClick(R.id.tv_people)
    public void onViewClicked() {
    }


    @OnClick({R.id.iv_headImg, R.id.tv_people, R.id.tv_money, R.id.tv_privacy, R.id.tv_address, R.id.tv_car, R.id.tv_order, R.id.tv_card, R.id.tv_about_us})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_headImg:
                break;
            case R.id.tv_people:
                intent = new Intent(getHoldingActivity(), PeopleActivity.class);
                break;
            case R.id.tv_money:
                intent = new Intent(getHoldingActivity(), MoneyActivity.class);
                break;
            case R.id.tv_privacy:
                intent = new Intent(getHoldingActivity(), PrivacyActivity.class);
                break;
            case R.id.tv_address:
                intent = new Intent(getHoldingActivity(), AddressManageActivity.class);
                break;
            case R.id.tv_car:
                intent = new Intent(getHoldingActivity(), CarManageActivity.class);
                break;
            case R.id.tv_order:
                intent = new Intent(getHoldingActivity(), MyOrderActivity.class);
                break;
            case R.id.tv_card:
                intent = new Intent(getHoldingActivity(), BankCardActivity.class);
                break;
            case R.id.tv_about_us:
                intent = new Intent(getHoldingActivity(), AboutUsActivity.class);
                break;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }
}
