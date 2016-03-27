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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Point pt : points)
            sb.append(pt).append(", ");
        sb.delete(sb.length() - 2, sb.length() - 1);
        return sb.toString();
    }
}
