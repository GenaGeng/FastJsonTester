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
package mutants.fastjson_1_2_48.com.alibaba.fastjson.parser;


import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;

/**
 * @author wenshao[szujobs@hotmail.com]
 */
@Deprecated
public class DefaultExtJSONParser extends DefaultJSONParser {

    public DefaultExtJSONParser(String input){
        this(input, com.alibaba.fastjson.parser.ParserConfig.getGlobalInstance());
    }

    public DefaultExtJSONParser(String input, com.alibaba.fastjson.parser.ParserConfig mapping){
        super(input, mapping);
    }

    public DefaultExtJSONParser(String input, com.alibaba.fastjson.parser.ParserConfig mapping, int features){
        super(input, mapping, features);
    }

    public DefaultExtJSONParser(char[] input, int length, ParserConfig mapping, int features){
        super(input, length, mapping, features);
    }

    
}