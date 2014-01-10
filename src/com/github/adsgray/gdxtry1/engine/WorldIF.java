package com.github.adsgray.gdxtry1.engine;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.util.StateIF;

/*
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.adsgray.gdxtry1.output.RenderConfig;
*/

public interface WorldIF {
    // three types of blobs
    // blobs: just run around not hitting anything
    // targets: same as blobs but can be hit by missiles
    // missiles: same as blobs but can hit targets
    public Boolean addBlobToWorld(BlobIF b);
    public Boolean removeBlobFromWorld(BlobIF b); // also removes from targets and missiles if applicable

    public Boolean addTargetToWorld(BlobIF b);
    public Boolean removeTargetFromWorld(BlobIF b);

    public Boolean addMissileToWorld(BlobIF b);
    public Boolean removeMissileFromWorld(BlobIF b);
    
    public int getNumTargets();
    public int getNumMissiles();
    public int getNumBlobs();

    // returns identifier
    public int createTrackableBlobList();
    public Boolean addBlobToTrackableBlobList(int listid, BlobIF b);
    public int trackableBlobListCount(int listid);

    public void killAllBlobs();
    
    public void tick();
    public void render();
    
    // inside a WorldIF so it has access to objs
    // Not used right now.
    public interface CollisionDetectionStrategy {
        public void findCollisions();
        public void handleCollisions();
    }
    
    /*
    public void setRenderer(RenderConfig r);
    public RenderConfig getRenderer();
    */
    
    public StateIF getState();
}
