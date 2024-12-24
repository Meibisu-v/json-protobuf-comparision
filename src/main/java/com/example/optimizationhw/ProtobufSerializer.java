package com.example.optimizationhw;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import static com.dyuproject.protostuff.runtime.RuntimeSchema.getSchema;

public class ProtobufSerializer {
    private static final ThreadLocal<LinkedBuffer> BUFFER_THREAD_LOCAL = ThreadLocal.withInitial(
            () -> LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE)
    );
    public static <T> byte[] serialize(T t,Class<T> clazz) {
        LinkedBuffer buffer = BUFFER_THREAD_LOCAL.get();
        try {
            return ProtobufIOUtil.toByteArray(t, getSchema(clazz), buffer);
        } finally {
            buffer.clear();
        }
    }


    public static <T> T deSerialize(byte[] data,Class<T> clazz) {
        RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);
        T t = runtimeSchema.newMessage();
        ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
        return t;
    }
}
