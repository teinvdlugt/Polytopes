package com.teinvdlugt.android.polytopes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plane {
    public List<Point> points;

    public Plane(Point... points) {
        this.points = new ArrayList<>();
        Collections.addAll(this.points, points);
    }
}
