package com.tiny.com.ops;

import android.opengl.GLU;
import android.util.FloatMath;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import static com.tiny.com.ops.com.tiny.com.ops.util.BufferUtil.arr2FloatBuffer;
import static java.lang.System.console;

/**
 * 圆环
 * Created by 95 on 2016/5/21.
 */

public class MyRingRenderer extends AbstractMyRenderer {
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glColor4f(1f,1f,0f,1f);
        //模型视图矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();//加载单位矩阵
        GLU.gluLookAt(gl,0,0,5,0,0,0,0,1,0);

        gl.glRotatef(xrotate, 1, 0, 0);
        gl.glRotatef(yrotate, 0, 1, 0);

        //环数据
        List<Float> coordsList = new ArrayList<Float>();
        double Rinner = 0.2;
        double Rring = 0.3;

        //循环系数
        int count = 40;
        float alpha = 0;
        float alphaStep = (float) (2 * Math.PI / count);
        float x0,y0,z0,x1,y1,z1;

        int count0 = 40;
        float betaStep = (float) (2 * Math.PI / count0);
        float beta = 0;
        for (int i = 0 ; i <= count ; i++)
        {
            alpha = i * alphaStep;
            for (int j = 0 ; j <= count0 ; j ++)
            {
                beta = j * betaStep;
                x0 = (float)( Math.cos( alpha ) * (Rinner + Rring * (1+ Math.cos(beta))));
                y0 = (float)( Math.sin( alpha ) * (Rinner + Rring * (1+ Math.cos(beta))));
                z0 = (float) (- Rring * Math.sin(beta));

                x1 = (float)( Math.cos( alpha + alphaStep) * (Rinner + Rring * (1+ Math.cos(beta))));
                y1 = (float)( Math.sin( alpha + alphaStep) * (Rinner + Rring * (1+ Math.cos(beta))));
                z1 = (float) (- Rring * Math.sin(beta));

//                System.out.println("x0:"+ x0);
//                System.out.println("y0:"+ y0);
//                System.out.println("z0:"+ z0);
//
//                System.out.println("x1:"+ x1);
//                System.out.println("y1:"+ y1);
//                System.out.println("z1:"+ z1);

                coordsList.add(x0);
                coordsList.add(y0);
                coordsList.add(z0);
                coordsList.add(x1);
                coordsList.add(y1);
                coordsList.add(z1);
            }
        }
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,arr2FloatBuffer(coordsList));
        gl.glDrawArrays(GL10.GL_LINE_STRIP,0,coordsList.size()/3);
    }
}
