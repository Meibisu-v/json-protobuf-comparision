package com.example.optimizationhw;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ProtobufSerializerTest {


    // Вспомогательный класс для тестирования вложенных объектов
    public static class NestedDto {
        public String nestedName;
        public int nestedValue;

        public NestedDto() {}

        public NestedDto(String nestedName, int nestedValue) {
            this.nestedName = nestedName;
            this.nestedValue = nestedValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NestedDto nestedDto = (NestedDto) o;
            return nestedValue == nestedDto.nestedValue && Objects.equals(nestedName, nestedDto.nestedName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nestedName, nestedValue);
        }
    }

    // Основной тестовый класс с вложенными объектами и коллекциями
    public static class ComplexDto {
        public String name;
        public int age;
        public List<NestedDto> nestedDtos;

        public ComplexDto() {}

        public ComplexDto(String name, int age, List<NestedDto> nestedDtos) {
            this.name = name;
            this.age = age;
            this.nestedDtos = nestedDtos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComplexDto that = (ComplexDto) o;
            return age == that.age && Objects.equals(name, that.name) && Objects.equals(nestedDtos, that.nestedDtos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, nestedDtos);
        }
    }

    @Test
    public void testComplexSerializationAndDeserialization() {
        List<NestedDto> nestedDtos = Arrays.asList(
                new NestedDto("Nested1", 10),
                new NestedDto("Nested2", 20)
        );
        ComplexDto dto = new ComplexDto("Alice", 30, nestedDtos);

        // Сериализация
        byte[] data = ProtobufSerializer.serialize(dto, ComplexDto.class);

        // Десериализация
        ComplexDto deserializedDto = ProtobufSerializer.deSerialize(data, ComplexDto.class);

        // Проверка равенства исходного и десериализованного объектов
        assertEquals(dto, deserializedDto);
    }

    @Test
    public void testEmptySerializationAndDeserialization() {
        ComplexDto dto = new ComplexDto();

        // Сериализация
        byte[] data = ProtobufSerializer.serialize(dto, ComplexDto.class);

        // Десериализация
        ComplexDto deserializedDto = ProtobufSerializer.deSerialize(data, ComplexDto.class);

        // Проверка равенства исходного и десериализованного объектов
        assertEquals(dto, deserializedDto);
    }

    @Test
    public void testInvalidDeserialization() {
        byte[] invalidData = new byte[]{1, 2, 3};

        // Проверка того, что десериализация некорректных данных вызывает RuntimeException
        assertThrows(RuntimeException.class, () -> {
            ProtobufSerializer.deSerialize(invalidData, ComplexDto.class);
        });
    }
    @Test
    public void testSerializationAndDeserialization() {
        TestDto dto = new TestDto("Alice", 30, 123);
        byte[] data = ProtobufSerializer.serialize(dto, TestDto.class);
        TestDto deserializedDto = ProtobufSerializer.deSerialize(data, TestDto.class);

        assertEquals(dto, deserializedDto);
    }
}
