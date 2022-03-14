import zsy.jason.exception.SyntaxException;
import zsy.jason.util.JasonParser;

import java.io.BufferedReader;
import java.io.FileReader;

public class TestJasonParser {
    public static void main(String[] args) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\CODE\\IdeaProjects\\my_jason\\test.json"));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            JasonParser parser = new JasonParser(content.toString());
            try {
                var root = parser.parse();
                System.out.println(root);
            } catch (SyntaxException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }

    }
}
