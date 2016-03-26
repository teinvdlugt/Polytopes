package com.teinvdlugt.android.polytopes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PolytopeView extends View {

    private int dimensions = 4;
    private double anglePerDimension = .5;
    private double distanceInLowDimensions = 200;
    private double distancePerHigherDimension = 100;
    private double rotation2D = 0;
    private int minX, maxX, minY, maxY;
    private Paint linePaint, planePaint;
    List<Point> points = new ArrayList<>();
    List<Line> lines = new ArrayList<>();
    List<Plane> planes = new ArrayList<>();

    private void render() {
        points.clear();
        lines.clear();
        planes.clear();

        Point first = new Point(0, 0);
        points.add(first);
        for (int n = 1; n < dimensions + 1; n++) {
            dimensionUp(n);
        }

        minX = maxX = points.get(0).x;
        minY = maxY = points.get(0).y;
        for (int i = 1; i < points.size(); i++) {
            Point pt = points.get(i);
            if (pt.x < minX) {
                minX = pt.x;
            }
            if (pt.x > maxX) {
                maxX = pt.x;
            }
            if (pt.y < minY) {
                minY = pt.y;
            }
            if (pt.y > maxY) {
                maxY = pt.y;
            }
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int xOffset = getWidth() / 2 - (maxX + minX) / 2;
        int yOffset = getHeight() / 2 - (maxY + minY) / 2;

        for (Line line : lines) {
            canvas.drawLine(line.p1.x + xOffset, line.p1.y + yOffset, line.p2.x + xOffset, line.p2.y + yOffset, linePaint);
        }
        for (Plane plane : planes) {
            Path path = new Path();
            path.moveTo(plane.points.get(0).x + xOffset, plane.points.get(0).y + yOffset);
            for (int i = 1; i < plane.points.size(); i++) {
                Point pt = plane.points.get(i);
                path.lineTo(pt.x + xOffset, pt.y + yOffset);
            }
            path.close();
            canvas.drawPath(path, planePaint);
        }
    }

    private void dimensionUp(int dimension) {
        double angle;
        switch (dimension) {
            case 1:
                angle = rotation2D;
                break;
            case 2:
                angle = rotation2D + Math.PI * .5;
                break;
            default:
                angle = (dimension - 2) * anglePerDimension;
        }

        double distance;
        switch (dimension) {
            case 1:
            case 2:
            case 3:
                distance = distanceInLowDimensions;
                break;
            default:
                distance = (dimension - 2) * distancePerHigherDimension;
        }

        List<Point> newPoints = new ArrayList<>();
        List<Line> newLines = new ArrayList<>();
        List<Plane> newPlanes = new ArrayList<>();
        if (dimension == 1) {
            Point point = this.points.get(0);
            int x = (int) (point.x + distance * Math.cos(angle));
            int y = (int) (point.y - distance * Math.sin(angle));
            Point newPoint = new Point(x, y);
            newPoints.add(newPoint);
            newLines.add(new Line(point, newPoint));
        } else if (dimension == 2) {
            Line line = lines.get(0);
            int xDiff = (int) (distance * Math.cos(angle));
            int yDiff = (int) (distance * Math.sin(angle));
            Point newP1 = new Point(line.p1.x + xDiff, line.p1.y - yDiff);
            Point newP2 = new Point(line.p2.x + xDiff, line.p2.y - yDiff);
            newPoints.add(newP1);
            newPoints.add(newP2);
            newLines.add(new Line(line.p1, newP1));
            newLines.add(new Line(line.p2, newP2));
            newLines.add(new Line(newP1, newP2));
            newPlanes.add(new Plane(line.p1, newP1, newP2, line.p2));
        } else if (dimension >= 3) {
            // First, copy all planes
            for (Plane plane : planes) {
                Plane newPlane = new Plane();
                for (Point pt : plane.points)
                    newPlane.points.add(new Point(
                            (int) (pt.x + distance * Math.cos(angle)),
                            (int) (pt.y - distance * Math.sin(angle))));
                newPlanes.add(newPlane);
            }
            // Then, every 1D line turns into a 2D plane
            for (Line line : lines) {
                int xDiff = (int) (distance * Math.cos(angle));
                int yDiff = (int) (distance * Math.sin(angle));
                Point newP1 = new Point(line.p1.x + xDiff, line.p1.y - yDiff);
                Point newP2 = new Point(line.p2.x + xDiff, line.p2.y - yDiff);
                newPoints.add(newP1);
                newPoints.add(newP2);
                newLines.add(new Line(line.p1, newP1));
                newLines.add(new Line(line.p2, newP2));
                newLines.add(new Line(newP1, newP2));
                newPlanes.add(new Plane(line.p1, newP1, newP2, line.p2));
            }
        }
        points.addAll(newPoints);
        lines.addAll(newLines);
        planes.addAll(newPlanes);
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        boolean render = this.dimensions != dimensions;
        this.dimensions = dimensions;
        if (render) {
            render();
            invalidate();
        }
    }

    public float getStrokeWidth() {
        return linePaint.getStrokeWidth();
    }

    public void setStrokeWidth(float strokeWidth) {
        linePaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public double getRotation2D() {
        return rotation2D;
    }

    public void setRotation2D(double rotation2D) {
        this.rotation2D = rotation2D;
        render();
        invalidate();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setStrokeWidth(3);
        linePaint.setAntiAlias(true);
        planePaint = new Paint();
        planePaint.setColor(Color.argb(0x33, 0, 0, 0));
        render();
    }

    public PolytopeView(Context context) {
        super(context);
        init();
    }

    public PolytopeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PolytopeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
