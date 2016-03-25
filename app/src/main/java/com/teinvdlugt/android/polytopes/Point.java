package com.teinvdlugt.android.polytopes;

import java.util.Locale;

public class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "(%d, %d)", x, y);
    }
}
