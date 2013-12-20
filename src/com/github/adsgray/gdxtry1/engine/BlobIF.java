package com.github.adsgray.gdxtry1.engine;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.adsgray.gdxtry1.output.*;

public interface BlobIF {
    public PositionIF getPosition();
    public Integer getMass();
    public void setWorld(WorldIF w);
    public void setSound(SoundIF s);
    public void setExtent(ExtentIF e);

    public void tick();
    public void render();
    public void renderWithShapeRenderer(ShapeRenderer shapes);

    public boolean intersects(BlobIF with);
    public BlobIF collision(BlobIF with);
}
