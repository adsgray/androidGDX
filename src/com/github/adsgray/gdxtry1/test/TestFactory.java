package com.github.adsgray.gdxtry1.test;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.accel.LinearAccel;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;
import com.github.adsgray.gdxtry1.engine.position.PositionIF;
import com.github.adsgray.gdxtry1.engine.velocity.BlobVelocity;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;
import com.github.adsgray.gdxtry1.output.Renderer;
import com.github.adsgray.gdxtry1.output.Renderer.RenderConfigIF;

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
    
    public static Renderer renderConfig() {
        return (Renderer) Renderer.nullRenderConfig;
    }

}
