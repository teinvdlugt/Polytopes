package com.teinvdlugt.android.polytopes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private PolytopeView polytopeView;
    private SeekBar rotation2DSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        polytopeView = (PolytopeView) findViewById(R.id.polytopeView);
        rotation2DSB = (SeekBar) findViewById(R.id.seekBar);

        rotation2DSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                polytopeView.setRotation2D(progress / 100.);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
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
