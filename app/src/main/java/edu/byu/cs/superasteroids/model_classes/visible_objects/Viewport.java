package edu.byu.cs.superasteroids.model_classes.visible_objects;

import android.graphics.Rect;

/**
 * Created by Marshall Garey
 * The currently visible rectangular portion of the world
 */
public class Viewport {

    public Viewport(int left, int top, int right, int bottom) {
        view = new Rect(left, top, right, bottom);
    }

    private Rect view;

    public void update() {

    }

    public Rect getView() {
        return view;
    }

    public void setView(Rect view) {
        this.view = view;
    }
}
