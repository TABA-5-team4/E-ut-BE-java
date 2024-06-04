package taba.team4.eut.biz.stat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taba.team4.eut.biz.stat.dto.response.MonthlyNegativeRatioDto;
import taba.team4.eut.biz.stat.dto.response.MonthlyStatDto;
import taba.team4.eut.biz.stat.dto.response.TodayStatDto;
import taba.team4.eut.biz.stat.dto.response.WeeklyStatDto;
import taba.team4.eut.biz.stat.service.StatService;
import taba.team4.eut.common.controller.BaseApiController;
import taba.team4.eut.common.controller.BaseApiDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1/stat")
@RequiredArgsConstructor
public class StatController extends BaseApiController<BaseApiDto<?>> {

    private final StatService statService;

    @GetMapping("")
    public String statP(@RequestParam("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate time = LocalDate.parse(date, formatter);
        log.info("stat 요청 : {}", time);

        DayOfWeek dayOfWeek = time.getDayOfWeek();
        log.info("요일 : {}", dayOfWeek);

        int dayOfMonth = time.getDayOfMonth();
        log.info("일 : {}", dayOfMonth);

        int dayOfYear = time.getDayOfYear();
        log.info("년의 몇 번째 날 : {}", dayOfYear);

        // 1년 중 몇 번째 주인지
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfMonth = time.get(weekFields.weekOfMonth());
        int month = time.getMonthValue();
        log.info("월 : {}", month);
        log.info("월의 몇 번째 주 : {}", weekOfMonth);

        return "stat";
    }

    @GetMapping("/daily")
    public ResponseEntity<BaseApiDto<?>> todayStat(@RequestParam("date") String date) {
        try {
            TodayStatDto todayStatDto = statService.getTodayStat(date);
            return super.ok(new BaseApiDto<>(todayStatDto));
        } catch (Exception e) {
            e.printStackTrace();
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "오늘의 통계 조회 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/weekly")
    public ResponseEntity<BaseApiDto<?>> weeklyStat(@RequestParam("date") String date) {
        try {
            WeeklyStatDto weeklyStat = statService.getWeeklyStat(date);
            return super.ok(new BaseApiDto<>(weeklyStat));
        } catch (Exception e) {
            e.printStackTrace();
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "주간 통계 조회 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/monthly")
    public ResponseEntity<BaseApiDto<?>> monthlyStat(@RequestParam("date") String date) {
        try {
            MonthlyStatDto weeklyStat = statService.getMonthlyStat(date);
            return super.ok(new BaseApiDto<>(weeklyStat));
        } catch (Exception e) {
            e.printStackTrace();
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "월간 통계 조회 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/calendar")
    public ResponseEntity<BaseApiDto<?>> calendarStat(@RequestParam("month") int month) {
        try {
            MonthlyNegativeRatioDto dto = statService.getMonthlyNegativeExp(month);
            return super.ok(new BaseApiDto<>(dto));
        } catch (Exception e) {
            e.printStackTrace();
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "월간 통계 조회 실패 : " + e.getMessage());
        }
    }

    @PostMapping("/random")
    public ResponseEntity<BaseApiDto<?>> randomStat(@RequestParam("month") String month) {
        try {
            statService.insertRandomStat(month);
            return super.ok(BaseApiDto.newBaseApiDto());
        } catch (Exception e) {
            e.printStackTrace();
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "오늘의 통계 조회 실패 : " + e.getMessage());
        }
    }

}
