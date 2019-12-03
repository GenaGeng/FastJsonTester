package mutants.fastjson_1_2_45.com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;


public class EnumerationSerializer implements com.alibaba.fastjson.serializer.ObjectSerializer {
    public static EnumerationSerializer instance = new EnumerationSerializer();
    
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        if (object == null) {
            out.writeNull(com.alibaba.fastjson.serializer.SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        
        Type elementType = null;
        if (out.isEnabled(SerializerFeature.WriteClassName)) {
            if (fieldType instanceof ParameterizedType) {
                ParameterizedType param = (ParameterizedType) fieldType;
                elementType = param.getActualTypeArguments()[0];
            }
        }
        
        Enumeration<?> e = (Enumeration<?>) object;
        
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);

        try {
            int i = 0;
            out.append('[');
            while (e.hasMoreElements()) {
                Object item = e.nextElement();
                if (i++ != 0) {
                    out.append(',');
                }

                if (item == null) {
                    out.writeNull();
                    continue;
                }

                ObjectSerializer itemSerializer = serializer.getObjectWriter(item.getClass());
                itemSerializer.write(serializer, item, i - 1, elementType, 0);
            }
            out.append(']');
        } finally {
            serializer.context = context;
        }
    }
}
