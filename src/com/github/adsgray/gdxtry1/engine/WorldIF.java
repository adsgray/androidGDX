package com.github.adsgray.gdxtry1.engine;

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

    public void killAllBlobs();
    
    public void tick();
    public void render();
    
    /*
    public void setRenderer(RenderConfig r);
    public RenderConfig getRenderer();
    */

}
