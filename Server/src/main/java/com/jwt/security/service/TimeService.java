package com.jwt.security.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeService {

    private Timestamp date;
    private ZoneId timeZone = ZoneId.ofOffset("GMT", ZoneOffset.of("+07"));
}
