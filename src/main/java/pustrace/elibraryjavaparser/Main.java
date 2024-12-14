package pustrace.elibraryjavaparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// логгер
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// тесты
import java.lang.Thread;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Программа запущена");
        String inputFilePath = "C:\\PusTrace\\programming\\java\\ElibraryJavaParser\\terminal\\import\\author.txt"; // Путь к файлу с authorId
        String outputFilePath = "C:\\PusTrace\\programming\\java\\ElibraryJavaParser\\terminal\\export\\statistics.json"; // Путь к выходному JSON-файлу

        AuthorIdReader reader = new AuthorIdReader();
        ElibraryFetcher fetcher = new ElibraryFetcher();
        fetcher.init();

        try {
            List<String> authorIds = reader.readAuthorIds(inputFilePath);
            List<AuthorStatistics> statisticsList = new ArrayList<>();


            for (String authorId : authorIds) {
                logger.info("Обработка автора с ID: {}", authorId);
                AuthorStatistics stats = fetcher.fetchAuthorStatistics(authorId);
                statisticsList.add(stats);
                // Задержка между обработкой авторов
                try {
                    Thread.sleep(3000); // 3000 мс = 3 секунды
                } catch (InterruptedException e) {
                    logger.warn("Поток был прерван", e);
                    Thread.currentThread().interrupt(); // Восстанавливаем состояние прерывания
                }
            }

            saveToJson(outputFilePath, statisticsList);
            System.out.println("Статистика сохранена в " + outputFilePath);
            logger.info("Статистика успешно сохранена в {}", outputFilePath);
        } catch (IOException e) {
            logger.error("Ошибка во время выполнения программы", e);
        }
    }

    private static void saveToJson(String filePath, List<AuthorStatistics> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения JSON: " + e.getMessage());
        }
    }
}
