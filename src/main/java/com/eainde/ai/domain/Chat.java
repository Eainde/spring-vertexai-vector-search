package com.eainde.ai.domain;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private String message;
    private String collectionName;
}
