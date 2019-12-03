package mutants.fastjson_1_2_32.com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.SerializeFilter;

/**
 * @since 1.2.9
 *
 */
public interface ContextValueFilter extends SerializeFilter {
    Object process(BeanContext context, Object object, String name, Object value);
}
