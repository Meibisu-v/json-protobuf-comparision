package com.example.optimizationhw;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;

public class BenchmarkRunner {

    @State(Scope.Thread)
    public static class MyState {
        TestDto dto;
        String json;
        byte[] protoData;

        @Setup(Level.Iteration)
        public void init() throws IOException {
            dto = TestDto.builder()
                    .name("Alice")
                    .age(30)
                    .ID(333)
                    .build();
            json = JsonSerializer.toJson(dto);
            protoData = ProtobufSerializer.serialize(dto, TestDto.class);
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Fork(value = 2, warmups = 1)
    @Measurement(iterations = 5, batchSize = 10)
    public void benchmarkJSONSerialize(MyState dto, Blackhole blackhole) throws IOException {
        String json = JsonSerializer.toJson(dto.dto);
        blackhole.consume(json);
    }


    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Fork(value = 2, warmups = 1)
    @Measurement(iterations = 5, batchSize = 10)
    public void benchmarkProtoSerialize(MyState dto, Blackhole blackhole) throws IOException {
       byte[] bytes = ProtobufSerializer.serialize(dto.dto, TestDto.class);
        blackhole.consume(bytes);
    }

    @Benchmark
    @Fork(value = 2, warmups = 1)
    @Measurement(iterations = 5, batchSize = 10)
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    public void benchmarkJSONDeserialize(MyState state, Blackhole blackhole) throws IOException {
        TestDto dto = JsonSerializer.fromJson(state.json, TestDto.class);
        blackhole.consume(dto);
    }
    @Benchmark
    @Fork(value = 2, warmups = 1)
    @Measurement(iterations = 5, batchSize = 10)
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    public void benchmarkProtoDeserialize(MyState state,Blackhole blackhole) throws IOException {
        TestDto dto = ProtobufSerializer.deSerialize(state.protoData, TestDto.class);
        blackhole.consume(dto);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
