package id.ahmadnbl.skeletonproject.util;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

import id.ahmadnbl.skeletonproject.R;

/**
 * Created by billy on 9/13/16.
 * Animating made simple :)
 */
public class AnimationUtil {
    private static final String TAG = "AnimateHelper";

    public static void setZoomoutFadeAppearEffect(View target, int delayInMilis){
        setZoomoutFadeAppearEffect(target, delayInMilis, -1);
    }
    /**
     * Create zoom out with fade in animation for appearing effect
     * @param target target view for animating
     * @param delayInMilis start animation in delay
     */
    public static void setZoomoutFadeAppearEffect(View target, int delayInMilis, int duration){
        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(target.getContext(), R.animator.zoom_out_fade);
        anim.setStartDelay(delayInMilis);
        anim.setTarget(target);
        if(duration > 0) {
            anim.setDuration(duration);
        }
        anim.start();
    }

    /**
     * Create fade in animation for appearing effect
     *
     * @param target          target view for animating
     * @param durationInMilis animation duration
     * @param delayInMilis    start animation in delay
     * @param listener listener of animation behaviour
     */
    public static void setFadeAppearEffect(View target, int durationInMilis, int delayInMilis, Animator.AnimatorListener listener) {
        ObjectAnimator alphaAnimation6 = ObjectAnimator.ofFloat(target, "alpha", 0.0F, 1.0F);
        alphaAnimation6.setStartDelay(delayInMilis);
        alphaAnimation6.setDuration(durationInMilis);
        if(listener!=null) {
            alphaAnimation6.addListener(listener);
        }
        alphaAnimation6.start();
    }

    /**
     * Create fade out animation for disappearing effect
     *
     * @param target          target view for animating
     * @param durationInMilis animation duration
     * @param delayInMilis    start animation in delay
     * @param listener listener of animation behaviour
     */
    public static void setFadeDisappearEffect(View target, int durationInMilis, int delayInMilis, Animator.AnimatorListener listener) {
        ObjectAnimator alphaAnimation6 = ObjectAnimator.ofFloat(target, "alpha", 1.0F, 0.0F);
        alphaAnimation6.setStartDelay(delayInMilis);
        alphaAnimation6.setDuration(durationInMilis);
        if(listener!=null) {
            alphaAnimation6.addListener(listener);
        }
        alphaAnimation6.start();
    }

    /**
     * Create bounce animation for appearing effect
     * @param target target view for animating
     * @param delayInMilis start animation in delay
     */
    public static void setBounceAppearEffect(View target, int delayInMilis){
        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(target.getContext(), R.animator.fade_appear_bounce);
        anim.setStartDelay(delayInMilis);
        anim.setTarget(target);
        anim.start();
    }

    /**
     * Create shrinking animation for with bouncing effect
     *
     * @param target       target view for animating
     * @param delayInMilis start animation in delay
     */
    public static void setShrinkBounceEffect(View target, int delayInMilis) {
        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(target.getContext(), R.animator.bounce_shrink);
        anim.setStartDelay(delayInMilis);
        anim.setTarget(target);
        anim.start();
    }

    /**
     * Create expanding animation for with bouncing effect
     *
     * @param target       target view for animating
     * @param delayInMilis start animation in delay
     */
    public static void setExpandBounceEffect(View target, int delayInMilis) {
        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(target.getContext(), R.animator.bounce_expand);
        anim.setStartDelay(delayInMilis);
        anim.setTarget(target);
        anim.start();
    }

//    /**
//     * Create disappearing animation with fade out and slide down
//     * @param target target view for animating
//     * @param delayInMilis start animation in delay
//     */
//    public static void setFadeSlideDownEffect(View target, int delayInMilis){
//        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(target.getContext(), R.animator.fade_disappear_slide_down);
//        anim.setStartDelay(delayInMilis);
//        anim.setTarget(target);
//        anim.start();
//    }

    /**
     * Set wobbly-click animation when view is in click-touched or released
     *
     * @param target target view
     */
    public static void setButtonFancyEffect(View target) {
        target.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("TAG", "onTouch: event=" + motionEvent.getAction());
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setShrinkBounceEffect(view, 0);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        setExpandBounceEffect(view, 0);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * Expanding the view into matching the parent size
     *
     * @param v the target view
     */
    public static void expand(final View v, int targetHeight){
        int initialHeight = v.getMeasuredHeight();
        int diffHeight = targetHeight - initialHeight;
        expand(v, diffHeight, 500, new AccelerateInterpolator(2), null);
    }
    public static void expand(final View v, final int targetHeight, int duration, Interpolator interpolator, final Animation.AnimationListener listener) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = initialHeight + (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration(duration);
        if (interpolator != null) {
            a.setInterpolator(interpolator);
        }
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                if(listener!=null) listener.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
                if(listener!=null) listener.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if(listener!=null) onAnimationRepeat(animation);
            }
        });

        v.startAnimation(a);
    }

    /**
     * Collapsing the view into wrapping their contents
     *
     * @param v the target view
     */
    public static void collapse(final View v, Animation.AnimationListener listener) {
        final int initialHeight = v.getMeasuredHeight();
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        collapse(v, initialHeight, v.getMeasuredHeight(), 500, listener);
    }
    public static void collapse(final View v, final int initialHeight, final int targetHeight, int duration, Animation.AnimationListener listener) {
        final int diffHeight = initialHeight - targetHeight;
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = initialHeight - (int) (diffHeight * interpolatedTime);
                v.requestLayout();
            }
            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration(duration);
        a.setInterpolator(new DecelerateInterpolator(2));
        if(listener!=null) {
            a.setAnimationListener(listener);
        }
        v.startAnimation(a);
    }
}
