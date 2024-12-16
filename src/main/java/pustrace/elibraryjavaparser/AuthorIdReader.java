package pustrace.elibraryjavaparser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс для чтения идентификаторов авторов из файла.
 */
public class AuthorIdReader {

    /**
     * Читает список идентификаторов авторов из указанного файла.
     *
     * @param filePath Путь к файлу, содержащему идентификаторы авторов.
     * @return Список строк, представляющих идентификаторы авторов.
     * @throws IOException Если произошла ошибка при чтении файла.
     */
    public List<String> readAuthorIds(String filePath) throws IOException {
        List<String> authorIds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                authorIds.add(line.trim());
            }
        }
        return authorIds;
    }
}
