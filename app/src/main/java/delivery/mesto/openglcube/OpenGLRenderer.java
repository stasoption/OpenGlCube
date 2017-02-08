package delivery.mesto.openglcube;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

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

    private int STATUS = 0;
    private float OFFSET = 0.0f;

    private float X_1 =  0.0f;
    private float Y_1 = 0.7f;

    private float X_2 =  0.5f;
    private float Y_2 = 0.2f;

    private float X_3 =  0.5f;
    private float Y_3 = -0.3f;

    private float X_4 =  0.0f;
    private float Y_4 = 0.2f;

    private float X_5 =  -0.5f;
    private float Y_5 = 0.2f;

    private float X_6 =  0.0f;
    private float Y_6 = -0.3f;

    private float X_7 = 0.0f;
    private float Y_7 = -0.8f;

    private float X_8 = -0.5f;
    private float Y_8 = -0.3f;

    private float vertex_X_1, vertex_Y_1,
                vertex_X_2, vertex_Y_2,
                vertex_X_3,vertex_Y_3,
                vertex_X_4,vertex_Y_4,
                vertex_X_5,vertex_Y_5,
                vertex_X_6,vertex_Y_6,
                vertex_X_7,vertex_Y_7,
                vertex_X_8,vertex_Y_8;

    float[] vertices;

    public OpenGLRenderer(Context context) {
        this.context = context;

        updateVertexes();
        updateVertexArray(OFFSET);
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
        int intColor = Color.parseColor("#7846c2");
        glClearColor(Color.red(intColor) / 255.0f,
                Color.green(intColor) / 255.0f,
                Color.blue(intColor) / 255.0f,
                1f);

        int vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);
        int fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId);
        glUseProgram(programId);
    }

    @Override
    public void onSurfaceChanged(GL10 arg0, int width, int height) {
        glViewport(0, 0, width, height);
    }

//    private float offset = 0.0f;
    @Override
    public void onDrawFrame(GL10 arg0) {
        drawCube();
    }

    public void setStatus(int status){
        this.STATUS = status;
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

    public void updateVertexes(){
        vertex_X_1 =  X_1;
        vertex_Y_1 = Y_1;

        vertex_X_2 =  X_2;
        vertex_Y_2 = Y_2;

        vertex_X_3 =  X_3;
        vertex_Y_3 = Y_3;

        vertex_X_4 =  X_4;
        vertex_Y_4 = Y_4;

        vertex_X_5 =  X_5;
        vertex_Y_5 = Y_5;

        vertex_X_6 =  X_6;
        vertex_Y_6 = Y_6;

        vertex_X_7 = X_7;
        vertex_Y_7 = Y_7;

        vertex_X_8 = X_8;
        vertex_Y_8 = Y_8;
    }


    public void updateVertexArray(float mOffset) {

        if(STATUS == 1){ //Up
            X_1 = vertex_X_1;
            Y_1 = vertex_Y_1 + mOffset;

            X_2 =  vertex_X_2;
            Y_2 = vertex_Y_2 + mOffset;

            X_3 =  vertex_X_3;
            Y_3 = vertex_Y_3 - mOffset;

            X_4 =  vertex_X_4;
            Y_4 = vertex_Y_4 - mOffset;

            X_5 =  vertex_X_5;
            Y_5 = vertex_Y_5 + mOffset;

            X_6 = vertex_X_6;
            Y_6 = vertex_Y_6 + mOffset;

            X_7 = vertex_X_7;
            Y_7 = vertex_Y_7 - mOffset;

            X_8 = vertex_X_8;
            Y_8 = vertex_Y_8 - mOffset;

        }else if(STATUS == 2){ //right

            X_1 = vertex_X_1 - mOffset;
            Y_1 = vertex_Y_1 + mOffset;

            X_2 =  vertex_X_2 + mOffset;
            Y_2 = vertex_Y_2 - mOffset;

            X_3 =  vertex_X_3+ mOffset;
            Y_3 = vertex_Y_3- mOffset;

            X_4 =  vertex_X_4 - mOffset;
            Y_4 = vertex_Y_4 + mOffset;

            X_5 =  vertex_X_5 - mOffset;
            Y_5 = vertex_Y_5 + mOffset;

            X_6 = vertex_X_6 + mOffset;
            Y_6 = vertex_Y_6 - mOffset;

            X_7 = vertex_X_7 + mOffset;
            Y_7 = vertex_Y_7 - mOffset;

            X_8 = vertex_X_8 - mOffset;
            Y_8 = vertex_Y_8 + mOffset;

        }else if(STATUS == 3){ //left

            X_1 =  vertex_X_1 + mOffset;
            Y_1 = vertex_Y_1 + mOffset;

            X_2 =  vertex_X_2 + mOffset;
            Y_2 = vertex_Y_2 + mOffset;

            X_3 =  vertex_X_3 + mOffset;
            Y_3 = vertex_Y_3 + mOffset;

            X_4 =  vertex_X_4 + mOffset;
            Y_4 = vertex_Y_4 + mOffset;

            X_5 =  vertex_X_5 - mOffset;
            Y_5 = vertex_Y_5 - mOffset;

            X_6 =  vertex_X_6 - mOffset;
            Y_6 = vertex_Y_6 - mOffset;

            X_7 = vertex_X_7 - mOffset;
            Y_7 = vertex_Y_7 - mOffset;

            X_8 = vertex_X_8 - mOffset;
            Y_8 = vertex_Y_8 - mOffset;
        }

        vertices = new float[]{
//          dot1:    X          Y      dot2:   X           Y
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

        vertexData = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(vertices);
    }


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

