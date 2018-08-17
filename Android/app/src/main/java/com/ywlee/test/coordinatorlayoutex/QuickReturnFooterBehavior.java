package com.ywlee.test.coordinatorlayoutex;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * @create 2018.08.17.
 * @author YWLEE
 *
 * 스크롤에 Action 에 따라 나타나거나 사라지는 Custom View
 */
public class QuickReturnFooterBehavior extends CoordinatorLayout.Behavior<View> {
    private int mDySinceDirectionChange;
    private boolean mHiding;
    private boolean mShowing;
    private TimeInterpolator INTERPOLATOR;

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

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull View child,
                                  @NonNull View target,
                                  int dx,
                                  int dy,
                                  @NonNull int[] consumed,
                                  int type) {
        // 스크롤 방향이 바뀌는 경우 모든 동작을 취소하고, Y 값을 다시 세팅
        if (dy > 0 && mDySinceDirectionChange < 0
                || dy < 0 && mDySinceDirectionChange > 0) {
            child.animate().cancel();
            mDySinceDirectionChange = 0;
        }

        mDySinceDirectionChange += dy;

        if (mDySinceDirectionChange > child.getHeight()
                && child.getVisibility() == View.VISIBLE
                && !mHiding) {
            hideView(child);
        } else if (mDySinceDirectionChange < 0
                && child.getVisibility() == View.GONE
                && !mShowing) {
            showView(child);
        }
    }

    /**
     * View 를 보임.
     * 아래에서 위로 슬라이딩 하는 애니메이션 후 보임
     *
     * @param view View
     */
    private void showView(View view) {
    }

    /**
     * View 를 숨김..
     * 아래로 슬라이딩 하는 액션 후 숨긴다.
     *
     * @param view 숨길 뷰
     */
    private void hideView(final View view) {
        mHiding = true;
        ViewPropertyAnimator animator = view.animate()
                .translationY(view.getHeight())
                .setInterpolator(INTERPOLATOR)
                .setDuration(200);

        animator.setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mHiding = false;
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                // 취소되면 다시 보여준다.
                mHiding = false;
                if (!mShowing)
                    showView(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.start();
    }
}
