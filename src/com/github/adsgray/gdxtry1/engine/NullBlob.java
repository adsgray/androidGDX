package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.output.RenderConfig;

public class NullBlob extends BaseBlob {

    public NullBlob(RenderConfig r) {
        // dangerous?
        super(0, null, null, null, r);
        // This Blob will die immediately.
        // Mainly used to seed transformation chains/loops
        maxTicks = 0;
    }
}
