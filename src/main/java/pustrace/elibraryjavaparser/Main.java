package pustrace.elibraryjavaparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Программа запущена");

        // Путь к входному и выходному файлам передаем из args или задаем вручную
        String inputFilePath = args.length > 0 ? args[0] : "C:\\path\\to\\author.txt";
        String outputFilePath = args.length > 1 ? args[1] : "C:\\path\\to\\statistics.json";

        processAuthors(inputFilePath, outputFilePath, 5000, 3000);
    }

    public static void processAuthors(String inputFilePath, String outputFilePath, int captchaDelay, int pageDelay) {
        // Проверяем, что файлы существуют
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            System.out.println("Входной файл не существует.");
            return;
        }
        AuthorIdReader reader = new AuthorIdReader();

        // Создаем объект ElibraryFetcher с заданными задержками
        ElibraryFetcher fetcher = new ElibraryFetcher(captchaDelay, pageDelay);

        // Инициализируем WebDriver и получаем куки
        fetcher.init();

        try {
            List<String> authorIds = reader.readAuthorIds(inputFilePath);

            // Используем Stream API для обработки авторов
            List<AuthorStatistics> statisticsList = authorIds.stream()
                    .map(authorId -> {
                        logger.info("Обработка автора с ID: {}", authorId);
                        AuthorStatistics stats = fetcher.fetchAuthorStatistics(authorId);  // Передаем задержку
                        return stats;
                    })
                    .collect(Collectors.toList());

            saveToJson(outputFilePath, statisticsList);
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
            logger.error("Ошибка сохранения JSON: {}", e.getMessage());
        }
    }
}
