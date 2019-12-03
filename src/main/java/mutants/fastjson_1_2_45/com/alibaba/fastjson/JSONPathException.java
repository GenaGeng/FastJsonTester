package mutants.fastjson_1_2_45.com.alibaba.fastjson;

import com.alibaba.fastjson.JSONException;

@SuppressWarnings("serial")
public class JSONPathException extends JSONException {

    public JSONPathException(String message){
        super(message);
    }
    
    public JSONPathException(String message, Throwable cause){
        super(message, cause);
    }
}
