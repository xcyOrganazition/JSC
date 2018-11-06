package cn.com.jinshangcheng.extra.explain.widget.pullrefresh;

import android.view.View;
import android.widget.AbsListView;


public abstract class PtrDefaultHandler {

    public static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return view.canScrollVertically(-1);
        }
    }

    public static boolean checkContentCanBePulledDown(MyPtrLayout frame, View content,
                                                      View header) {
        return !canChildScrollUp(content);
    }

    public static boolean checkCanDoRefresh(MyPtrLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }
}