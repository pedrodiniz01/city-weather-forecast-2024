package com.example.demo.data;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "City")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private String forecastLink;
}
