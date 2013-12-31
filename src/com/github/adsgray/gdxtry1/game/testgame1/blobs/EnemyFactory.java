package com.github.adsgray.gdxtry1.game.testgame1.blobs;

import com.badlogic.gdx.graphics.Color;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobSource;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.blob.NullBlob;
import com.github.adsgray.gdxtry1.engine.blob.TextBlobIF;
import com.github.adsgray.gdxtry1.engine.blob.decorator.ShowExtentDecorator;
import com.github.adsgray.gdxtry1.engine.extent.CircleExtent;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionComposeDecorator;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.GameFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.game.PositionFactory;
import com.github.adsgray.gdxtry1.game.testgame1.BossTargetMissileSource;
import com.github.adsgray.gdxtry1.game.testgame1.TargetUtils;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.RectConfig;
import com.github.adsgray.gdxtry1.output.Renderer.TextConfig;
import com.github.adsgray.gdxtry1.output.Renderer.TriangleConfig;

public class EnemyFactory {
    
    // TODO: enemy BlobSources that the code
    // will choose from randomly.
    // At least 3 levels of "difficulty"
    // need an enemy that actually aims at you
    // and fires more/faster
    private static BlobPath randomPath() {
        if (TargetUtils.rnd.nextInt(100) < 50) {
            return PathFactory.squarePath(15,5);
        } else {
            return PathFactory.squarePathClockwise(15,5);
        }
    }
    
    public static BlobIF defaultEnemy(WorldIF world, Renderer renderer) {
        PositionIF p = GameFactory.randomPosition(20,GameFactory.BOUNDS_X - 20,GameFactory.BOUNDS_Y - 500,GameFactory.BOUNDS_Y - 100);
        RectConfig rc = renderer.new RectConfig(GameFactory.randomColor(), 60, 60);
        BlobIF b = BlobFactory.rectangleBlob(p, randomPath(), rc, renderer);
        b.setLifeTime(TargetUtils.rnd.nextInt(200));

        b.registerTickDeathTrigger(TargetUtils.fireAtDefenderLoop(1000, TargetUtils.targetMissileSource, 1));

        b = BlobFactory.throbber(b);
        // N.B. this has to be the last decorator so that we can cast to Enemy
        b = new DefaultEnemy(b);
        world.addTargetToWorld(b);
        return b;
    }
    
    // 800x1422
    public static BlobIF bossEnemy(WorldIF world, Renderer renderer, PositionIF aimTarget) {
        PositionIF p = GameFactory.randomPosition(200,250,GameFactory.BOUNDS_Y - 450,GameFactory.BOUNDS_Y - 400);
        RectConfig rc = renderer.new RectConfig(GameFactory.randomColor(), 500, 300);
        BlobIF b = BlobFactory.rectangleBlob(p, randomPath(), rc, renderer);

        b.setLifeTime(TargetUtils.rnd.nextInt(200));
        b.registerTickDeathTrigger(TargetUtils.fireAtDefenderLoop(100, new BossTargetMissileSource(aimTarget), 3));

        b = BlobFactory.throbber(b);
        b = BlobFactory.flashColorCycler(b, 5);
        b.setPath(PathFactory.backAndForth(5, 5));
        // N.B. this has to be the last decorator so that we can cast to Enemy
        b = new BossEnemy(b, aimTarget);
        world.addTargetToWorld(b);
        return b;       
    }
    
    // clusters cause a memory leak because the cluster
    // blobs are never killed and never go away even after
    // all members leave the cluster...
    static BlobPath randomPathInCluster() {
        return PathFactory.jigglePath(20);
    }

    private static BlobSource sourceForClusterEnemies = new BlobSource() {
        @Override protected BlobIF generate(BlobIF parent) {
            WorldIF w = parent.getWorld();
            Renderer renderer = parent.getRenderer();
            // a little smaller
            RectConfig rc = renderer.new RectConfig(GameFactory.randomColor(), 55, 55);
            BlobIF b = BlobFactory.rectangleBlob(PositionFactory.origin(), randomPathInCluster(), rc, renderer);
            b.setLifeTime(TargetUtils.rnd.nextInt(200));
            b.registerTickDeathTrigger(TargetUtils.fireAtDefenderLoop(1000, TargetUtils.targetMissileSource, 1));
            b = BlobFactory.throbber(b);
            // N.B. this has to be the last decorator so that we can cast to Enemy
            b = new DefaultEnemy(b);
            w.addTargetToWorld(b);
            return b;
        }
    };
    
    private static int randomClusterDistance() {
        return TargetUtils.rnd.nextInt(50) + 50;
    }
    public static BlobIF createThreeCluster(WorldIF world, Renderer renderer) {
        PositionIF p = GameFactory.randomPosition(20,GameFactory.BOUNDS_X - 20,GameFactory.BOUNDS_Y - 500,GameFactory.BOUNDS_Y - 100);
        return BlobFactory.createThreeCluster(p, randomPath(), sourceForClusterEnemies, randomClusterDistance(), world, renderer);
    }

    public static BlobSource hitPointBonusSource = new BlobSource() {
        @Override protected BlobIF generate(BlobIF parent) {
            WorldIF w = parent.getWorld();
            Renderer r = parent.getRenderer();

            BlobIF b = BlobFactory.createPrizeBlob(w,r);
            b.setLifeTime(250);
            b = BlobFactory.rainbowColorCycler(b, 3);
            b = BlobFactory.throbber(b);
            b.setExtent(new CircleExtent(45)); // important! collision detection...
            b.setPosition(new BlobPosition(parent.getPosition()));
            b.setPath(PathFactory.straightUpDown(-9)); // down
            
            TextConfig textrc = r.new TextConfig(Color.WHITE, 1.5f);
            TextBlobIF t = (TextBlobIF) BlobFactory.textBlob(
                    new PositionComposeDecorator(b.getPosition(), new BlobPosition(-25,50)),
                    PathFactory.stationary(), textrc, r);
            t.setWorld(w);
            t.setLifeTime(125);
            t.setText("BONUS");
            w.addBlobToWorld(BlobFactory.rainbowColorCycler(t, 3));

            b = new HitpointBonusDecorator(b, t, -5); // negative hitpoints means bonus hitpoints
            w.addTargetToWorld(b);

            return b;
        }
    };
    
}
