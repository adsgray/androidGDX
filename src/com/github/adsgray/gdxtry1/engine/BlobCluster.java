package com.github.adsgray.gdxtry1.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.blob.BaseBlob;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;

// Like the "opposite" of a BlobSet.
// In a BlobSet the Blobs are absorbed and removed from the world.
// In a BlobCluster they remain in the world.
// BlobCluster is like a nullblob
public class BlobCluster extends BaseBlob implements ClusterIF {

    protected List<BlobIF> objs;
    
    public BlobCluster(PositionIF pos, BlobPath path, Renderer r) {
        super(0, pos, null, null, r);
        setPath(path);
        objs = new ArrayList<BlobIF>();
        updateRenderConfig();
    }

    @Override
    public Boolean leaveCluster(BlobIF b) {
        Boolean ret = objs.remove(b);
        updateRenderConfig();

        // once all Blobs have left the cluster let it die.
        if (objs.size() == 0) {
            clearTickDeathTriggers();
            setLifeTime(0);
        }

        return ret;
    }

    private void updateRenderConfig() {
        // have fun JVM GC...
        // Be terribly Scala like here and clobber the renderConfig with an
        // updated version that has the correct set of Blobs haha.
        // The way to avoid this is to add something ugly like Obj clientData
        // to RenderConfigIF...
        renderConfig = renderer.new BlobSetRenderConfig(objs);
    }

    @Override public BlobIF absorbBlob(BlobIF b) {
        objs.add(b);
        updateRenderConfig();
        return this;
    }

    @Override public BlobIF absorbBlob(BlobIF b, BlobTransform transform) {
        return absorbBlob(b);
    }

    @Override
    public BlobIF swap(BlobIF in, BlobIF out) {
        absorbBlob(in);
        leaveCluster(out);
        return in;
    }
}
