package com.teinvdlugt.android.polytopes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    private Paint paint;
    List<Point> points = new ArrayList<>();
    List<Line> lines = new ArrayList<>();

    private void render() {
        points.clear();
        lines.clear();
        Point first = new Point(200, 1150);
        points.add(first);
        for (int n = 1; n < dimensions + 1; n++) {
            dimensionUp(n);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Line line : lines) {
            canvas.drawLine(line.p1.x, line.p1.y, line.p2.x, line.p2.y, paint);
        }
    }

    private void dimensionUp(int dimension) {
        List<Point> newPoints = new ArrayList<>();
        List<Line> newLines = new ArrayList<>();
        for (Point point : points) {
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
            int x = (int) (point.x + distance * Math.cos(angle));
            int y = (int) (point.y - distance * Math.sin(angle));
            Point newPoint = new Point(x, y);
            newPoints.add(newPoint);
            newLines.add(new Line(point, newPoint));
        }
        for (Line line : lines) {
            Point newP1 = newPoints.get(points.indexOf(line.p1));
            Point newP2 = newPoints.get(points.indexOf(line.p2));
            newLines.add(new Line(newP1, newP2));
        }
        points.addAll(newPoints);
        lines.addAll(newLines);
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

    public double getRotation2D() {
        return rotation2D;
    }

    public void setRotation2D(double rotation2D) {
        this.rotation2D = rotation2D;
        render();
        invalidate();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
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
