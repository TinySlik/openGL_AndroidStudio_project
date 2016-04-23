package com.tiny.com.ops;

import android.opengl.GLU;
import android.util.Log;

import com.tiny.com.ops.com.tiny.com.ops.util.BufferUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 三角形带渲染三角形
 */
public class MyTriangleConRenderer extends AbstractMyRenderer{

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0,0,0,1);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //  清除颜色缓存 和深度缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        //gl.glColor4f(0f,1f,0f,1f);

        //启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //启用表面剔除
        gl.glEnable(GL10.GL_CULL_FACE);

        gl.glFrontFace(GL10.GL_CCW);
        //剔除背面
        gl.glCullFace(GL10.GL_BACK);

        //单调模式
        gl.glShadeModel(GL10.GL_FLAT);

        gl.glLineWidth(5.0f);
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
        //锥顶
        List<Float> coordsList = new ArrayList<Float>();
        coordsList.add(0f);
        coordsList.add(0f);
        coordsList.add(0.5f);

        //锥底
        List<Float> coordsConeBottomList = new ArrayList<Float>();
        coordsConeBottomList.add(0f);
        coordsConeBottomList.add(0f);
        coordsConeBottomList.add(-1f);
        //顶点着色模式
        //
        //

        List<Float> colorList = new ArrayList<>();
        colorList.add(1f);
        colorList.add(0f);
        colorList.add(0f);
        colorList.add(1f);
        float x = 0f,y = 0f,z = -1.0f;
        int i = 0;

        boolean flag = false;
        //底面

        for( float alpha = 0f;alpha < Math.PI * 2.2; alpha = (float)(alpha + Math.PI / 8)){
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            //锥顶
            coordsList.add(x);
            coordsList.add(y);
            coordsList.add(z);

            //锥底
            coordsConeBottomList.add(x);
            coordsConeBottomList.add(y);
            coordsConeBottomList.add(z);
            //点颜色值

            if (flag = !flag)
            {
                colorList.add(1f);
                colorList.add(1f);
                colorList.add(0f);
                colorList.add(1f);
            }else{
                colorList.add(1f);
                colorList.add(0f);
                colorList.add(0f);
                colorList.add(1f);
            }
        }

        if (flag = !flag)
        {
            colorList.add(1f);
            colorList.add(1f);
            colorList.add(0f);
            colorList.add(1f);
        }else{
            colorList.add(1f);
            colorList.add(0f);
            colorList.add(0f);
            colorList.add(1f);
        }

        //颜色缓冲区
        FloatBuffer colorBuffer = BufferUtil.arr2FloatBuffer (colorList);
        gl.glColorPointer(4,GL10.GL_FLOAT,0,colorBuffer);

        //绘制锥面
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,BufferUtil.arr2ByteBuffer(coordsList));
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN,0,coordsList.size() /3 );

        //剔除正面
        gl.glCullFace(GL10.GL_FRONT);
        //绘制锥底
        colorBuffer.position(4);
        gl.glColorPointer(4,GL10.GL_FLOAT,0,colorBuffer);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,BufferUtil.arr2ByteBuffer(coordsConeBottomList));
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN,0,coordsConeBottomList.size() /3 );
    }
}
