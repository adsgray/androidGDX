package com.github.adsgray.gdxtry1.engine;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface WorldIF {
    public Boolean addBlobToWorld(BlobIF b);
    public Boolean removeBlobFromWorld(BlobIF b);
    
    public Boolean addEphemeralBlobToWorld(BlobIF b);
    public void scheduleRemovalFromWorld(BlobIF b);

    public void tick();
    public void render();
    public void renderWithShapeRenderer(ShapeRenderer shapes);
}
