package com.example.storeforsocks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    @NotEmpty
    private Color color;
    @NotEmpty
    private Size size;
    @Positive
    @Max(100)
    @Min(0)
    private int cottonPart;
    @Positive
    @Min(1)
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPart == socks.cottonPart && color == socks.color && size == socks.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPart);
    }
}