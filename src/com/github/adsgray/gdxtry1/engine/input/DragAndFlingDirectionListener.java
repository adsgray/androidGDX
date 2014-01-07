package com.github.adsgray.gdxtry1.engine.input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.github.adsgray.gdxtry1.engine.blob.BlobIF;
import com.github.adsgray.gdxtry1.engine.input.SimpleDirectionGestureDetector.DirectionListener;
import com.github.adsgray.gdxtry1.engine.position.BlobPosition;

public class DragAndFlingDirectionListener implements DirectionListener {

    protected class BlobManager {
        public List<BlobIF> objs;
        public List<BlobIF> toRemove;
        public List<BlobIF> toAdd;
        
        public BlobManager() {
            objs = new ArrayList<BlobIF>();
            toRemove = new ArrayList<BlobIF>();
            toAdd = new ArrayList<BlobIF>();
        }
        
        public void handleAdditionsAndRemovals() {
            Iterator<BlobIF> iter = toRemove.iterator();
            while (iter.hasNext()) {
                objs.remove(iter.next());
            }
            toRemove.clear();
            
            iter = toAdd.iterator();
            while (iter.hasNext()) {
                objs.add(iter.next());
            }
            toAdd.clear();
        }
    }
    
    protected BlobManager draggable;
    protected BlobManager beingDragged;
    protected BlobManager flingable;
    protected BlobManager tappable;
    protected List<KeyListener> keyListeners;

    public DragAndFlingDirectionListener() {
        draggable = new BlobManager();
        beingDragged = new BlobManager();
        flingable = new BlobManager();
        tappable = new BlobManager();
        keyListeners = new ArrayList<KeyListener>();
    }
    
    public Boolean registerKeyListener(KeyListener k) {
        return keyListeners.add(k);
    }
    public Boolean deregisterKeyListener(KeyListener k) {
        return keyListeners.remove(k);
    }
    
    public Boolean registerDraggable(Draggable b) {
        return draggable.toAdd.add(b);
    }
    public Boolean deregisterDraggable(Draggable b) {
        return draggable.toRemove.add(b);
    }
 
    public Boolean registerFlingable(Flingable b) {
        return flingable.toAdd.add(b);
    }
    public Boolean deregisterFlingable(Flingable b) {
        return flingable.toRemove.add(b);
    }

    public Boolean registerTappable(Tappable b) {
        return tappable.toAdd.add(b);
    }
    public Boolean deregisterTappable(Tappable b) {
        return tappable.toRemove.add(b);
    }

    protected void handleAllAdditionsAndRemovals() {
        draggable.handleAdditionsAndRemovals();
        beingDragged.handleAdditionsAndRemovals();
        flingable.handleAdditionsAndRemovals();
        tappable.handleAdditionsAndRemovals();
    }

    @Override
    public void onLeft(FlingInfo f) {
        if (flingable.objs.isEmpty()) return;
        Iterator<BlobIF> iter = flingable.objs.iterator();
        while (iter.hasNext())  {
            Flingable fling = (Flingable)iter.next();
            if (fling.getFlingExtent().contains(fling.getPosition(), new BlobPosition(f.startX, f.startY)))
                fling.onFlingLeft(f);
        }
        handleAllAdditionsAndRemovals();
    }

    @Override
    public void onRight(FlingInfo f) {
        if (flingable.objs.isEmpty()) return;
        Iterator<BlobIF> iter = flingable.objs.iterator();
        while (iter.hasNext())  {
            Flingable fling = (Flingable)iter.next();
            if (fling.getFlingExtent().contains(fling.getPosition(), new BlobPosition(f.startX, f.startY)))
                fling.onFlingRight(f);
        }
        handleAllAdditionsAndRemovals();
    }

    @Override
    public void onUp(FlingInfo f) {
        if (flingable.objs.isEmpty()) return;
        Iterator<BlobIF> iter = flingable.objs.iterator();
        while (iter.hasNext())  {
            Flingable fling = (Flingable)iter.next();
            if (fling.getFlingExtent().contains(fling.getPosition(), new BlobPosition(f.startX, f.startY)))
                fling.onFlingUp(f);
        }
        handleAllAdditionsAndRemovals();
    }

    @Override
    public void onDown(FlingInfo f) {
        if (flingable.objs.isEmpty()) return;
        Iterator<BlobIF> iter = flingable.objs.iterator();
        while (iter.hasNext())  {
            Flingable fling = (Flingable)iter.next();
            if (fling.getFlingExtent().contains(fling.getPosition(), new BlobPosition(f.startX, f.startY)))
                fling.onFlingDown(f);
        }
        handleAllAdditionsAndRemovals();
    }

    @Override
    public void panStarted(float sx, float sy) {
        int x = (int)sx;
        int y = (int)sy;
        
        BlobPosition p = new BlobPosition(x,y);
        
        Iterator<BlobIF> iter = draggable.objs.iterator();

        while (iter.hasNext()) {
            Draggable b = (Draggable)iter.next();
            if (b.getDragExtent().contains(b.getPosition(), p)) {
                beingDragged.toAdd.add(b);
            }
        }
        handleAllAdditionsAndRemovals();
    }

    @Override
    public void panInProgress(float curx, float cury) {
        // TODO optionally skip some of these events...
        Iterator<BlobIF> iter = beingDragged.objs.iterator();
        BlobPosition p = new BlobPosition((int)curx,(int)cury);
        
        // note: this will not work for things that are in clusters...
        while (iter.hasNext()) {
            Draggable b = (Draggable)iter.next();
            b.panInProgress(p);
        }
        handleAllAdditionsAndRemovals();
    }

    @Override
    public void completePan(float sx, float sy, float ex, float ey) {
        Iterator<BlobIF> iter = beingDragged.objs.iterator();
        // start and end positions
        BlobPosition sp = new BlobPosition((int)sx,(int)sy);
        BlobPosition ep = new BlobPosition((int)ex,(int)ey);
        
        // note: this will not work for things that are in clusters...
        while (iter.hasNext()) {
            Draggable b = (Draggable)iter.next();
            b.completePan(sp, ep);
        }

        beingDragged.objs.clear();
        handleAllAdditionsAndRemovals();
    }

    @Override
    public void onTap(float x, float y, int count) {
        Iterator<BlobIF> iter = tappable.objs.iterator();
        
        // tap position
        BlobPosition tp = new BlobPosition((int)x, (int)y);
        
        // if any tappable blobs contain this position:
        while (iter.hasNext()) {
            Tappable b = (Tappable)iter.next();
            if (b.getTapExtent().contains(b.getPosition(), tp)) {
                b.onTap(tp, count);
            }
        }
        handleAllAdditionsAndRemovals();
    }

    @Override
    public void keyDown(int key) {
        Iterator<KeyListener> iter = keyListeners.iterator();
        
        while (iter.hasNext()) {
            KeyListener k = iter.next();
            k.keyDown(key);
        }
    }
}
