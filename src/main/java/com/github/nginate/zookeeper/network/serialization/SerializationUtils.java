package com.github.nginate.zookeeper.network.serialization;

import com.github.nginate.zookeeper.network.exceptions.SerializationException;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import lombok.experimental.UtilityClass;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static com.github.nginate.zookeeper.network.serialization.ReflectionUtils.doWithSortedFields;
import static com.github.nginate.zookeeper.network.serialization.TypeName.*;

@UtilityClass
public class SerializationUtils {
    private static final Map<TypeName, BiConsumer<ByteBuf, Object>> serializers = new EnumMap<>(TypeName.class);
    private static final Map<TypeName, BiFunction<ByteBuf, Class<?>, Object>> deserializers = new EnumMap<>(TypeName
            .class);

    private static final Predicate<Field> fieldFilter = field -> field.isAnnotationPresent(Type.class);
    private static final Comparator<Field> comparator = Comparator.comparing(field -> field.getAnnotation(Type.class)
            .order());

    static {
        serializers.put(BOOLEAN, (buffer, o) -> buffer.writeBoolean((boolean) Optional.ofNullable(o).orElse(0)));
        serializers.put(INT8, (buffer, o) -> buffer.writeByte((byte) Optional.ofNullable(o).orElse((byte) -1)));
        serializers.put(INT16, (buffer, o) -> buffer.writeShort((short) Optional.ofNullable(o).orElse((short) -1)));
        serializers.put(INT32, (buffer, o) -> buffer.writeInt((int) Optional.ofNullable(o).orElse(-1)));
        serializers.put(INT64, (buffer, o) -> buffer.writeLong((long) Optional.ofNullable(o).orElse(-1L)));
        serializers.put(STRING, (buffer, o) -> {
            if (o != null) {
                byte[] data = String.class.cast(o).getBytes(Charsets.UTF_8);
                buffer.writeShort(data.length).writeBytes(data);
            } else {
                buffer.writeShort(-1);
            }
        });
        serializers.put(BYTES, (buffer, o) -> {
            if (o != null) {
                byte[] data = (byte[]) o;
                buffer.writeInt(data.length).writeBytes(data);
            } else {
                buffer.writeInt(-1);
            }
        });
        serializers.put(WRAPPER, SerializationUtils::serializeObject);

        deserializers.put(BOOLEAN, (buffer, clazz) -> buffer.readBoolean());
        deserializers.put(INT8, (buffer, clazz) -> buffer.readByte());
        deserializers.put(INT16, (buffer, clazz) -> buffer.readShort());
        deserializers.put(INT32, (buffer, clazz) -> buffer.readInt());
        deserializers.put(INT64, (buffer, clazz) -> buffer.readLong());
        deserializers.put(STRING, (buffer, clazz) -> {
            short size = buffer.readShort();
            if (size == -1) {
                return null;
            } else {
                byte[] rawString = new byte[size];
                buffer.readBytes(rawString);
                return new String(rawString, Charsets.UTF_8);
            }
        });
        deserializers.put(BYTES, (buffer, clazz) -> {
            int size = buffer.readInt();
            if (size == -1) {
                return null;
            } else {
                byte[] array = new byte[size];
                buffer.readBytes(array);
                return array;
            }
        });
        deserializers.put(WRAPPER, SerializationUtils::deserializeObject);
    }

    public static <T> T deserializeObject(ByteBuf buf, Class<T> clazz) {
        try {
            T response = clazz.newInstance();
            doWithSortedFields(clazz, fieldFilter, comparator, field -> readField(field, response, buf));
            return response;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    public static void serializeObject(ByteBuf bodyBuffer, Object message) {
        doWithSortedFields(message.getClass(), fieldFilter, comparator,
                field -> writeField(field, message, bodyBuffer));
    }

    private static void readField(Field field, Object response, ByteBuf buf) {
        try {
            Class<?> clazz = field.getType();
            Type type = field.getAnnotation(Type.class);
            BiFunction<ByteBuf, Class<?>, Object> deserializer = deserializers.get(type.value());
            Object value = null;
            if (type.value() != BYTES && field.getType().isArray()) {
                int arraySize = buf.readInt();
                if (arraySize != -1) {
                    value = IntStream.range(0, arraySize)
                            .mapToObj(i -> deserializer.apply(buf, field.getType().getComponentType()))
                            .toArray(i -> (Object[]) Array.newInstance(field.getType().getComponentType(), arraySize));
                }
            } else {
                value = deserializer.apply(buf, clazz);
            }
            PropertyUtils.setProperty(response, field.getName(), value);
        } catch (Exception e) {
            throw new SerializationException("Can't deserialize field " + field.getName(), e);
        }
    }

    private static void writeField(Field field, Object message, ByteBuf buf) {
        try {
            Type type = field.getAnnotation(Type.class);
            BiConsumer<ByteBuf, Object> serializer = serializers.get(type.value());
            Object value = PropertyUtils.getProperty(message, field.getName());
            if (type.value() != BYTES && field.getType().isArray()) {
                if (value != null) {
                    Object[] array = Object[].class.cast(value);
                    buf.writeInt(array.length);
                    for (Object obj : array) {
                        serializer.accept(buf, obj);
                    }
                } else {
                    buf.writeInt(-1);
                }
            } else {
                serializer.accept(buf, value);
            }
        } catch (Exception e) {
            throw new SerializationException("Can't serialize field " + field.getName(), e);
        }
    }

}
