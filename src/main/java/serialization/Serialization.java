package serialization;

import com.alibaba.fastjson.JSON;
import testsuite.Person;

/**
 * @author GengNing
 * @description  serialization
 * @date 2019/11/6
 */
public class Serialization {
    public static String serializationPerson(Person person){
        String s = JSON.toJSONString(person);
        return s;
    }

}
