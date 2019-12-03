package mutants.fastjson_1_2_45.com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.util.Set;


public interface AutowiredObjectDeserializer extends ObjectDeserializer {
	Set<Type> getAutowiredFor();
}
