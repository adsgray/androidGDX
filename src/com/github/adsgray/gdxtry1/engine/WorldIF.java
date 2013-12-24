package com.github.adsgray.gdxtry1.engine;

/*
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.adsgray.gdxtry1.output.RenderConfig;
*/

public interface WorldIF {
    public Boolean addBlobToWorld(BlobIF b);
    public Boolean removeBlobFromWorld(BlobIF b);
    public void killAllBlobs();
    
    public Boolean addEphemeralBlobToWorld(BlobIF b);
    public void scheduleRemovalFromWorld(BlobIF b);
    public void scheduleAddToWorld(BlobIF b);
    public void scheduleEphemeralAddToWorld(BlobIF b);
    
    public void tick();
    public void render();
    public void handleCollisions();
    
    /*
    public void setRenderer(RenderConfig r);
    public RenderConfig getRenderer();
    */

}
