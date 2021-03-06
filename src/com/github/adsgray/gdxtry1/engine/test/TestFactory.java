package com.github.adsgray.gdxtry1.engine.test;

import com.github.adsgray.gdxtry1.engine.World;
import com.github.adsgray.gdxtry1.engine.WorldIF;
import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobSource;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTransform;
import com.github.adsgray.gdxtry1.engine.blob.BlobIF.BlobTrigger;
import com.github.adsgray.gdxtry1.engine.blob.decorator.BlobDecorator;
import com.github.adsgray.gdxtry1.engine.output.Renderer;
import com.github.adsgray.gdxtry1.engine.output.Renderer.RenderConfigIF;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class TestFactory {
 
    public static PositionIF position42() {
        return new BlobPosition(42,42);
    }
    
    public static VelocityIF velocity1dash1() {
        return new BlobVelocity(1,1);
    }
    
    public static AccelIF linearAccel1dash1() {
        return new LinearAccel(1,1);
    }
    
    public static Renderer renderer() {
        return Renderer.getTestInstance();
    }

    public static RenderConfigIF renderConfig() {
        return Renderer.getTestInstance().nullRenderConfig;
    }
    
    public static WorldIF world() {
        return new World();
    }
    
    // to use in testing to check how many times the methods are called
    public static class TestBlobSource extends BlobSource {
        public int num;
        public TestBlobSource() { num = 0; }

        @Override protected BlobIF generate(BlobIF parent) {
            num += 1;
            return parent;
        }
    }
    
    public static class TestBlobTransform extends BlobTransform {
        public int num;
        public TestBlobTransform() { num = 0; }
        @Override public BlobIF transform(BlobIF b) {
            num += 1;
            return b;
        }
    }

    public static class TestBlobTrigger extends BlobTrigger {
        int num;
        public TestBlobTrigger() { num = 0; }
        @Override public BlobIF trigger(BlobIF source, BlobIF secondary) {
            num += 1;
            return source;
        }
    }
    
    // doesn't actually decorate, just needed for BlobIF reference
    public static class TestDecorator extends BlobDecorator {
        public TestDecorator(BlobIF component) { super(component); }
    }
}
