package mutants.fastjson_1_2_48.com.alibaba.fastjson.serializer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.LabelFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

public abstract class SerializeFilterable {

    protected List<com.alibaba.fastjson.serializer.BeforeFilter>       beforeFilters       = null;
    protected List<com.alibaba.fastjson.serializer.AfterFilter>        afterFilters        = null;
    protected List<com.alibaba.fastjson.serializer.PropertyFilter>     propertyFilters     = null;
    protected List<com.alibaba.fastjson.serializer.ValueFilter>        valueFilters        = null;
    protected List<com.alibaba.fastjson.serializer.NameFilter>         nameFilters         = null;
    protected List<com.alibaba.fastjson.serializer.PropertyPreFilter>  propertyPreFilters  = null;
    protected List<com.alibaba.fastjson.serializer.LabelFilter>        labelFilters        = null;
    protected List<com.alibaba.fastjson.serializer.ContextValueFilter> contextValueFilters = null;

    protected boolean                  writeDirect         = true;

    public List<com.alibaba.fastjson.serializer.BeforeFilter> getBeforeFilters() {
        if (beforeFilters == null) {
            beforeFilters = new ArrayList<com.alibaba.fastjson.serializer.BeforeFilter>();
            writeDirect = false;
        }

        return beforeFilters;
    }

    public List<com.alibaba.fastjson.serializer.AfterFilter> getAfterFilters() {
        if (afterFilters == null) {
            afterFilters = new ArrayList<com.alibaba.fastjson.serializer.AfterFilter>();
            writeDirect = false;
        }

        return afterFilters;
    }

    public List<com.alibaba.fastjson.serializer.NameFilter> getNameFilters() {
        if (nameFilters == null) {
            nameFilters = new ArrayList<com.alibaba.fastjson.serializer.NameFilter>();
            writeDirect = false;
        }

        return nameFilters;
    }

    public List<com.alibaba.fastjson.serializer.PropertyPreFilter> getPropertyPreFilters() {
        if (propertyPreFilters == null) {
            propertyPreFilters = new ArrayList<com.alibaba.fastjson.serializer.PropertyPreFilter>();
            writeDirect = false;
        }

        return propertyPreFilters;
    }

    public List<com.alibaba.fastjson.serializer.LabelFilter> getLabelFilters() {
        if (labelFilters == null) {
            labelFilters = new ArrayList<com.alibaba.fastjson.serializer.LabelFilter>();
            writeDirect = false;
        }

        return labelFilters;
    }

    public List<com.alibaba.fastjson.serializer.PropertyFilter> getPropertyFilters() {
        if (propertyFilters == null) {
            propertyFilters = new ArrayList<com.alibaba.fastjson.serializer.PropertyFilter>();
            writeDirect = false;
        }

        return propertyFilters;
    }

    public List<com.alibaba.fastjson.serializer.ContextValueFilter> getContextValueFilters() {
        if (contextValueFilters == null) {
            contextValueFilters = new ArrayList<com.alibaba.fastjson.serializer.ContextValueFilter>();
            writeDirect = false;
        }

        return contextValueFilters;
    }

    public List<com.alibaba.fastjson.serializer.ValueFilter> getValueFilters() {
        if (valueFilters == null) {
            valueFilters = new ArrayList<com.alibaba.fastjson.serializer.ValueFilter>();
            writeDirect = false;
        }

        return valueFilters;
    }

    public void addFilter(SerializeFilter filter) {
        if (filter == null) {
            return;
        }

        if (filter instanceof com.alibaba.fastjson.serializer.PropertyPreFilter) {
            this.getPropertyPreFilters().add((com.alibaba.fastjson.serializer.PropertyPreFilter) filter);
        }

        if (filter instanceof com.alibaba.fastjson.serializer.NameFilter) {
            this.getNameFilters().add((com.alibaba.fastjson.serializer.NameFilter) filter);
        }

        if (filter instanceof com.alibaba.fastjson.serializer.ValueFilter) {
            this.getValueFilters().add((com.alibaba.fastjson.serializer.ValueFilter) filter);
        }

        if (filter instanceof com.alibaba.fastjson.serializer.ContextValueFilter) {
            this.getContextValueFilters().add((com.alibaba.fastjson.serializer.ContextValueFilter) filter);
        }

        if (filter instanceof com.alibaba.fastjson.serializer.PropertyFilter) {
            this.getPropertyFilters().add((com.alibaba.fastjson.serializer.PropertyFilter) filter);
        }

        if (filter instanceof com.alibaba.fastjson.serializer.BeforeFilter) {
            this.getBeforeFilters().add((BeforeFilter) filter);
        }

        if (filter instanceof com.alibaba.fastjson.serializer.AfterFilter) {
            this.getAfterFilters().add((AfterFilter) filter);
        }

        if (filter instanceof com.alibaba.fastjson.serializer.LabelFilter) {
            this.getLabelFilters().add((LabelFilter) filter);
        }
    }

    public boolean applyName(com.alibaba.fastjson.serializer.JSONSerializer jsonBeanDeser, //
                             Object object, String key) {

        if (jsonBeanDeser.propertyPreFilters != null) {
            for (com.alibaba.fastjson.serializer.PropertyPreFilter filter : jsonBeanDeser.propertyPreFilters) {
                if (!filter.apply(jsonBeanDeser, object, key)) {
                    return false;
                }
            }
        }
        
        if (this.propertyPreFilters != null) {
            for (PropertyPreFilter filter : this.propertyPreFilters) {
                if (!filter.apply(jsonBeanDeser, object, key)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public boolean apply(com.alibaba.fastjson.serializer.JSONSerializer jsonBeanDeser, //
                         Object object, //
                         String key, Object propertyValue) {
        
        if (jsonBeanDeser.propertyFilters != null) {
            for (com.alibaba.fastjson.serializer.PropertyFilter propertyFilter : jsonBeanDeser.propertyFilters) {
                if (!propertyFilter.apply(object, key, propertyValue)) {
                    return false;
                }
            }
        }
        
        if (this.propertyFilters != null) {
            for (PropertyFilter propertyFilter : this.propertyFilters) {
                if (!propertyFilter.apply(object, key, propertyValue)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    protected String processKey(com.alibaba.fastjson.serializer.JSONSerializer jsonBeanDeser, //
                                Object object, //
                                String key, //
                                Object propertyValue) {

        if (jsonBeanDeser.nameFilters != null) {
            for (com.alibaba.fastjson.serializer.NameFilter nameFilter : jsonBeanDeser.nameFilters) {
                key = nameFilter.process(object, key, propertyValue);
            }
        }
        
        if (this.nameFilters != null) {
            for (NameFilter nameFilter : this.nameFilters) {
                key = nameFilter.process(object, key, propertyValue);
            }
        }

        return key;
    }
    
    protected Object processValue(com.alibaba.fastjson.serializer.JSONSerializer jsonBeanDeser, //
                                  BeanContext beanContext,
                                  Object object, //
                                  String key, //
                                  Object propertyValue) {

        if (propertyValue != null) {
            if ((jsonBeanDeser.out.writeNonStringValueAsString //
                    || (beanContext != null && (beanContext.getFeatures() & SerializerFeature.WriteNonStringValueAsString.mask) != 0))
                    && (propertyValue instanceof Number || propertyValue instanceof Boolean)) {
                String format = null;
                if (propertyValue instanceof Number
                        && beanContext != null) {
                    format = beanContext.getFormat();
                }

                if (format != null) {
                    propertyValue = new DecimalFormat(format).format(propertyValue);
                } else {
                    propertyValue = propertyValue.toString();
                }
            } else if (beanContext != null && beanContext.isJsonDirect()) {
                String jsonStr = (String) propertyValue;
                propertyValue = JSON.parse(jsonStr);
            }
        }
        
        if (jsonBeanDeser.valueFilters != null) {
            for (com.alibaba.fastjson.serializer.ValueFilter valueFilter : jsonBeanDeser.valueFilters) {
                propertyValue = valueFilter.process(object, key, propertyValue);
            }
        }

        List<com.alibaba.fastjson.serializer.ValueFilter> valueFilters = this.valueFilters;
        if (valueFilters != null) {
            for (ValueFilter valueFilter : valueFilters) {
                propertyValue = valueFilter.process(object, key, propertyValue);
            }
        }

        if (jsonBeanDeser.contextValueFilters != null) {
            for (com.alibaba.fastjson.serializer.ContextValueFilter valueFilter : jsonBeanDeser.contextValueFilters) {
                propertyValue = valueFilter.process(beanContext, object, key, propertyValue);
            }
        }

        if (this.contextValueFilters != null) {
            for (ContextValueFilter valueFilter : this.contextValueFilters) {
                propertyValue = valueFilter.process(beanContext, object, key, propertyValue);
            }
        }

        return propertyValue;
    }
    
    /**
     * only invoke by asm byte
     * 
     * @return
     */
    protected boolean writeDirect(JSONSerializer jsonBeanDeser) {
        return jsonBeanDeser.out.writeDirect //
               && this.writeDirect //
               && jsonBeanDeser.writeDirect;
    }
}
