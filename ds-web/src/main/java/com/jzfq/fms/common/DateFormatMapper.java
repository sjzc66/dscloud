package com.jzfq.fms.common;


import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 解决Date类型返回json格式为自定义格式
 * @author aokunsang
 * @date 2013-5-28
 */
public class DateFormatMapper extends ObjectMapper {

	public DateFormatMapper(){
		CustomSerializerFactory factory = new CustomSerializerFactory();
		factory.addGenericMapping(Date.class, new JsonSerializer<Date>(){
			@Override
			public void serialize(Date value,
								  JsonGenerator jsonGenerator,
								  SerializerProvider provider)
					throws IOException, JsonProcessingException {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonGenerator.writeString(sdf.format(value));
			}
		});
		factory.addGenericMapping(BigDecimal.class, new JsonSerializer<BigDecimal>(){
			@Override
			public void serialize(BigDecimal value,
								  JsonGenerator jsonGenerator,
								  SerializerProvider provider)
					throws IOException, JsonProcessingException {
				DecimalFormat df = null;
				if(value.doubleValue()==0){
					df = new DecimalFormat("0.00");//四舍五入
				}else if(value.doubleValue()<1){
					df = new DecimalFormat("0.00%");//四舍五入
				}else{
					df = new DecimalFormat("#.00");//四舍五入
				}
				jsonGenerator.writeString(df.format(value.doubleValue()));
			}
		});
		this.setSerializerFactory(factory);
	}
}