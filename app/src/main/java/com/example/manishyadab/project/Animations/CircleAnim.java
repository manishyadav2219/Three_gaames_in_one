package com.example.manishyadab.project.Animations;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleAnim extends Animation {

    private Circle circle;

    public CircleAnim(Circle circle) {
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = (360 * interpolatedTime);

        circle.setAngle(angle);
        circle.requestLayout();
    }

}
