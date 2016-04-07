package com.tiny.com.ops;

import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGLSurfaceView view = new MyGLSurfaceView(this);
        //renderer:渲染器
        view.setRenderer(new MyRenderer());
        setContentView(view);
    }

    class MyGLSurfaceView extends GLSurfaceView{
        public MyGLSurfaceView(Context context) {
            super(context);
        }

        public MyGLSurfaceView(Context context , AttributeSet attrs) {
            super(context, attrs);
        }
    }

    class MyRenderer implements android.opengl.GLSurfaceView.Renderer {

        @Override
        //表层创建时
        public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
            //设置清屏色
            gl.glClearColor(0,0,0,1);
            //启用客户端状态
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        }

        @Override
        //表层size时
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视口,输出画面区域
            gl.glViewport(0,0,width,height);
            //取比例
            float ratio = (float)width / (float)height ;
            //矩阵，矩阵模式，投影矩阵，openGL基于状态机
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //平截头体
            gl.glFrustumf(-1f,1f,-ratio,ratio,3,7);
        }

        //绘图
        public void onDrawFrame(GL10 gl) {
            //  清除颜色缓存
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            //eyex,eyey,eyez:放置眼球坐标
            //centerx,centery,centerz:眼球的观察点
            //upx,upy,upz,制定画面向上方
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();//加载单位矩阵
            GLU.gluLookAt(gl,0,0,5,0,0,0,0,1,0);

            //画三角形
            //绘制数组
            //三角形坐标
            float[] coords = {
                    0f,0.5f,0f,
                    -0.5f,-0.5f,0f,
                    0.5f,-0.5f,0f
            };
            //分配字节缓存区空间，存放定点坐标数据
            ByteBuffer ibb = ByteBuffer.allocateDirect(coords.length * 4);
            //设置的顺序（本地顺序）
            ibb.order (ByteOrder.nativeOrder());
            //放置顶点坐标数组
            FloatBuffer fbb = ibb.asFloatBuffer();
            fbb.put(coords);
            //定位指针的位置，从该位置开始读取顶点数据
            ibb.position(0);

            //设置绘图区颜色：红色
            gl.glColor4f(1f,0f,0f,1f);
            //
            gl.glVertexPointer(3,GL10.GL_FLOAT,0,ibb);
            //绘制三角形
            gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);
        }
    }
}
