package mutants.fastjson_1_2_32.com.alibaba.fastjson.invoke;

import mutants.fastjson_1_2_32.com.alibaba.fastjson.JSON;
import testsuite.Person;

/**
 * @author GN
 * @description deserialize json file to JavaBean object
 * @date 2019/12/2
 */
public class DeserializeJson {
    public static Person deserializeJson(String jsonContent) {

        Person person = JSON.parseObject(jsonContent, Person.class);

        return person;
    }

}
