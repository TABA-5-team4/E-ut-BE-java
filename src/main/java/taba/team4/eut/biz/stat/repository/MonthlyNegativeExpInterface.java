package taba.team4.eut.biz.stat.repository;

import java.time.LocalDate;
import java.util.Date;

public interface MonthlyNegativeExpInterface {
    LocalDate getStatDate();
    Double getAvgNegativeExpRate();
}
