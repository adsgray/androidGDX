package com.github.adsgray.gdxtry1.engine.extent;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

public interface ExtentIF {
    public boolean intersects(PositionIF me, BlobIF them);
    public boolean contains(PositionIF me, PositionIF point);
    public void scale(float factor);
    public ExtentIF clone();
}
