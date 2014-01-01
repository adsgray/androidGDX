package com.github.adsgray.gdxtry1.engine.input;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.input.SimpleDirectionGestureDetector.DirectionListener.FlingInfo;

public interface Flingable extends BlobIF {
    public void onFlingUp(FlingInfo f);
    public void onFlingLeft(FlingInfo f);
    public void onFlingRight(FlingInfo f);
    public void onFlingDown(FlingInfo f);
    public ExtentIF getFlingExtent();
}
