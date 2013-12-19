package com.github.adsgray.gdxtry1.engine;

public interface WorldIF {
    public Boolean addBlobToWorld(BlobIF b);
    public Boolean removeBlobFromWorld(BlobIF b);
    public void tick();
    public void render();
}
