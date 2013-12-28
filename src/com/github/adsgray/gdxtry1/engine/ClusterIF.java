package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;

public interface ClusterIF extends BlobIF {
    public Boolean leaveCluster(BlobIF b);
    public BlobIF swap(BlobIF in, BlobIF out); // add "in" to cluster, remove "out" from cluster. return "in"
}
