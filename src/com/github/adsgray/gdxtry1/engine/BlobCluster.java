package com.github.adsgray.gdxtry1.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.adsgray.gdxtry1.output.RenderConfig;

// Like the "opposite" of a BlobSet.
// In a BlobSet the Blobs are absorbed and removed from the world.
// In a BlobCluster they remain in the world.
// BlobCluster is like a nullblob
public class BlobCluster extends BaseBlob implements ClusterIF {

    protected List<BlobIF> objs;
    
    public BlobCluster(PositionIF pos, BlobPath path, RenderConfig r) {
        super(0, pos, null, null, r);
        setPath(path);
        objs = new ArrayList<BlobIF>();
        renderConfig = new RenderConfig.BlobSetRenderConfig(objs);
    }

    @Override
    public Boolean leaveCluster(BlobIF b) {
        return objs.remove(b);
    }

    @Override public BlobIF absorbBlob(BlobIF b) {
        objs.add(b);
        return this;
    }

    @Override public BlobIF absorbBlob(BlobIF b, BlobTransform transform) {
        // ignore the transformation for now. Doesn't really make sense
        // for BlobCluster
        return absorbBlob(b);
    }

    @Override
    public BlobIF swap(BlobIF in, BlobIF out) {
        leaveCluster(out);
        absorbBlob(in);
        return in;
    }
}
