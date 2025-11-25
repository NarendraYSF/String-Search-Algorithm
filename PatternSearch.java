import java.util.ArrayList;
import java.util.List;

public class PatternSearch {
    
    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            if (text.substring(i, i + pattern.length()).equals(pattern)) {
                matches.add(i);
            }
        }
        return matches;
    }
    
    public static void main(String[] args) {
        String text = "Hello World";
        String pattern = "World";
        
        List<Integer> result = search(text, pattern);
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        System.out.println("Found at: " + result);
        
        // More examples
        System.out.println("\n'hello' in 'hello world hello': " + search("hello world hello", "hello"));
        System.out.println("'aaa' in 'aaaaaaaa': " + search("aaaaaaaa", "aaa"));
        System.out.println("'xyz' in 'abcdefgh': " + search("abcdefgh", "xyz"));
    }
}

