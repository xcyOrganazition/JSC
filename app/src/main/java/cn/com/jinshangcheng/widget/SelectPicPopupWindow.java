package cn.com.jinshangcheng.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.utils.PictureSelectorUtils;

public class SelectPicPopupWindow extends PopupWindow {

    private int listType;
    private int position;
    private int availableSize;//可继续添加的图片个数
    private int requestCode;
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private View view;
    private BaseActivity activity;
    private List<String> list;
    private Integer params;
    private String listname;
    private View parent;
    private File vFile;
    private static String path;
    private String orderId;

    private String IDCardType;

    public SelectPicPopupWindow(BaseActivity activity, View parent,
                                Integer params, ArrayList<String> list, String listname) {
        this.activity = activity;
        this.parent = parent;
        this.list = list;
        this.params = params;// 拍照的参数
        this.listname = listname;// list的名称
        initView();

    }


    /**
     * @param activity
     * @param parent
     * @param requestCode   请求码
     * @param availableSize 可添加图片的数量
     * @param listType      要添加的List的Type   已完成----1，未完成的已完成部分----2，未完成的未完成部分----3
     * @param position      要添加的子条目的postion
     */
    public SelectPicPopupWindow(BaseActivity activity, View parent,
                                int requestCode, int availableSize, int listType, int position, String orderId) {
        this.activity = activity;
        this.parent = parent;
        this.requestCode = requestCode;
        this.availableSize = availableSize;// 拍照的参数
        this.listType = listType;//
        this.position = position;//
        this.orderId = orderId;//
        initView();

    }

    /**
     * @param activity
     * @param parent
     * @param requestCode   请求码
     * @param availableSize 可添加图片的数量
     */
    public SelectPicPopupWindow(BaseActivity activity, View parent,
                                int requestCode, int availableSize) {
        this.activity = activity;
        this.parent = parent;
        this.requestCode = requestCode;
        this.availableSize = availableSize;
        initView();

    }

    public void setIDCardType(String IDCardType) {
        this.IDCardType = IDCardType;
    }

    public String getIDCardType() {
        return IDCardType;
    }

    private void initView() {
        // 窗体
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        this.setHeight(h / 3);
//        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);// 设置PopupWindow可获得焦点
        this.setTouchable(true);// 设置PopupWindow可触摸
        this.setOutsideTouchable(true);// 设置非PopupWindow区域可触摸
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimBottom);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.individual_change_item, null);
        btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) view.findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        this.setContentView(view);

        // 拍照
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePhoto();
                dismiss();
            }
        });

        // 从相册中选择
        btn_pick_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("",
//                        availableSize);
//                intent.putExtra("listname", listname);
//                intent.putExtra("activity", activity.getClass().getName());
//
//                intent.putExtra("listType", listType);
//                intent.putExtra("orderId", orderId);
//                intent.putExtra("position", position);
//                intent.putExtra("IDCardType", IDCardType);
//                intent.putExtra("availableSize", availableSize);
//                // intent.putExtra(listname,(Serializable)list);
//                activity.startActivity(intent);
                PictureSelectorUtils.getPictures(activity, availableSize, false, false);
                dismiss();
            }
        });

        // 取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        showPopupWindow(parent);

    }

    public void takePhoto() {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    /**
     * 拍照并保存
     */
//    public void takePhoto() {
//
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //拍照图片保存
//        File vFile = new File(Environment.getExternalStorageDirectory() + "/sgmjImage/",
//                System.currentTimeMillis() + ".jpg");
//        if (!vFile.exists()) {
//            File vDirPath = vFile.getParentFile();
//            vDirPath.mkdirs();
//        } else {
//            if (vFile.exists()) {
//                vFile.delete();
//            }
//        }
//
//        Uri cameraUri = Uri.fromFile(vFile);
//        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
//        //保存图片路径到文件中
////        if ("id_front".equals(IDCardType) || "id_back".equals(IDCardType)) {
////        } else {
////            activity.startActivityForResult(openCameraIntent, -1);
////        }
//        activity.startActivityForResult(openCameraIntent, requestCode);
//
//    }

    /**
     * 获取每个拍摄的照片
     *
     * @return
     */
    public String getPath() {
        return path;
    }

//    /**
//     * 可以添加图片的数量
//     *
//     * @return
//     */
//    private int getAvailableSize() {
//        int availSize = ParamConfig.MAX_IMAGE_COUNT - list.size();
//        if (availSize >= 0) {
//            return availSize;
//        }
//        return 0;
//    }

    @Override
    public void dismiss() {
        backgroundAlpha(activity, 1f);
        super.dismiss();
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            backgroundAlpha(activity, 0.7f);
            this.showAtLocation(parent,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            this.dismiss();

        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity activity, float bgAlpha) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        window.setAttributes(lp);
    }
}