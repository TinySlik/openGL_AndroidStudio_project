package com.tiny.com.ops;

import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 点渲染器，绘制螺旋线
 */
public abstract class AbstractMyRenderer implements android.opengl.GLSurfaceView.Renderer{
    public float ratio;

        public float xrotate = 0f;//围绕x轴旋转角
        public float yrotate = 0f;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glClearColor(0,0,0,1);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0,0,width,height);
            ratio =(float) width/(float)height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio,ratio,-1f,1f,3f,7f);
    }

    @Override
    public abstract void onDrawFrame(GL10 gl) ;
}
