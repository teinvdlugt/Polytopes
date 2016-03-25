package com.teinvdlugt.android.polytopes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private PolytopeView polytopeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        polytopeView = (PolytopeView) findViewById(R.id.polytopeView);
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
