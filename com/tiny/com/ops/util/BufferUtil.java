package com.tiny.com.ops.com.tiny.com.ops.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Created by 95 on 2016/4/16.
 */
public class BufferUtil {
    public static ByteBuffer arr2ByteBuffer(float[] arr){
        ByteBuffer ibb = ByteBuffer.allocateDirect(arr.length * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        fbb.put(arr);
        ibb.position(0);
        return  ibb;
    }

    public static ByteBuffer arr2ByteBuffer(List<Float> list){
        ByteBuffer ibb = ByteBuffer.allocateDirect(list.size() * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        for(Float f: list)
        {
            fbb.put(f);
        }
        ibb.position(0);
        return  ibb;
    }

    public static FloatBuffer arr2FloatBuffer(List<Float> list){
        ByteBuffer ibb = ByteBuffer.allocateDirect(list.size() * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        for(Float f: list)
        {
            fbb.put(f);
        }
        fbb.position(0);
        return  fbb;
    }
}




