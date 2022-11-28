package com.example.inside_test_task.mapper;

import com.example.inside_test_task.dto.in.MessageRequest;
import com.example.inside_test_task.entity.MyMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {

    @Mapping(target = "id", ignore = true)
    public abstract MyMessage messageRequestToMyMessage(MessageRequest request);

}
