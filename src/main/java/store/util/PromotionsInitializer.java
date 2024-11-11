package store.util;

import store.model.discount.Promotion;
import store.model.inventory.Promotions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 파일로부터 프로모션 정보를 불러와서 재고 프로모션 객체(Promotions)를 초기화하는 클래스
 * 파일이 잘못되었으면 IOException를 호출한다.
 */
public class PromotionsInitializer {
    
    public static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    
    private static final int NAME = 0;
    
    private static final int CONDITION = 1;
    
    private static final int START_DATE = 3;
    
    private static final int END_DATE = 4;
    
    public Promotions get(String pathLine) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Path path = Path.of(pathLine);
        Map<String, Promotion> promotions = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(path, UTF_8);
            putPromotion(lines, formatter, promotions);
        } catch (IOException e) {
            System.out.println(ExceptionMessage.WRONG_FILE);
        }
        return new Promotions(promotions);
    }

    private static void putPromotion(List<String> lines, DateTimeFormatter formatter,
                                     Map<String, Promotion> promotions) {
        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i).split(",");
            String name = line[NAME];
            int condition = Integer.parseInt(line[CONDITION]);
            LocalDate startDate = LocalDate.parse(line[START_DATE], formatter);
            LocalDate endDate = LocalDate.parse(line[END_DATE], formatter);
            Promotion promotion = new Promotion(name, startDate, endDate, condition);
            promotions.put(name, promotion);
        }
    }

}
