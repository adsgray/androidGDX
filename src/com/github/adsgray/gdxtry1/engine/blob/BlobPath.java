package com.github.adsgray.gdxtry1.engine.blob;

import com.github.adsgray.gdxtry1.engine.accel.AccelIF;
import com.github.adsgray.gdxtry1.engine.velocity.VelocityIF;

public class BlobPath {

        public VelocityIF vel;
        public AccelIF acc;
        
        public BlobPath(VelocityIF vel, AccelIF acc) {
            this.vel = vel;
            this.acc = acc;
        }
}
