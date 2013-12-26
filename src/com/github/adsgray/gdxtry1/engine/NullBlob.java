package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig;

public class NullBlob extends BaseBlob {

    public NullBlob(PositionIF p, VelocityIF v, AccelIF a, RenderConfig r) {
        super(0, p, v, a, r);
        // This Blob will die immediately.
        // Mainly used to seed transformation chains/loops
        maxTicks = 0;
        
    }
    public NullBlob(RenderConfig r) {
        // dangerous?
        this(null, null, null, r);
    }
}
