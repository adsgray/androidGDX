package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig;

public class NullBlob extends BaseBlob {

    public NullBlob(PositionIF p, VelocityIF v, AccelIF a, RenderConfig r) {
        super(0, p, v, a, r);
        // This Blob will die immediately.
        // Mainly used to seed transformation chains/loops
        maxTicks = 0;
        // a renderConfig that does nothing
        renderConfig = RenderConfig.nullRenderConfig;
    }

    // NullBlob needs a renderconfig so that it can
    // be given to child blobs...
    public NullBlob(RenderConfig r) {
        // dangerous?
        this(null, null, null, r);
    }
    
    public NullBlob(BlobIF source) {
        this(source.getPosition(), source.getVelocity(), source.getAccel(), source.getRenderer());
        this.setWorld(source.getWorld());
    }
}
