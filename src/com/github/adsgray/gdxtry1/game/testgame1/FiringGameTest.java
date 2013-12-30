package com.github.adsgray.gdxtry1.game.testgame1;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.ShowExtentDecorator;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.Game;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.input.DragAndFlingDirectionListener;
import com.github.adsgray.gdxtry1.input.Draggable;
import com.github.adsgray.gdxtry1.input.Flingable;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.RectConfig;
import com.github.adsgray.gdxtry1.output.Renderer.TriangleConfig;

public class FiringGameTest implements Game {

    DragAndFlingDirectionListener input;
    WorldIF world;
    Renderer renderer;
    static final int numEnemies = 10;

    public FiringGameTest(DragAndFlingDirectionListener dl, WorldIF w, Renderer r) {
        input = dl;
        world = w;
        renderer = r;
        setupGame();
    }
    
    private BlobIF createDefender() {
        PositionIF p = new BlobPosition(100,100);
        TriangleConfig rc = renderer.new TriangleConfig(Color.RED, 80);
        BlobIF b = BlobFactory.triangleBlob(p, PathFactory.stationary(), rc, renderer);
        b = new ShowExtentDecorator(b);
        b = new FiringBlobDecorator(b);
        world.addMissileToWorld(b);
        b.registerCollisionTrigger(new DefenderCollisionTrigger());
        b.setLifeTime(1000000);
        return b;
    }
    
    private BlobIF createOneEnemy() {
        PositionIF p = GameFactory.randomPosition(10,800,700,1000);
        RectConfig rc = renderer.new RectConfig(Color.BLUE, 60, 60);
        BlobIF b = BlobFactory.rectangleBlob(p, PathFactory.squarePath(10, 5), rc, renderer);
        b = BlobFactory.throbber(b);
        b.setLifeTime(100);
        b.registerTickDeathTrigger(TargetUtils.fireAtDefenderLoop());
        return b;
    }

    private void createEnemies() {
        int numToAdd = numEnemies - world.getNumTargets();
        if (numToAdd < 0) numToAdd = 0;
        
        while(numToAdd > 0) {
            BlobIF b = createOneEnemy();
            world.addTargetToWorld(b);
            numToAdd -= 1;
        }
    }

    private void setupGame() {
        BlobIF defender = createDefender();
        input.registerDraggable((Draggable) defender);
        input.registerFlingable((Flingable) defender);
        createEnemies();
    }
}
