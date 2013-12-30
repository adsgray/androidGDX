package com.github.adsgray.gdxtry1.input;

import com.github.adsgray.gdxtry1.input.SimpleDirectionGestureDetector.DirectionListener;

public class DirectionListenerDecorator extends DefaultDirectionListener {

    DirectionListener component;

    public DirectionListenerDecorator(DirectionListener component) {
        super();
        this.component = component;
    }

    // maybe should do super.blah() for all of these as well...
    @Override public void onLeft(FlingInfo f) { component.onLeft(f); }
    @Override public void onRight(FlingInfo f) { component.onRight(f); }
    @Override public void onUp(FlingInfo f) { component.onUp(f); }
    @Override public void onDown(FlingInfo f) { component.onDown(f); }
    @Override public void panStarted(float sx, float sy) { component.panStarted(sx, sy); }
    @Override public void panInProgress(float curx, float cury) { component.panInProgress(curx, cury); }
    @Override public void completePan(float sx, float sy, float ex, float ey) { component.completePan(sx, sy, ex, ey); }
}
