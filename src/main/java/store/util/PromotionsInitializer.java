package store.util;

import store.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PromotionsInitializer {
    
    private static final int NAME = 0;
    
    private static final int CONDITION = 1;
    
    private static final int START_DATE = 3;
    
    private static final int END_DATE = 4;
    
    public Promotions get() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Path path = Path.of("src/main/resources/promotions.md");
        Map<String, Promotion> promotions = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(path, UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                String[] line = lines.get(i).split(",");
                String name = line[NAME];
                int condition = Integer.parseInt(line[CONDITION]);
                LocalDate startDate = LocalDate.parse(line[START_DATE], formatter);
                LocalDate endDate = LocalDate.parse(line[END_DATE], formatter);
                Promotion promotion = new Promotion(name, startDate, endDate, condition);
                promotions.put(name, promotion);
            }
        } catch (IOException e) {
            System.out.println(Constants.EXCEPTION_MESSAGE_PREFIX +
                    "잘못된 파일입니다.");
        }
        return new Promotions(promotions);
    }
    
}
