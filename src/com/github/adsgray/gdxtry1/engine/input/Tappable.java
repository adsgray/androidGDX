package com.github.adsgray.gdxtry1.engine.input;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.extent.ExtentIF;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

public interface Tappable extends BlobIF {
    public void onTap(PositionIF pos, int count);
    public ExtentIF getTapExtent();
}
