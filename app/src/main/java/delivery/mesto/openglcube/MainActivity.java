package delivery.mesto.openglcube;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private OpenGLRenderer mOpenGLRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOpenGLRenderer = new OpenGLRenderer(MainActivity.this);

        if (!supportES2()) {
            Toast.makeText(this, "OpenGl ES 2.0 is not supported", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(mOpenGLRenderer);


        LinearLayout renderer = (LinearLayout) findViewById(R.id.renderer);
        renderer.addView(glSurfaceView);

        SeekBar height = (SeekBar)findViewById(R.id.height);
        height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float prog = (float) progress / 200;

                mOpenGLRenderer.updateVertexArray(prog);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mOpenGLRenderer.setStatus(1);
                mOpenGLRenderer.updateVertexes();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar right = (SeekBar)findViewById(R.id.right);
        right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float prog = (float) progress / 200;

                mOpenGLRenderer.updateVertexArray(prog);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mOpenGLRenderer.setStatus(2);
                mOpenGLRenderer.updateVertexes();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar left = (SeekBar)findViewById(R.id.left);
        left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float prog = (float) progress / 200;

                mOpenGLRenderer.updateVertexArray(prog);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mOpenGLRenderer.setStatus(3);
                mOpenGLRenderer.updateVertexes();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    private boolean supportES2() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return (configurationInfo.reqGlEsVersion >= 0x20000);
    }



}