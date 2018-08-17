package com.ywlee.test.coordinatorlayoutex;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * @create 2018.08.17.
 * @author YWLEE
 *
 * 스크롤에 Action 에 따라 나타나거나 사라지는 Custom View
 */
public class QuickReturnFooterBehavior extends CoordinatorLayout.Behavior<View> {
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull View child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target,
                                       int axes,
                                       int type) {
        // 가로 방향의 스크롤일 경우에만 footer 를 보여주거나 사라지게 할 예정이기 때문에
        // 가로 방향의 경우에만 return true
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        return type == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
}
