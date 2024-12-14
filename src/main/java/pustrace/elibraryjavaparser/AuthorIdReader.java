package pustrace.elibraryjavaparser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class AuthorIdReader {
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
