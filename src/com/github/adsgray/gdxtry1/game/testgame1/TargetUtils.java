package com.github.adsgray.gdxtry1.game.testgame1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobSource;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.game.TriggerFactory;

public class TargetUtils {

    private static Random rnd = new Random();
    private static BlobSource targetMissileSource = new TargetMissileSource();

    // function to generate a sequence of tickDeathTriggers
    // which cause a 'target' to shoot 'targets' down at the defender
    public static BlobTrigger fireAtDefenderLoop() {
        BlobTransform fireAtDefender = new BlobTransform() {
            @Override public BlobIF transform(BlobIF b) {
                b.setLifeTime(rnd.nextInt(1000));
                BlobIF thing = targetMissileSource.get(b);
                return b;
            }
        };
        
        List<BlobTransform> transforms = new ArrayList<BlobTransform>();
        transforms.add(fireAtDefender);
        
        return TriggerFactory.createTransformSequence(transforms, true);
    }
}
