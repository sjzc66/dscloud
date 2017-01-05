package com.jzfq.fms.rest.common;


import com.jzfq.fms.common.common.JsonResult;
import com.jzfq.fms.interceptor.PageList;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aokunsang
 * @description 解决Date类型返回json格式为自定义格式
 * @date 2013-5-28
 */
public class DateFormatMapper extends ObjectMapper {

    public DateFormatMapper() {

        SimpleModule module = new SimpleModule("JsonResultModule", new Version(1, 1, 1, ""));
        module.addSerializer(JsonResult.class, new JsonResultSerializer(this));
        module.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value,
                                  JsonGenerator jsonGenerator,
                                  SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jsonGenerator.writeString(sdf.format(value));
            }
        });
        module.addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
            @Override
            public void serialize(BigDecimal value,
                                  JsonGenerator jsonGenerator,
                                  SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                DecimalFormat df = null;
                if (value.doubleValue() == 0) {
                    df = new DecimalFormat("0.00");//四舍五入
                } else if (value.doubleValue() < 1) {
                    df = new DecimalFormat("0.00%");//四舍五入
                } else {
                    df = new DecimalFormat("#.00");//四舍五入
                }
                jsonGenerator.writeString(df.format(value.doubleValue()));
            }
        });
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString("");
            }
        });
        this.registerModule(module);

    }

    private class JsonResultSerializer extends JsonSerializer<JsonResult> {

        private ObjectMapper mapper;

        public JsonResultSerializer(ObjectMapper mapper) {
            this.mapper = mapper;
        }

        public JsonResultSerializer() {
        }

        @Override
        public void serialize(JsonResult result, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            Map<String, Object> map = new HashMap<>(10);
            if (result.getResult() instanceof PageList) {
                PageList list = (PageList) result.getResult();
                map.put("data", result.getResult());
                map.put("currentPage", list.getCurrentPage());
                map.put("pageSize", list.getPageSize());
                map.put("totalCount", list.getTotalCount());
                result.setResult(map);
            }
            Map<String, Object> finalResult = new HashMap<>(10);
            finalResult.put("result", result.getResult());
            finalResult.put("msg", result.getMsg());
            finalResult.put("success", result.isSuccess());
            this.mapper.writeValue(jsonGenerator, finalResult);

        }
    }

}