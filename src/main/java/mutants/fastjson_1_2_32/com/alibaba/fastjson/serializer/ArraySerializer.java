/*
 * Copyright 1999-2017 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mutants.fastjson_1_2_32.com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author wenshao[szujobs@hotmail.com]
 */
public class ArraySerializer implements ObjectSerializer {

	private final Class<?> componentType;
    private final ObjectSerializer compObjectSerializer;

    public ArraySerializer(Class<?> componentType, ObjectSerializer compObjectSerializer){
        this.componentType = componentType;
        this.compObjectSerializer = compObjectSerializer;
    }

    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
                                                                                                       throws IOException {
        SerializeWriter out = serializer.out;

        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }

        Object[] array = (Object[]) object;
        int size = array.length;

        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);

        try {
            out.append('[');
            for (int i = 0; i < size; ++i) {
            	if (i != 0) {
            		out.append(',');
            	}
                Object item = array[i];

                if (item == null) {
                    out.append("null");
                } else if (item.getClass() == componentType) {
                	compObjectSerializer.write(serializer, item, i, null, 0);
                } else {
                	ObjectSerializer itemSerializer = serializer.getObjectWriter(item.getClass());
                	itemSerializer.write(serializer, item, i, null, 0);
                }
            }
            out.append(']');
        } finally {
            serializer.context = context;
        }
    }
}
