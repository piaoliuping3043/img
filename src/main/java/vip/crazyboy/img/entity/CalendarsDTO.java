package vip.crazyboy.img.entity;

import lombok.Data;

import java.util.List;

@Data
public class CalendarsDTO {
    private Integer totalWeek;
    private Long fhTimestamp;
    private Long refreshMilliseconds;
    private List<CalendarDTO> calendars;
}
