package delivery.mesto.openglcube;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import static delivery.mesto.openglcube.OpenGLRenderer.STATUS_LEFT;
import static delivery.mesto.openglcube.OpenGLRenderer.STATUS_RIGHT;
import static delivery.mesto.openglcube.OpenGLRenderer.STATUS_UP;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private OpenGLRenderer mOpenGLRenderer;
    private boolean rendererSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addOpenGLCube();

    }

    private void addOpenGLCube(){
        if (!supportES2()) {
            Toast.makeText(this, "OpenGl ES 2.0 is not supported", Toast.LENGTH_LONG).show();
        }else {
            mOpenGLRenderer = new OpenGLRenderer(MainActivity.this);
            //if openGL is enabled
            rendererSet = true;
            //init GLSurfaceView
            glSurfaceView = new GLSurfaceView(this);
            glSurfaceView.setEGLContextClientVersion(2);

            glSurfaceView.setRenderer(mOpenGLRenderer);

            //container for the OpenGL canvas
            LinearLayout renderer = (LinearLayout) findViewById(R.id.renderer);
            renderer.addView(glSurfaceView);
            renderer.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_cube, null));

            //seekbar for up processing
            SeekBar height = (SeekBar)findViewById(R.id.height);
            height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    float progress_up;
                    progress_up = (float) progress / 200;
                    mOpenGLRenderer.setOffsetUp(progress_up);
                    mOpenGLRenderer.updateVertexArray();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mOpenGLRenderer.setStatus(STATUS_UP);
                    mOpenGLRenderer.updateVertexes();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            //seekbar for right processing
            SeekBar right = (SeekBar)findViewById(R.id.right);
            right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    float progress_right;
                    progress_right = (float) progress / 200;
                    mOpenGLRenderer.setOffsetRight(progress_right);
                    mOpenGLRenderer.updateVertexArray();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mOpenGLRenderer.setStatus(STATUS_RIGHT);
                    mOpenGLRenderer.updateVertexes();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            //seekbar for left processing
            SeekBar left = (SeekBar)findViewById(R.id.left);
            left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    float progress_left;
                    progress_left  = (float) progress / 200;
                    mOpenGLRenderer.setOffsetLeft(progress_left);
                    mOpenGLRenderer.updateVertexArray();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mOpenGLRenderer.setStatus(STATUS_LEFT);
                    mOpenGLRenderer.updateVertexes();
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }
    }

    //check enabling OpenGL on device
    private boolean supportES2() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return (configurationInfo.reqGlEsVersion >= 0x20000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }

}