package taba.team4.eut.biz.stat.repository;

public interface AvgStatInterface {
    Double getHappinessScore();
    Double getPanicScore();
    Double getNeutralScore();
    Double getAnxietyScore();
    Double getAngerScore();
    Double getSadnessScore();
    Double getDisgustScore();

    Long getUsageTimeSecond();
}
