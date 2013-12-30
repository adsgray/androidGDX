package com.github.adsgray.gdxtry1.input;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

public interface Draggable extends BlobIF {
    public void panStarted(PositionIF start);
    public void panInProgress(PositionIF cur);
    public void completePan(PositionIF start, PositionIF stop);
    public ExtentIF getDragExtent();
}
