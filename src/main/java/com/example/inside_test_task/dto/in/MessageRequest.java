package com.example.inside_test_task.dto.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequest {
    private String name;
    private String message;
}
