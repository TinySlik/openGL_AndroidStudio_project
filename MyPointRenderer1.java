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
public class MyPointRenderer1 implements android.opengl.GLSurfaceView.Renderer{
    private float ratio;

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
        gl.glFrustumf(-1f,1f,-ratio,ratio,3f,7f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //  清除颜色缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //eyex,eyey,eyez:放置眼球坐标
        //centerx,centery,centerz:眼球的观察点
        //upx,upy,upz,制定画面向上方
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();//加载单位矩阵
        GLU.gluLookAt(gl,0,0,5,0,0,0,0,1,0);

        //rotato
        gl.glRotatef(yrotate,0f,1f,0f);
        gl.glRotatef(xrotate,1f,0f,0f);
        //计算点坐标
        float r = 0.5f ;
        List<Float> coordsList = new ArrayList<Float>();
        float x = 0f,y = 0f,z = 1.5f;
        float zstep = 0.005f;
        for( float alpha = 0f;alpha < Math.PI * 6 ; alpha = (float)(alpha + Math.PI / 32)){
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));

            z = z - zstep;
            coordsList.add(x);
            coordsList.add(y);
            coordsList.add(z);
        }
        //转换点称为缓冲区
        ByteBuffer ibb = ByteBuffer.allocateDirect(coordsList.size() * 4);
        ibb . order(ByteOrder.nativeOrder());
        FloatBuffer fbb =  ibb.asFloatBuffer();
        for(float f : coordsList)
        {
            fbb.put(f);
        }
        ibb.position(0);
        //制定定点指针
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,ibb);
        gl.glDrawArrays(GL10.GL_POINTS,0,coordsList.size() / 3);
    }
}
