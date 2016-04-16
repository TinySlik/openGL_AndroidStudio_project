package com.tiny.com.ops;

import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.support.v4.view.KeyEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
    private  AbstractMyRenderer render;
    private MyGLSurfaceView view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MyGLSurfaceView(this);
        //renderer:渲染器
        render = new MyPointSizeRenderer1();
        view.setRenderer(render);
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        //GLSurfaceView.RENDERMODE_CONTINUOUSLY:持续渲染
        //GLSurfaceView.RENDERMODE_WHEN_DIRTY:脏渲染，命令渲染
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        float step = 0.5f;
        if(keyCode == KeyEvent.KEYCODE_8){
            render.xrotate = render.xrotate - step;
        }else if(keyCode == KeyEvent.KEYCODE_2){
            render.xrotate = render.xrotate + step;
        }else if(keyCode == KeyEvent.KEYCODE_4){
            render.yrotate = render.yrotate - step;
        }else if(keyCode == KeyEvent.KEYCODE_6){
            render.yrotate = render.yrotate + step;
        }
        view.requestRender();
        return super.onKeyDown(keyCode, event);
    }
}
