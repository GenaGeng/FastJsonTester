package mutants.fastjson_1_2_32.com.alibaba.fastjson.invoke;

import mutants.fastjson_1_2_32.com.alibaba.fastjson.JSON;
import testsuite.Person;

/**
 * @author GN
 * @description deserialize binary file to JavaBean object
 * @date 2019/12/2
 */
public class DeserializeBinary {
    public static Person deserializeBinary(byte[] binaryContent){

        Person person = JSON.parseObject(binaryContent,Person.class);

        return person;
    }
}
