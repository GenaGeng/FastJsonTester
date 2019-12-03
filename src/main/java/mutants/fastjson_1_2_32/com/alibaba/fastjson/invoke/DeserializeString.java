package mutants.fastjson_1_2_32.com.alibaba.fastjson.invoke;

import mutants.fastjson_1_2_32.com.alibaba.fastjson.JSON;
import testsuite.Person;

/**
 * @author GN
 * @description deserialize string to JavaBean object
 * @date 2019/12/2
 */
public class DeserializeString {

    public static Person deserializeString(String str){

        Person person = JSON.parseObject(str,Person.class);

        return person;
    }
}
