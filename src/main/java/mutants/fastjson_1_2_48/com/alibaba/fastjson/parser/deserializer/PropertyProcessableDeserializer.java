package mutants.fastjson_1_2_48.com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessable;

import java.lang.reflect.Type;

/**
 * Created by wenshao on 15/07/2017.
 */
public class PropertyProcessableDeserializer implements ObjectDeserializer {
    public final Class<com.alibaba.fastjson.parser.deserializer.PropertyProcessable> type;

    public PropertyProcessableDeserializer(Class<com.alibaba.fastjson.parser.deserializer.PropertyProcessable> type) {
        this.type = type;
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        PropertyProcessable processable;
        try {
            processable = this.type.newInstance();
        } catch (Exception e) {
            throw new JSONException("craete instance error");
        }

        Object object =parser.parse(processable, fieldName);

        return (T) object;
    }

    public int getFastMatchToken() {
        return JSONToken.LBRACE;
    }
}
