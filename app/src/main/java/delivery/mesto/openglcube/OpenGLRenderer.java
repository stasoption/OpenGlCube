package delivery.mesto.openglcube;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.GL_LINE_LOOP;
import static android.opengl.GLES10.glLineWidth;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class OpenGLRenderer implements Renderer {
    private Context context;
    private int programId;
    private FloatBuffer vertexData;
    private int uColorLocation;
    private int aPositionLocation;

    public final static int STATUS_UP = 1;
    public final static int STATUS_RIGHT = 2;
    public final static int STATUS_LEFT = 3;

    private int STATUS = 0;
    private float OFFSET_UP = 0.0f;
    private float OFFSET_RIGHT = 0.0f;
    private float OFFSET_LEFT = 0.0f;

    //for storing coordinates
    private float X_1, X_2, X_3, X_4, X_5, X_6, X_7, X_8;
    private float Y_1, Y_2, Y_3, Y_4, Y_5, Y_6, Y_7, Y_8;

    //vertices when drawing
    private float vertex_X_1, vertex_X_2, vertex_X_3, vertex_X_4, vertex_X_5, vertex_X_6, vertex_X_7, vertex_X_8;
    private float vertex_Y_1, vertex_Y_2, vertex_Y_3, vertex_Y_4, vertex_Y_5, vertex_Y_6, vertex_Y_7, vertex_Y_8;


    public OpenGLRenderer(Context context) {
        this.context = context;
        initVertexes();
        updateVertexes();
        updateVertexArray();
    }

    // statuses for the cube when changing (up, right, left)
    public void setStatus(int status){
        this.STATUS = status;
    }

    // how many offset for the cube when it changing
    public void setOffsetUp(float offset){
        this.OFFSET_UP = offset;
    }
    public void setOffsetRight(float offset){
        this.OFFSET_RIGHT = offset;
    }
    public void setOffsetLeft(float offset){
        this.OFFSET_LEFT = offset;
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
        //background foe the canvas
        int intColor = Color.parseColor("#7846c2");
        glClearColor(Color.red(intColor) / 255.0f,
                Color.green(intColor) / 255.0f,
                Color.blue(intColor) / 255.0f,
                1f);
        //set parameters for shaders
        int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);
        int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        glUseProgram(programId);
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        drawCube();
    }



    private void drawCube(){
        bindData();
        glClear(GL_COLOR_BUFFER_BIT);
        glLineWidth(10);
        // BACK RIGHT
        glDrawArrays(GL_LINE_LOOP, 0, 4);
        // FRONT LEFT
        glDrawArrays(GL_LINE_LOOP, 4, 4);
        //FRONT RIGHT
        glDrawArrays(GL_LINE_LOOP, 8, 4);
        //BACK LEFT
        glDrawArrays(GL_LINE_LOOP, 12, 4);
    }

    //primary initialized the cube
    public void initVertexes(){
        X_1 =  0.0f;
        Y_1 = 0.7f;

        X_2 =  0.5f;
        Y_2 = 0.2f;

        X_3 =  0.5f;
        Y_3 = -0.3f;

        X_4 =  0.0f;
        Y_4 = 0.2f;

        X_5 =  -0.5f;
        Y_5 = 0.2f;

        X_6 =  0.0f;
        Y_6 = -0.3f;

        X_7 = 0.0f;
        Y_7 = -0.8f;

        X_8 = -0.5f;
        Y_8 = -0.3f;
    }

    //update the cube after transformation
    public void updateVertexes(){

        if(STATUS == STATUS_UP){
            vertex_X_1 =  X_1;
            vertex_Y_1 = Y_1 - OFFSET_UP;

            vertex_X_2 =  X_2;
            vertex_Y_2 = Y_2 - OFFSET_UP;

            vertex_X_3 =  X_3;
            vertex_Y_3 = Y_3 + OFFSET_UP;

            vertex_X_4 =  X_4;
            vertex_Y_4 = Y_4 + OFFSET_UP;

            vertex_X_5 =  X_5;
            vertex_Y_5 = Y_5 - OFFSET_UP;

            vertex_X_6 =  X_6;
            vertex_Y_6 = Y_6 - OFFSET_UP;

            vertex_X_7 = X_7;
            vertex_Y_7 = Y_7 + OFFSET_UP;

            vertex_X_8 = X_8;
            vertex_Y_8 = Y_8 + OFFSET_UP;
        }else if(STATUS == STATUS_RIGHT){

            vertex_X_1 =  X_1 + OFFSET_RIGHT;
            vertex_Y_1 = Y_1 - OFFSET_RIGHT;

            vertex_X_2 =  X_2 - OFFSET_RIGHT;
            vertex_Y_2 = Y_2 + OFFSET_RIGHT;

            vertex_X_3 =  X_3 - OFFSET_RIGHT;
            vertex_Y_3 = Y_3 + OFFSET_RIGHT;

            vertex_X_4 =  X_4 + OFFSET_RIGHT;
            vertex_Y_4 = Y_4 - OFFSET_RIGHT;

            vertex_X_5 =  X_5 + OFFSET_RIGHT;
            vertex_Y_5 = Y_5 - OFFSET_RIGHT;

            vertex_X_6 =  X_6 - OFFSET_RIGHT;
            vertex_Y_6 = Y_6 + OFFSET_RIGHT;

            vertex_X_7 = X_7 - OFFSET_RIGHT;
            vertex_Y_7 = Y_7 + OFFSET_RIGHT;

            vertex_X_8 = X_8 + OFFSET_RIGHT;
            vertex_Y_8 = Y_8 - OFFSET_RIGHT;
        }else if(STATUS == STATUS_LEFT){

            vertex_X_1 =  X_1 - OFFSET_LEFT;
            vertex_Y_1 = Y_1 - OFFSET_LEFT;

            vertex_X_2 =  X_2 - OFFSET_LEFT;
            vertex_Y_2 = Y_2 - OFFSET_LEFT;

            vertex_X_3 =  X_3 - OFFSET_LEFT;
            vertex_Y_3 = Y_3 - OFFSET_LEFT;

            vertex_X_4 =  X_4 - OFFSET_LEFT;
            vertex_Y_4 = Y_4 - OFFSET_LEFT;

            vertex_X_5 =  X_5 + OFFSET_LEFT;
            vertex_Y_5 = Y_5 + OFFSET_LEFT;

            vertex_X_6 =  X_6 + OFFSET_LEFT;
            vertex_Y_6 = Y_6 + OFFSET_LEFT;

            vertex_X_7 = X_7 + OFFSET_LEFT;
            vertex_Y_7 = Y_7 + OFFSET_LEFT;

            vertex_X_8 = X_8 + OFFSET_LEFT;
            vertex_Y_8 = Y_8 + OFFSET_LEFT;
        }
    }



    // transformation the cube when drawingtransformation
    public void updateVertexArray() {
        if(STATUS == STATUS_UP){ //Up
            X_1 = vertex_X_1;
            Y_1 = vertex_Y_1 + OFFSET_UP;

            X_2 =  vertex_X_2;
            Y_2 = vertex_Y_2 + OFFSET_UP;

            X_3 =  vertex_X_3;
            Y_3 = vertex_Y_3 - OFFSET_UP;

            X_4 =  vertex_X_4;
            Y_4 = vertex_Y_4 - OFFSET_UP;

            X_5 =  vertex_X_5;
            Y_5 = vertex_Y_5 + OFFSET_UP;

            X_6 = vertex_X_6;
            Y_6 = vertex_Y_6 + OFFSET_UP;

            X_7 = vertex_X_7;
            Y_7 = vertex_Y_7 - OFFSET_UP;

            X_8 = vertex_X_8;
            Y_8 = vertex_Y_8 - OFFSET_UP;

        }else if(STATUS == STATUS_RIGHT){ //right

            X_1 = vertex_X_1 - OFFSET_RIGHT;
            Y_1 = vertex_Y_1 + OFFSET_RIGHT;

            X_2 =  vertex_X_2 + OFFSET_RIGHT;
            Y_2 = vertex_Y_2 - OFFSET_RIGHT;

            X_3 =  vertex_X_3+ OFFSET_RIGHT;
            Y_3 = vertex_Y_3- OFFSET_RIGHT;

            X_4 =  vertex_X_4 - OFFSET_RIGHT;
            Y_4 = vertex_Y_4 + OFFSET_RIGHT;

            X_5 =  vertex_X_5 - OFFSET_RIGHT;
            Y_5 = vertex_Y_5 + OFFSET_RIGHT;

            X_6 = vertex_X_6 + OFFSET_RIGHT;
            Y_6 = vertex_Y_6 - OFFSET_RIGHT;

            X_7 = vertex_X_7 + OFFSET_RIGHT;
            Y_7 = vertex_Y_7 - OFFSET_RIGHT;

            X_8 = vertex_X_8 - OFFSET_RIGHT;
            Y_8 = vertex_Y_8 + OFFSET_RIGHT;

        }else if(STATUS == STATUS_LEFT){ //left

            X_1 =  vertex_X_1 + OFFSET_LEFT;
            Y_1 = vertex_Y_1 + OFFSET_LEFT;

            X_2 =  vertex_X_2 + OFFSET_LEFT;
            Y_2 = vertex_Y_2 + OFFSET_LEFT;

            X_3 =  vertex_X_3 + OFFSET_LEFT;
            Y_3 = vertex_Y_3 + OFFSET_LEFT;

            X_4 =  vertex_X_4 + OFFSET_LEFT;
            Y_4 = vertex_Y_4 + OFFSET_LEFT;

            X_5 =  vertex_X_5 - OFFSET_LEFT;
            Y_5 = vertex_Y_5 - OFFSET_LEFT;

            X_6 =  vertex_X_6 - OFFSET_LEFT;
            Y_6 = vertex_Y_6 - OFFSET_LEFT;

            X_7 = vertex_X_7 - OFFSET_LEFT;
            Y_7 = vertex_Y_7 - OFFSET_LEFT;

            X_8 = vertex_X_8 - OFFSET_LEFT;
            Y_8 = vertex_Y_8 - OFFSET_LEFT;
        }

        float[] mVertices = new float[]{
//         dot1: X   Y    dot2:  X    Y
                // BACK RIGHT
                X_1, Y_1,       X_2, Y_2,
                X_3, Y_3,       X_4, Y_4,
                // FRONT LEFT
                X_5, Y_5,       X_6, Y_6,
                X_7, Y_7,       X_8, Y_8,
                //FRONT RIGHT
                X_6, Y_6,       X_2, Y_2,
                X_3, Y_3,       X_7, Y_7,
                //BACK LEFT
                X_5, Y_5,       X_1, Y_1,
                X_4, Y_4,       X_8, Y_8
        };

        vertexData = ByteBuffer.allocateDirect(mVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(mVertices);
    }


    //set OpenGlData
    private void bindData() {
        uColorLocation = glGetUniformLocation(programId, "u_Color");
        int intColor = Color.parseColor("#ffffff");

        glUniform4f(uColorLocation,
                Color.red(intColor) / 255.0f,
                Color.green(intColor) / 255.0f,
                Color.blue(intColor) / 255.0f,
                1f);

        aPositionLocation = glGetAttribLocation(programId, "a_Position");
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }
}

