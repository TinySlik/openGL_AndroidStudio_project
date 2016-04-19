package com.tiny.com.ops;

import android.opengl.GLU;

import com.tiny.com.ops.com.tiny.com.ops.util.BufferUtil;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 点渲染器，绘制螺旋线
 */
public class MyScissorRenderer extends AbstractMyRenderer{

    private  int width;
    private  int height;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0,0,0,1);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        gl.glViewport(0,0,width,height);
        ratio =(float) width/(float)height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio,ratio,-1f,1f,3f,7f);
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

        //启用剪裁
        gl.glEnable(GL10.GL_SCISSOR_TEST);

        //rotato
        gl.glRotatef(yrotate,0f,1f,0f);
        gl.glRotatef(xrotate,1f,0f,0f);
        //计算点坐标
        float [] coords = {
               -ratio , 1f,2f,
                -ratio , -1f,2f,

                ratio, 1f,2f,
                ratio, -1f,2f,
        };

        //颜色数组
        float[][] colors = {
                {1f,0f,0f,1f},
                {0f,1f,0f,1f},
                {0f,0f,1f,1f},
                {1f,1f,0f,1f},
                {0f,1f,1f,1f},
                {1f,0f,1f,1f},
        };
        for (int i = 0 ; i < 6 ; i++)
        {
            gl.glScissor(i*20, i *20 ,width - i*40 , height - i * 40);
            gl.glColor4f(colors[i][0],colors[i][1],colors[i][2],colors[i][3]);
            gl.glVertexPointer(3,GL10.GL_FLOAT, 0 , BufferUtil.arr2ByteBuffer(coords));
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);
        }
    }
}
