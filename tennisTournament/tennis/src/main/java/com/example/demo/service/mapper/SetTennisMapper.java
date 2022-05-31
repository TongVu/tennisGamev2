package com.example.demo.service.mapper;

import com.example.demo.entity.SetTennis;
import com.example.demo.service.dto.SetTennisDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SetTennisMapper {
    SetTennisMapper INSTANCE = Mappers.getMapper(SetTennisMapper.class);

    @Mapping(source=  "match.player1.firstName", target = "playerOneFirstName")
    @Mapping(source=  "match.player2.firstName", target = "playerTwoFirstName")
    @Mapping(source = "match.stadium.name", target = "stadiumName")
    @Mapping(source = "match.round.roundName", target = "roundName")
    SetTennisDto toDto(SetTennis setTennis);

    List<SetTennisDto> toDtos(List<SetTennis> setTennisList);
}
