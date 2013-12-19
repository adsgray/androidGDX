package com.github.adsgray.gdxtry1.engine;

import java.util.Iterator;
import java.util.Vector;

import android.util.Log;

public class World {

    private Vector<Blob> objs;

    public World() {
        Log.d("trace", "World created");
        objs = new Vector<Blob>();
    }
    
    public Boolean addBlobToWorld(Blob b) {
        return objs.add(b);
    }
    
    public Boolean removeBlobFromWorld(Blob b) {
        return objs.remove(b);
    }
    
    public void tick() {
        Iterator<Blob> iter = objs.iterator();
        
        while (iter.hasNext()) {
            Blob b = iter.next();
            b.tick();
        }
    }
}
