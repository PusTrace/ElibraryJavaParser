import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import pustrace.elibraryjavaparser.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MainTest {

    @Test
    public void testProcessAuthors_FileNotExists() {
        // Мокируем File
        File inputFile = mock(File.class);
        when(inputFile.exists()).thenReturn(false);

        // Перехватываем вывод в консоль
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Проверяем, что метод вернет сообщение о несуществующем файле
        Main.processAuthors("nonexistent_path.txt", "output.json", 5000, 3000);

        // Проверка содержимого вывода
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Входной файл не существует"));
    }


    @Test
    public void testProcessAuthors_SuccessfulProcessing() throws IOException {
        // Мокируем зависимости
        AuthorIdReader reader = mock(AuthorIdReader.class);
        ElibraryFetcher fetcher = mock(ElibraryFetcher.class);
        FileWriter fileWriter = mock(FileWriter.class);

        // Мокируем вывод авторов
        List<String> authorIds = Arrays.asList("author1", "author2");
        when(reader.readAuthorIds("input.txt")).thenReturn(authorIds);

        // Мокируем возврат статистики для авторов
        AuthorStatistics stats1 = mock(AuthorStatistics.class);
        AuthorStatistics stats2 = mock(AuthorStatistics.class);
        when(fetcher.fetchAuthorStatistics("author1")).thenReturn(stats1);
        when(fetcher.fetchAuthorStatistics("author2")).thenReturn(stats2);

        // Мокируем сохранение JSON
        doNothing().when(fileWriter).write(anyString());

        // Тестируем метод
        Main.processAuthors("input.txt", "output.json", 5000, 3000);

        // Проверяем, что были вызваны методы для получения статистики и записи в файл
        verify(fetcher).fetchAuthorStatistics("author1");
        verify(fetcher).fetchAuthorStatistics("author2");

        // Проверяем, что JSON сохранен в файл
        verify(fileWriter, times(1)).write(anyString());
    }


    @Test
    public void testSaveToJson() throws IOException {
        // Делаем мок для FileWriter
        FileWriter mockWriter = mock(FileWriter.class);
        List<AuthorStatistics> statistics = Arrays.asList(new AuthorStatistics());

        // Вызываем метод сохранения
        Main.saveToJson("output.json", statistics);

        // Проверяем, что метод write был вызван
        verify(mockWriter).write(anyString());

        // Можно добавить дополнительную проверку содержимого файла (например, сериализовать обратно и проверить)
        String jsonOutput = new String(mockWriter.toString().getBytes(), StandardCharsets.UTF_8);
        AuthorStatistics[] statsArray = new Gson().fromJson(jsonOutput, AuthorStatistics[].class);
        assertEquals(statistics.size(), statsArray.length);
    }


    @Test
    public void testMain_InvalidFilePath() {
        // Мокируем все внешние зависимости
        AuthorIdReader mockReader = mock(AuthorIdReader.class);
        ElibraryFetcher mockFetcher = mock(ElibraryFetcher.class);

        // Проверяем, что программа корректно работает, когда входной файл не существует
        Main.processAuthors("invalid_path.txt", "output.json", 5000, 3000);

        // Проверяем, что программа не вызывает fetcher, если файл не существует
        verify(mockFetcher, never()).fetchAuthorStatistics(anyString());
    }
}
