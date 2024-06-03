package taba.team4.eut.biz.stat.utils;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class Gara {
    private List<String> summaryList = Arrays.asList(
            "점심 메뉴를 고민 중이다.",
            "어제 본 영화가 재미있었다.",
            "주말에 산책하며 사진을 찍었다.",
            "새 옷이 마음에 든다.",
            "요즘 일이 많아 바쁘다.",
            "날씨가 좋다.",
            "어제 친구와 저녁을 먹었다.",
            "운동을 시작해서 몸이 아프다.",
            "주말에 여행 계획이 있다.",
            "새 드라마가 흥미진진하다.",
            "회사 회의가 길었다.",
            "커피를 마시며 책을 읽는다.",
            "요리를 배우고 있다.",
            "밤새 일해서 피곤하다.",
            "새 집이 마음에 든다.",
            "운동 후 사우나에 갔다.",
            "저녁 메뉴를 고민 중이다.",
            "친구 생일 선물을 고민 중이다.",
            "아침에 늦잠을 잤다.",
            "주말에 가족과 바비큐 파티를 했다.",
            "새로운 운동을 시작했다.",
            "새로운 취미를 찾았다.",
            "새로운 요리법을 시도했다.",
            "어제 밤에 잠을 잘 못 잤다.",
            "아침에 일찍 일어났다.",
            "친구와 영화관에 갔다.",
            "집안일을 끝냈다.",
            "주말에 집에서 쉬었다.",
            "오랜만에 친구를 만났다.",
            "점심에 새로운 식당을 찾았다.",
            "여름 휴가를 계획 중이다.",
            "새로운 책을 시작했다.",
            "도서관에서 책을 빌렸다.",
            "주말에 가족과 시간을 보냈다.",
            "새로운 전자제품을 구입했다.",
            "취미 생활을 즐겼다.",
            "운동 후에 몸이 개운하다.",
            "어제 늦게까지 일했다.",
            "오늘 아침 조깅을 했다.",
            "친구와 전화 통화를 했다.",
            "오늘 저녁에 외식을 했다.",
            "오늘 날씨가 흐렸다.",
            "주말에 캠핑을 갔다.",
            "새로운 가구를 샀다.",
            "오늘 아침에 일찍 일어났다.",
            "친구와 함께 공부를 했다.",
            "새로운 언어를 배우고 있다.",
            "어제 밤에 책을 읽었다.",
            "오늘 하루 종일 집에 있었다.",
            "새로운 음식을 시도했다.",
            "새로운 취미를 시작했다.",
            "오늘 아침에 운동을 했다.",
            "친구와 점심을 먹었다.",
            "새로운 신발을 샀다.",
            "오늘 하루 종일 바빴다.",
            "어제 밤에 영화를 봤다.",
            "오늘 아침에 일찍 일어났다.",
            "새로운 음악을 들었다.",
            "친구와 함께 산책을 했다.",
            "오늘 하루 종일 일했다.",
            "어제 밤에 일찍 잠들었다.",
            "오늘 아침에 커피를 마셨다.",
            "주말에 가족과 시간을 보냈다.",
            "새로운 직장을 구했다.",
            "새로운 프로젝트를 시작했다.",
            "오늘 아침에 일찍 일어났다.",
            "친구와 함께 저녁을 먹었다.",
            "새로운 취미를 찾았다.",
            "어제 밤에 잠을 잘 잤다.",
            "오늘 아침에 운동을 했다.",
            "친구와 함께 영화를 봤다.",
            "새로운 책을 시작했다.",
            "오늘 하루 종일 바빴다.",
            "어제 밤에 책을 읽었다.",
            "오늘 아침에 산책을 했다.",
            "친구와 점심을 먹었다.",
            "새로운 가방을 샀다.",
            "오늘 하루 종일 일했다.",
            "어제 밤에 늦게 잤다.",
            "오늘 아침에 일찍 일어났다.",
            "새로운 음식을 시도했다.",
            "새로운 음악을 들었다.",
            "친구와 함께 시간을 보냈다.",
            "오늘 하루 종일 바빴다.",
            "어제 밤에 영화를 봤다.",
            "오늘 아침에 일찍 일어났다.",
            "새로운 취미를 찾았다.",
            "어제 밤에 책을 읽었다.",
            "오늘 아침에 커피를 마셨다.",
            "주말에 가족과 시간을 보냈다.",
            "새로운 직장을 구했다.",
            "새로운 프로젝트를 시작했다.",
            "오늘 아침에 일찍 일어났다.",
            "친구와 함께 저녁을 먹었다.",
            "새로운 신발을 샀다."
    );

    public String getSummary() {
        return summaryList.get((int) (Math.random() * summaryList.size()));
    }

    // random value generate
    // list length is 7
    // sum of value of list is 1
    // values are random value
    public List<Double> getRandomStatValue() {
        double[] values = new double[7];
        double sum = 0;
        for (int i = 0; i < 7; i++) {
            values[i] = Math.random();
            sum += values[i];
        }
        for (int i = 0; i < 7; i++) {
            values[i] /= sum;
        }
        return Arrays.asList(values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
    }

    public int getNegativeExpRate(List<Double> list) {
        return (int) Math.floor((list.get(3) + list.get(4) + list.get(5) + list.get(6)) * 100);
    }


}