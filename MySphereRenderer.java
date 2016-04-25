package com.tiny.com.ops;

import android.opengl.GLU;
import com.tiny.com.ops.com.tiny.com.ops.util.BufferUtil;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.spec.OAEPParameterSpec;
import javax.microedition.khronos.opengles.GL10;

/**绘制一个球体
 * Created by 95 on 2016/4/25.
 */
public class MySphereRenderer extends AbstractMyRenderer{
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glColor4f(1f,0f,0f,1f);
        //模型视图矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();//加载单位矩阵
        GLU.gluLookAt(gl,0,0,5,0,0,0,0,1,0);

        gl.glRotatef(xrotate, 1, 0, 0);
        gl.glRotatef(yrotate, 0, 1, 0);

        float R = 0.7f;
        int statck = 8 ;
        float stackStep =(float) (Math.PI / statck) ;//纬角度
        int slice = 12;
        float sliceStep = (float) (Math . PI) / slice ;//水平圆递增角度
        float r0,r1,y0,y1,x0,x1,z0,z1;
        float alpha0 = 0,alpha1 = 0;
        float beta = 0;
        List<Float> coords = new ArrayList<Float>();

        for (int i = 0; i <= statck ; i ++ ) {
            alpha0 = (float) (-Math.PI / 2 + (i * stackStep));
            alpha1 = (float) (-Math.PI / 2 + ((i + 1) * stackStep));
            y0 = (float) (R * Math.sin(alpha0));
            r0 = (float) (R * Math.cos(alpha0));
            y1 = (float) (R * Math.sin(alpha1));
            r1 = (float) (R * Math.cos(alpha1));
            for (int j = 0; j <(slice * 2) ; j++)
            {
                beta = j * sliceStep;
                x0 = (float)  (r0 * Math.cos(beta));
                z0 = (float)  (r0 * Math.sin(beta));
                x1 = (float)  (r1 * Math.cos(beta));
                z1 = (float)  (r1 * Math.sin(beta));
                coords.add(x0);
                coords.add(y0);
                coords.add(z0);
                coords.add(x1);
                coords.add(y1);
                coords.add(z1);
            }
        }

        FloatBuffer fbb = BufferUtil.arr2FloatBuffer(coords);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fbb);
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coords.size() / 3);
    }
}
