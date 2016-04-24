package com.tiny.com.ops;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.tiny.com.ops.AbstractMyRenderer;

/**
 * Ä£°å:Ê¹ÓÃÄ£°å»º³åÇø,Ïàµ±ÓÚ. Ê¹ÓÃ¾ØÕóÊµÏÖ»æÖÆ²»Í¬µÄÎïÌå.¶¯»­Ð§¹û Ê¹ÓÃÄ£°å»º³åÇøÐèÒªÓ²¼þÖ§³ÖGPU±à³Ì²Å¿ÉÒÔ.
 */
public class MyStencilRenderer extends AbstractMyRenderer{

    public float xrotate = 0;
    public float yrotate = 0;
    List<Float> vertexList;
    float ratio = 0;
    float left = -ratio, top = 1f, width = 0.3f;
    boolean xadd = false;
    boolean yadd = false;

    public void onDrawFrame(GL10 gl) {
        // Çå³ýÑÕÉ«,Éî¶È,Ä£°å»º³åÇø
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT| GL10.GL_STENCIL_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        /****************************** »æÖÆ°×É«ÂÝÐýÏß **********************************/
        gl.glPushMatrix();
        gl.glRotatef(xrotate, 1, 0, 0);// ÈÆxÐý×ª
        gl.glRotatef(yrotate, 0, 1, 0);// ÈÆyÐý×ª

        // µ¥µ÷×ÅÉ«
        gl.glShadeModel(GL10.GL_FLAT);

        vertexList = new ArrayList<Float>();
        float x = 0, y = .8f, z = 0, ystep = 0.005f, r = 0.7f;
        for (float angle = 0f; angle < (Math.PI * 2 * 3); angle += (Math.PI / 40)) {
            x = (float) (r * Math.cos(angle));
            z = -(float) (r * Math.sin(angle));
            y = y - ystep;
            vertexList.add(x);
            vertexList.add(y);
            vertexList.add(z);
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexList.size() * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer fvb = vbb.asFloatBuffer();
        for (Float f : vertexList) {
            fvb.put(f);
        }
        fvb.position(0);

        //Í¨¹ýÄ£°å²âÊÔÊ±,²ÅÄÜ¹»»æÖÆÍ¼°¸,·ñÔò²»»æÍ¼°¸.
        // »æÖÆ°×ÏßÖ±Ïß,ÉèÖÃÄ£°åº¯Êý,ËùÓÐ²Ù×÷¶¼²»ÄÜÍ¨¹ý²âÊÔ,µ«ÊÇ¶ÔÄ£°å»º³åÇøµÄÖµ½øÐÐÔö¼Ó
        gl.glStencilFunc(GL10.GL_NEVER, 0, 0);
        gl.glStencilOp(GL10.GL_INCR, GL10.GL_INCR, GL10.GL_INCR);

        // »æÖÆ°×É«ÂÝÐýÏß
        gl.glColor4f(0.2f, 0.4f, 0.8f, 1f);// °×Ïß
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fvb);
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, vertexList.size() / 3);
        gl.glPopMatrix();

        /****************************** 绘制红色方块 **********************************/
        if (xadd) {
            left = left + 0.01f;
        } else {
            left = left - 0.01f;
        }
        if (left <= (-ratio)) {
            xadd = true;
        }
        if (left >= (ratio - width)) {
            xadd = false;
        }

        if (yadd) {
            top = top + 0.01f;
        } else {
            top = top - 0.01f;
        }
        if (top >= 1) {
            yadd = false;
        }
        if (top <= (-1 + width)) {
            yadd = true;
        }

        float[] rectVertex = { left, top - width, 2f, left, top, 2f,
                left + width, top - width, 2f, left + width, top, 2f };
        vbb = ByteBuffer.allocateDirect(3 * 4 * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer rectfb = vbb.asFloatBuffer();
        rectfb.put(rectVertex);
        rectfb.position(0);

        // ÉèÖÃÄ£°åº¯Êý,ËùÓÐ²Ù×÷¶¼²»ÄÜÍ¨¹ý²âÊÔ,µ«ÊÇ¶ÔÄ£°å»º³åÇøµÄÖµ½øÐÐÔö¼Ó
        gl.glStencilFunc(GL10.GL_NOTEQUAL, 1, 1);
        gl.glStencilOp(GL10.GL_KEEP, GL10.GL_KEEP, GL10.GL_KEEP);

        gl.glColor4f(1f, 0f, 0f, 1f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectfb);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }

    // ÉèÖÃÊÓ¿Ú
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        gl.glViewport(0, 0, w, h);
        ratio = (float) w / h;
        left = -ratio;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        gl.glClearColor(0f, 0f, 0f, 1.0f);// ÇåÆÁÉ«
        gl.glClearStencil(0);             // Ä£°åÇå³ýÖµ

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// vertex array
        gl.glEnable(GL10.GL_DEPTH_TEST);// depth test
        gl.glEnable(GL10.GL_STENCIL_TEST);// ÆôÓÃÄ£°å²âÊÔ
    }
}
