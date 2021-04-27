package cn.pinming.microservice.measure.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * BigDecimal序列化
 * @author luo hao
 */
public class SerializerBigDecimal extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(Objects.isNull(value)) {
            gen.writeNull();
        } else {
            BigDecimal bigDecimal = value.setScale(2, RoundingMode.HALF_UP);
            gen.writeString(bigDecimal.toString());
        }
    }
}

