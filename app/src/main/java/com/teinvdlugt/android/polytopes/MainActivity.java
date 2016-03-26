package com.teinvdlugt.android.polytopes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private PolytopeView polytopeView;
    private SeekBar strokeWidthSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        polytopeView = (PolytopeView) findViewById(R.id.polytopeView);
        strokeWidthSeekBar = (SeekBar) findViewById(R.id.strokeWidthSeekBar);
        strokeWidthSeekBar.setProgress((int) polytopeView.getStrokeWidth() * 10);
        strokeWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                polytopeView.setStrokeWidth((progress + 1) / 10f);
                // (stroke width shouldn't be zero)
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        VerticalScroller verticalScroller = (VerticalScroller) findViewById(R.id.verticalScroller);
        if (verticalScroller != null)
            verticalScroller.setOnProgressChangeListener(new VerticalScroller.OnProgressChangeListener() {
                @Override
                public void onProgressChanged(double progress) {
                    polytopeView.setRotation2D(progress);
                }
            });
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
}
