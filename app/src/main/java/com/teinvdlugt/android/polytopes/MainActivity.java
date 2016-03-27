package com.teinvdlugt.android.polytopes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private PolytopeView polytopeView;
    private SeekBar strokeWidthSeekBar, opacitySeekBar,
            perspectiveAngleSeekBar, perspectiveDistanceSeekBar;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageButton expandButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        polytopeView = (PolytopeView) findViewById(R.id.polytopeView);
        expandButton = (ImageButton) findViewById(R.id.expandImageButton);
        strokeWidthSeekBar = (SeekBar) findViewById(R.id.strokeWidthSeekBar);
        opacitySeekBar = (SeekBar) findViewById(R.id.opacitySeekBar);
        perspectiveAngleSeekBar = (SeekBar) findViewById(R.id.perspectiveAngleSeekBar);
        perspectiveDistanceSeekBar = (SeekBar) findViewById(R.id.perspectiveDistanceSeekBar);
        strokeWidthSeekBar.setProgress((int) polytopeView.getStrokeWidth() * 10);
        opacitySeekBar.setProgress(polytopeView.getPlaneOpacity());
        perspectiveAngleSeekBar.setProgress((int) (polytopeView.getAnglePerDimension() * 100));
        perspectiveDistanceSeekBar.setProgress((int) pxToDip(this, (float) polytopeView.getDistancePerHigherDimension()));
        strokeWidthSeekBar.setOnSeekBarChangeListener(this);
        opacitySeekBar.setOnSeekBarChangeListener(this);
        perspectiveAngleSeekBar.setOnSeekBarChangeListener(this);
        perspectiveDistanceSeekBar.setOnSeekBarChangeListener(this);

        VerticalScroller verticalScroller = (VerticalScroller) findViewById(R.id.verticalScroller);
        if (verticalScroller != null)
            verticalScroller.setOnProgressChangeListener(new VerticalScroller.OnProgressChangeListener() {
                @Override
                public void onProgressChanged(double progress) {
                    polytopeView.setRotation2D(progress);
                }
            });

        View bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        expandButton.setImageResource(R.drawable.ic_expand_more_black_24dp);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        expandButton.setImageResource(R.drawable.ic_expand_less_black_24dp);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.equals(strokeWidthSeekBar)) {
            polytopeView.setStrokeWidth((progress) / 10f);
        } else if (seekBar.equals(opacitySeekBar)) {
            polytopeView.setPlaneOpacity(progress);
        } else if (seekBar.equals(perspectiveAngleSeekBar)) {
            polytopeView.setAnglePerDimension(progress / 100d);
        } else if (seekBar.equals(perspectiveDistanceSeekBar)) {
            polytopeView.setDistancePerHigherDimension(dipToPx(this, progress));
        }
    }

    public void onClick2D(View view) {
        polytopeView.setDimensions(2);
    }

    public void onClick3D(View view) {
        polytopeView.setDimensions(3);
    }

    public void onClick4D(View view) {
        polytopeView.setDimensions(4);
    }

    public void onClick5D(View view) {
        polytopeView.setDimensions(5);
    }

    public void onClickExpand(View view) {
        switch (bottomSheetBehavior.getState()) {
            case BottomSheetBehavior.STATE_COLLAPSED:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case BottomSheetBehavior.STATE_EXPANDED:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    public static float dipToPx(Context context, float dip) {
        return dip * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDip(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}
