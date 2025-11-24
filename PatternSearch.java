/**
 * String Pattern Search Algorithm - Educational Implementation
 * ============================================================
 * This program demonstrates how computers search for patterns in text
 * using the naive/brute-force approach.
 * 
 * @author Learning Tool
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatternSearch {
    
    // ANSI color codes for colorful terminal output
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    
    /**
     * Find all occurrences of a pattern in text using the naive approach.
     * 
     * @param text The text to search in
     * @param pattern The pattern to search for
     * @param verbose If true, prints the search process
     * @return List of all positions (indices) where the pattern is found
     */
    public static List<Integer> findPattern(String text, String pattern, boolean verbose) {
        // List to store all positions where we find the pattern
        List<Integer> matches = new ArrayList<>();
        
        // STEP 1: Handle edge cases first
        if (pattern.isEmpty()) {
            if (verbose) {
                System.out.println(YELLOW + "⚠️  Pattern is empty!" + RESET);
            }
            return matches;
        }
        
        if (text.isEmpty()) {
            if (verbose) {
                System.out.println(YELLOW + "⚠️  Text is empty!" + RESET);
            }
            return matches;
        }
        
        if (pattern.length() > text.length()) {
            if (verbose) {
                System.out.printf(YELLOW + "⚠️  Pattern (length %d) is longer than text (length %d)%n" + RESET,
                    pattern.length(), text.length());
            }
            return matches;
        }
        
        // STEP 2: Calculate how many positions we need to check
        // We stop when there's not enough text left to fit the pattern
        int searchLimit = text.length() - pattern.length() + 1;
        
        if (verbose) {
            System.out.println();
            System.out.println(CYAN + BOLD + "🔍 Searching for '" + pattern + "' in '" + text + "'" + RESET);
            System.out.printf(BLUE + "📏 Text length: %d, Pattern length: %d%n" + RESET,
                text.length(), pattern.length());
            System.out.printf(BLUE + "📍 Will check positions 0 to %d%n" + RESET, searchLimit - 1);
            System.out.println();
            System.out.println("------------------------------------------------------------");
        }
        
        // STEP 3: Outer loop - slide through each position in the text
        for (int textPosition = 0; textPosition < searchLimit; textPosition++) {
            
            if (verbose) {
                System.out.println();
                System.out.println(PURPLE + BOLD + "📍 Position " + textPosition + ":" + RESET);
            }
            
            // Assume we have a match until proven otherwise
            boolean isMatch = true;
            
            // STEP 4: Inner loop - compare pattern characters with text
            for (int patternPosition = 0; patternPosition < pattern.length(); patternPosition++) {
                
                // Calculate where we are in the text
                int currentTextIndex = textPosition + patternPosition;
                
                // Get the characters we're comparing
                char textChar = text.charAt(currentTextIndex);
                char patternChar = pattern.charAt(patternPosition);
                
                if (verbose) {
                    // Visual comparison
                    String matchSymbol = (textChar == patternChar) ? GREEN + "✓" : RED + "✗";
                    System.out.printf("   Comparing: text[%d]='%c' with pattern[%d]='%c' %s%s%n",
                        currentTextIndex, textChar, patternPosition, patternChar, matchSymbol, RESET);
                }
                
                // Check if characters match
                if (textChar != patternChar) {
                    isMatch = false;
                    if (verbose) {
                        System.out.println(RED + "   ❌ No match at position " + textPosition + RESET);
                    }
                    break; // No need to check further characters
                }
            }
            
            // STEP 5: If all characters matched, record this position
            if (isMatch) {
                matches.add(textPosition);
                if (verbose) {
                    // Show the match visually
                    System.out.println(GREEN + BOLD + "   ✅ MATCH FOUND at position " + textPosition + "!" + RESET);
                    System.out.println("   " + text);
                    System.out.println("   " + " ".repeat(textPosition) + "^".repeat(pattern.length()));
                }
            }
        }
        
        if (verbose) {
            System.out.println("------------------------------------------------------------");
            if (!matches.isEmpty()) {
                System.out.println(GREEN + BOLD + "\n🎉 Found " + matches.size() + 
                    " match(es) at position(s): " + matches + RESET);
            } else {
                System.out.println(RED + BOLD + "\n❌ Pattern '" + pattern + 
                    "' not found in '" + text + "'" + RESET);
            }
        }
        
        return matches;
    }
    
    /**
     * Simplified version without verbose output.
     * Returns list of all positions where pattern is found.
     */
    public static List<Integer> findPatternSimple(String text, String pattern) {
        return findPattern(text, pattern, false);
    }
    
    /**
     * Find only the first occurrence of the pattern.
     * 
     * @return Position of first match, or -1 if not found
     */
    public static int findFirstOccurrence(String text, String pattern) {
        List<Integer> matches = findPattern(text, pattern, false);
        return matches.isEmpty() ? -1 : matches.get(0);
    }
    
    /**
     * Find the last occurrence of the pattern.
     * 
     * @return Position of last match, or -1 if not found
     */
    public static int findLastOccurrence(String text, String pattern) {
        List<Integer> matches = findPattern(text, pattern, false);
        return matches.isEmpty() ? -1 : matches.get(matches.size() - 1);
    }
    
    /**
     * Count how many times the pattern appears in the text.
     * 
     * @return Number of times pattern appears
     */
    public static int countOccurrences(String text, String pattern) {
        return findPattern(text, pattern, false).size();
    }
    
    // ============================================================
    // BONUS FEATURES
    // ============================================================
    
    /**
     * Search for pattern ignoring case differences.
     */
    public static List<Integer> findPatternCaseInsensitive(String text, String pattern, boolean verbose) {
        if (verbose) {
            System.out.println(CYAN + "🔤 Case-insensitive search mode" + RESET);
        }
        return findPattern(text.toLowerCase(), pattern.toLowerCase(), verbose);
    }
    
    // ============================================================
    // TEST CASES AND EXAMPLES
    // ============================================================
    
    /**
     * Run all test cases to demonstrate the algorithm.
     */
    public static void runAllTests() {
        System.out.println("============================================================");
        System.out.println(BOLD + "STRING PATTERN SEARCH - TEST SUITE" + RESET);
        System.out.println("============================================================");
        
        // Test 1: Basic successful search
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 1: Basic Search" + RESET);
        System.out.println("============================================================");
        findPattern("hello world", "wor", true);
        
        // Test 2: Multiple overlapping matches
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 2: Overlapping Matches" + RESET);
        System.out.println("============================================================");
        findPattern("banana", "ana", true);
        
        // Test 3: Pattern not found
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 3: Pattern Not Found" + RESET);
        System.out.println("============================================================");
        findPattern("hello", "world", true);
        
        // Test 4: Overlapping repeated characters
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 4: Repeated Characters" + RESET);
        System.out.println("============================================================");
        findPattern("aaaa", "aa", true);
        
        // Test 5: Empty text
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 5: Empty Text" + RESET);
        System.out.println("============================================================");
        findPattern("", "test", true);
        
        // Test 6: Pattern longer than text
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 6: Pattern Longer Than Text" + RESET);
        System.out.println("============================================================");
        findPattern("hi", "hello", true);
        
        // Test 7: Single character match
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 7: Single Character" + RESET);
        System.out.println("============================================================");
        findPattern("abcabc", "a", true);
        
        // Test 8: Exact match (pattern equals text)
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 8: Exact Match" + RESET);
        System.out.println("============================================================");
        findPattern("hello", "hello", true);
        
        // Bonus Tests
        System.out.println("\n\n============================================================");
        System.out.println(BOLD + "BONUS FEATURES" + RESET);
        System.out.println("============================================================");
        
        // Test 9: Case-insensitive search
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 9: Case-Insensitive Search" + RESET);
        System.out.println("============================================================");
        findPatternCaseInsensitive("Hello World", "world", true);
        
        // Test 10: Quick summary tests without verbose
        System.out.println("\n\n" + CYAN + BOLD + "🧪 TEST 10: Quick Summary Tests" + RESET);
        System.out.println("============================================================");
        
        String[][] testCases = {
            {"programming", "gram"},
            {"Python is awesome", "is"},
            {"Mississippi", "issi"},
            {"test", "testing"}
        };
        
        for (String[] testCase : testCases) {
            String text = testCase[0];
            String pattern = testCase[1];
            List<Integer> matches = findPatternSimple(text, pattern);
            int first = findFirstOccurrence(text, pattern);
            int count = countOccurrences(text, pattern);
            
            System.out.printf("%nText: '%s' | Pattern: '%s'%n", text, pattern);
            System.out.printf("  Positions: %s%n", matches);
            System.out.printf("  First at: %d, Count: %d%n", first, count);
        }
    }
    
    /**
     * Interactive mode for user to test their own strings.
     */
    public static void interactiveSearch() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n\n============================================================");
        System.out.println(BOLD + "INTERACTIVE PATTERN SEARCH" + RESET);
        System.out.println("============================================================");
        System.out.println("Try your own examples! (or press Enter to skip)");
        
        System.out.print("\nEnter text to search in: ");
        String text = scanner.nextLine().trim();
        
        if (!text.isEmpty()) {
            System.out.print("Enter pattern to search for: ");
            String pattern = scanner.nextLine().trim();
            
            if (!pattern.isEmpty()) {
                System.out.println();
                List<Integer> result = findPattern(text, pattern, true);
                
                // Show additional info
                System.out.println("\n📊 Summary:");
                if (!result.isEmpty()) {
                    System.out.println("   First occurrence: " + result.get(0));
                    System.out.println("   Last occurrence: " + result.get(result.size() - 1));
                    System.out.println("   Total count: " + result.size());
                } else {
                    System.out.println("   First occurrence: Not found");
                    System.out.println("   Last occurrence: Not found");
                    System.out.println("   Total count: 0");
                }
            }
        }
    }
    
    // ============================================================
    // EDUCATIONAL EXPLANATION
    // ============================================================
    
    /**
     * Print an explanation of how the algorithm works.
     */
    public static void explainAlgorithm() {
        String explanation = """
            
╔══════════════════════════════════════════════════════════════╗
║         HOW THE PATTERN SEARCH ALGORITHM WORKS              ║
╚══════════════════════════════════════════════════════════════╝

🎯 THE MAIN IDEA:
   Slide the pattern across the text, one position at a time,
   and check if all characters match at each position.

📝 THE STEPS:

1. START at position 0 of the text
   
2. COMPARE the pattern with the text at current position:
   - Check each character of the pattern
   - If all match → Record this position!
   - If any don't match → Move to next position
   
3. SLIDE to the next position (move one character right)
   
4. REPEAT steps 2-3 until we've checked all possible positions
   
5. RETURN all positions where we found matches

⚡ SLIDING WINDOW CONCEPT:
   Imagine the pattern as a window that slides across the text:
   
   Text:    b a n a n a
   Pattern: a n a
   
   Position 0: [b a n] a n a  → No match (b ≠ a)
   Position 1:  b [a n a] n a → Match! ✓
   Position 2:  b a [n a n] a → No match (n ≠ a)
   Position 3:  b a n [a n a] → Match! ✓

⏱️  TIME COMPLEXITY:
   - For each position (n positions): O(n)
   - Check each pattern character (m characters): O(m)
   - Total: O(n × m) - might be slow for very large texts
   
   Where n = length of text, m = length of pattern

💡 WHY IT'S CALLED "NAIVE" OR "BRUTE FORCE":
   Because it checks EVERY possible position, even when we could
   potentially skip some positions using smarter algorithms
   (like KMP, Boyer-Moore, or Rabin-Karp).
   
   But it's perfect for learning because it's simple and intuitive!

🎓 KEY CONCEPTS YOU'VE LEARNED:
   ✓ String indexing and character comparison
   ✓ Nested loops for multi-level checking
   ✓ Sliding window technique
   ✓ Edge case handling
   ✓ Algorithm complexity basics

        """;
        
        System.out.println(CYAN + explanation + RESET);
    }
    
    // ============================================================
    // MAIN PROGRAM
    // ============================================================
    
    /**
     * Main method to run the program.
     */
    public static void main(String[] args) {
        // Show the explanation first
        explainAlgorithm();
        
        // Run all automated tests
        runAllTests();
        
        // Optional: Interactive mode
        interactiveSearch();
        
        System.out.println("\n\n============================================================");
        System.out.println(GREEN + BOLD + "✅ Pattern Search Tutorial Complete!" + RESET);
        System.out.println("============================================================");
        System.out.println("\n💡 Next Steps:");
        System.out.println("   - Try modifying the code to skip positions intelligently");
        System.out.println("   - Research more efficient algorithms (KMP, Boyer-Moore)");
        System.out.println("   - Use this as a base for understanding regular expressions");
        System.out.println();
    }
}

