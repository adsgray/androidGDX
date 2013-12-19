package com.github.adsgray.gdxtry1.engine;

import java.util.Iterator;
import java.util.Vector;

import android.util.Log;

public class World implements WorldIF {

    private Vector<BlobIF> objs;

    public World() {
        Log.d("trace", "World created");
        objs = new Vector<BlobIF>();
    }
    
    @Override
    public Boolean addBlobToWorld(BlobIF b) {
        return objs.add(b);
    }
    
    @Override
    public Boolean removeBlobFromWorld(BlobIF b) {
        return objs.remove(b);
    }
    
    @Override
    public void tick() {
        Iterator<BlobIF> iter = objs.iterator();
        
        while (iter.hasNext()) {
            BlobIF b = iter.next();
            b.tick();
        }
    }
}
