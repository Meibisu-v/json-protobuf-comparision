package com.example.optimizationhw;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonAutoDetect
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TestDto {
    public String name;
    public int age;
    public int ID;
}
