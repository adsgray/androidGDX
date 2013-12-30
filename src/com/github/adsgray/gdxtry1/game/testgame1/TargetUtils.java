package com.github.adsgray.gdxtry1.game.testgame1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobSource;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.blob.BlobPath;
import com.github.adsgray.gdxtry1.engine.blob.ExplosionBlob;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.game.AccelFactory;
import com.github.adsgray.gdxtry1.game.BlobFactory;
import com.github.adsgray.gdxtry1.game.PathFactory;
import com.github.adsgray.gdxtry1.game.TriggerFactory;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.CircleConfig;

public class TargetUtils {

    public static Random rnd = new Random();
    public static BlobSource targetMissileSource = new TargetMissileSource();
    public static BlobSource angryTargetMissileSource = new AngryTargetMissileSource();

    private static class missileTransform extends BlobTransform {

        private int maxLifeTime;
        private BlobSource missileSource;

        public missileTransform(int maxLifeTime, BlobSource missileSource) {
            this.maxLifeTime = maxLifeTime;
            this.missileSource = missileSource;
        }

        @Override public BlobIF transform(BlobIF b) {
            b.setLifeTime(rnd.nextInt(maxLifeTime));
            BlobIF thing = missileSource.get(b);
            return b;
        }
        
    }
    // function to generate a sequence of tickDeathTriggers
    // which cause a 'target' to shoot 'targets' down at the defender
    public static BlobTrigger fireAtDefenderLoop(int maxLifeTime, BlobSource missileSource) {
        BlobTransform fireAtDefender = new missileTransform(maxLifeTime, missileSource);
        List<BlobTransform> transforms = new ArrayList<BlobTransform>();
        transforms.add(fireAtDefender);
        
        return TriggerFactory.createTransformSequence(transforms, true);
    }
    
    public static BlobIF becomeAngryExplosion(BlobIF source) {
        //PositionIF p = new BlobPosition(source.getPosition());
        // have the explosion follow the parent around!
        PositionIF p = source.getPosition();
        Renderer r = source.getRenderer();
        BlobPath path = PathFactory.jigglePath(2);
        ExplosionBlob ex = new ExplosionBlob(0, p, path.vel, path.acc, r);
        
        BlobSource bs = new BlobSource() {
            @Override protected BlobIF generate(BlobIF parent) {
                Renderer r = parent.getRenderer();
                WorldIF w = parent.getWorld();
                // ugh, always forget to clone the position...
                PositionIF p = new BlobPosition(parent.getPosition());
                BlobPath path = new BlobPath(BlobFactory.randomVelocity(), AccelFactory.explosionAccel());
                CircleConfig rc = r.new CircleConfig(BlobFactory.randomColor(), 6);
                BlobIF b = BlobFactory.circleBlob(p, path, rc, r);
                b.setLifeTime(15);
                w.addBlobToWorld(b);
                return b;
            }
        };
        ex.setLifeTime(20);
        ex.setBlobSource(bs);
        return ex;
    }
}
