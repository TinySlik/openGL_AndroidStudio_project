package com.tiny.com.ops;

import android.opengl.GLU;

import com.tiny.com.ops.com.tiny.com.ops.util.BufferUtil;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * 点渲染器，绘制螺旋线
 */
public class MyLinesStrpsRenderer1 extends AbstractMyRenderer{

    @Override
    public void onDrawFrame(GL10 gl) {
        //  清除颜色缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        gl.glColor4f(0f,1f,0f,1f);
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
        List<Float> coordsList = new ArrayList<Float>();
        float x = 0f,y = 0f,z = 1.0f;
        float zstep = 0.005f;
        float psize = 1.0f;
        float pstep = 0.2f;
        for( float alpha = 0f;alpha < Math.PI * 6 ; alpha = (float)(alpha + Math.PI / 32)){
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            gl.glPointSize(psize = psize + pstep );
            z = z - zstep;
            coordsList.add(x);
            coordsList.add(y);
            coordsList.add(z);
        }

//        gl.glVertexPointer(3,GL10.GL_FLOAT,0, BufferUtil.arr2ByteBuffer(coordsList));
//        gl.glDrawArrays(GL10.GL_LINE_STRIP,0,coordsList.size()/3);

        gl.glVertexPointer(3,GL10.GL_FLOAT,0, BufferUtil.arr2ByteBuffer(coordsList));
        gl.glDrawArrays(GL10.GL_LINES,0,coordsList.size()/3);
    }
}
