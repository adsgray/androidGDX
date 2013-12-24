package com.github.adsgray.gdxtry1.engine;

import java.util.Vector;

import com.github.adsgray.gdxtry1.output.*;
import com.github.adsgray.gdxtry1.output.RenderConfig.RectConfig;
import com.github.adsgray.gdxtry1.output.RenderConfig.RenderConfigIF;

import android.util.Log;

/** this blob has a mass, size, position, velocity and can be given
 * impulses.
 * @author andrew
 *
 */
public class RectangleBlob extends BaseBlob {
    
    /* every Blob has a renderer and a config object that tells the renderer how to draw this Blob */
    protected RectConfig rectConfig;
    
    private void createExtent() {
        // TODO: make FRE constructor that takes floats...
        FakeRectangleExtent re = new FakeRectangleExtent((int)rectConfig.w, (int)rectConfig.h);
        setExtent(re);
    }

    public RectangleBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, RenderConfig gdx) {
        super(massin, posin, velin, accel, gdx);
        // TODO have these render specific options passed in somehow
        rectConfig = renderer.randomRectConfig();
        renderConfig = rectConfig;
        createExtent();
    }
 
    public RectangleBlob(Integer massin, PositionIF posin, VelocityIF velin, AccelIF accel, RenderConfig gdx, RectConfig rc) {
        super(massin, posin, velin, accel, gdx);
        // TODO have these render specific options passed in somehow
        rectConfig = rc;
        renderConfig = rectConfig;
        createExtent();
    }

    /* split this blob up into numPieces new blobs.
     * conserving momentum etc.
     * Caller will have to
     * - remove this blob from the world
     * - add the new blobs to the world
     * 
     */
    @Override
    public Vector<BlobIF> explode(Integer numPieces) {
        Vector<BlobIF> vec = new Vector<BlobIF>();
        updateWorldAfterExplode(vec);
        sound.crash(EXPLODE_INTENSITY);
        return vec;
    }
     
    // Blobs have full knowledge of what they are and how they should be rendered.
    @Override
    public void render() {
        renderer.renderRect(this, rectConfig);
    }
}
