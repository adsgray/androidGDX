package com.github.adsgray.gdxtry1.engine.util;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.MultiplyPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

public class PositionFactory {

    public static PositionIF origin() {
        return new BlobPosition(0,0);
    }
    
    public static PositionIF beside(BlobIF b, int x, int y) {
        PositionIF p = b.getPosition();
        return new BlobPosition(x + p.getX(), y + p.getY());
    }

    public static PositionIF multiplyPosition(PositionIF pos, PositionIF mult) {
        return new MultiplyPosition(pos, mult);
    }

    // make b's position a mirror of pos wrt X (so the Y axis)
    public static PositionIF mirrorX(PositionIF pos) {
        return multiplyPosition(pos, new BlobPosition(-1, 1));
    }

    public static PositionIF mirrorY(PositionIF pos) {
        return multiplyPosition(pos, new BlobPosition(1, -1));
    }

    public static PositionIF mirrorXY(PositionIF pos) {
        return multiplyPosition(pos, new BlobPosition(-1, -1));
    }
}
