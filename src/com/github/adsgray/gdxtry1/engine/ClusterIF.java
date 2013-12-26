package com.github.adsgray.gdxtry1.engine;

public interface ClusterIF extends BlobIF {
    public Boolean leaveCluster(BlobIF b);
    public BlobIF swap(BlobIF in, BlobIF out); // add "in" to cluster, remove "out" from cluster. return "in"
}
