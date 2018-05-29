package com.pivotal.springboot2;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
@ToString
public class TvShow {
    @Id
    private String id;
    private String name;
}
