"""
String Pattern Search Algorithm - Educational Implementation
============================================================
This module demonstrates how computers search for patterns in text
using the naive/brute-force approach.
"""


def find_pattern(text, pattern, verbose=True):
    """
    Find all occurrences of a pattern in text using the naive approach.
    
    Args:
        text (str): The text to search in
        pattern (str): The pattern to search for
        verbose (bool): If True, prints the search process
    
    Returns:
        list: All positions (indices) where the pattern is found
    """
    # List to store all positions where we find the pattern
    matches = []
    
    # STEP 1: Handle edge cases first
    if len(pattern) == 0:
        if verbose:
            print("⚠️  Pattern is empty!")
        return matches
    
    if len(text) == 0:
        if verbose:
            print("⚠️  Text is empty!")
        return matches
    
    if len(pattern) > len(text):
        if verbose:
            print(f"⚠️  Pattern (length {len(pattern)}) is longer than text (length {len(text)})")
        return matches
    
    # STEP 2: Calculate how many positions we need to check
    # We stop when there's not enough text left to fit the pattern
    search_limit = len(text) - len(pattern) + 1
    
    if verbose:
        print(f"\n🔍 Searching for '{pattern}' in '{text}'")
        print(f"📏 Text length: {len(text)}, Pattern length: {len(pattern)}")
        print(f"📍 Will check positions 0 to {search_limit - 1}\n")
        print("-" * 60)
    
    # STEP 3: Outer loop - slide through each position in the text
    for text_position in range(search_limit):
        
        if verbose:
            print(f"\n📍 Position {text_position}:")
        
        # Assume we have a match until proven otherwise
        is_match = True
        
        # STEP 4: Inner loop - compare pattern characters with text
        for pattern_position in range(len(pattern)):
            
            # Calculate where we are in the text
            current_text_index = text_position + pattern_position
            
            # Get the characters we're comparing
            text_char = text[current_text_index]
            pattern_char = pattern[pattern_position]
            
            if verbose:
                # Visual comparison
                match_symbol = "✓" if text_char == pattern_char else "✗"
                print(f"   Comparing: text[{current_text_index}]='{text_char}' with pattern[{pattern_position}]='{pattern_char}' {match_symbol}")
            
            # Check if characters match
            if text_char != pattern_char:
                is_match = False
                if verbose:
                    print(f"   ❌ No match at position {text_position}")
                break  # No need to check further characters
        
        # STEP 5: If all characters matched, record this position
        if is_match:
            matches.append(text_position)
            if verbose:
                # Show the match visually
                print(f"   ✅ MATCH FOUND at position {text_position}!")
                print(f"   {text}")
                print(f"   {' ' * text_position}{'^' * len(pattern)}")
    
    if verbose:
        print("-" * 60)
        if matches:
            print(f"\n🎉 Found {len(matches)} match(es) at position(s): {matches}")
        else:
            print(f"\n❌ Pattern '{pattern}' not found in '{text}'")
    
    return matches


def find_pattern_simple(text, pattern):
    """
    Simplified version without verbose output.
    Returns list of all positions where pattern is found.
    """
    return find_pattern(text, pattern, verbose=False)


def find_first_occurrence(text, pattern):
    """
    Find only the first occurrence of the pattern.
    
    Returns:
        int: Position of first match, or -1 if not found
    """
    matches = find_pattern(text, pattern, verbose=False)
    return matches[0] if matches else -1


def count_occurrences(text, pattern):
    """
    Count how many times the pattern appears in the text.
    
    Returns:
        int: Number of times pattern appears
    """
    matches = find_pattern(text, pattern, verbose=False)
    return len(matches)


# ============================================================
# BONUS FEATURES
# ============================================================

def find_pattern_case_insensitive(text, pattern, verbose=True):
    """
    Search for pattern ignoring case differences.
    """
    if verbose:
        print(f"🔤 Case-insensitive search mode")
    return find_pattern(text.lower(), pattern.lower(), verbose=verbose)


def find_last_occurrence(text, pattern):
    """
    Find the last occurrence of the pattern.
    
    Returns:
        int: Position of last match, or -1 if not found
    """
    matches = find_pattern(text, pattern, verbose=False)
    return matches[-1] if matches else -1


# ============================================================
# TEST CASES AND EXAMPLES
# ============================================================

def run_all_tests():
    """
    Run all test cases to demonstrate the algorithm.
    """
    print("=" * 60)
    print("STRING PATTERN SEARCH - TEST SUITE")
    print("=" * 60)
    
    # Test 1: Basic successful search
    print("\n\n🧪 TEST 1: Basic Search")
    print("=" * 60)
    text = "hello world"
    pattern = "wor"
    result = find_pattern(text, pattern)
    
    # Test 2: Multiple overlapping matches
    print("\n\n🧪 TEST 2: Overlapping Matches")
    print("=" * 60)
    text = "banana"
    pattern = "ana"
    result = find_pattern(text, pattern)
    
    # Test 3: Pattern not found
    print("\n\n🧪 TEST 3: Pattern Not Found")
    print("=" * 60)
    text = "hello"
    pattern = "world"
    result = find_pattern(text, pattern)
    
    # Test 4: Overlapping repeated characters
    print("\n\n🧪 TEST 4: Repeated Characters")
    print("=" * 60)
    text = "aaaa"
    pattern = "aa"
    result = find_pattern(text, pattern)
    
    # Test 5: Empty text
    print("\n\n🧪 TEST 5: Empty Text")
    print("=" * 60)
    text = ""
    pattern = "test"
    result = find_pattern(text, pattern)
    
    # Test 6: Pattern longer than text
    print("\n\n🧪 TEST 6: Pattern Longer Than Text")
    print("=" * 60)
    text = "hi"
    pattern = "hello"
    result = find_pattern(text, pattern)
    
    # Test 7: Single character match
    print("\n\n🧪 TEST 7: Single Character")
    print("=" * 60)
    text = "abcabc"
    pattern = "a"
    result = find_pattern(text, pattern)
    
    # Test 8: Exact match (pattern equals text)
    print("\n\n🧪 TEST 8: Exact Match")
    print("=" * 60)
    text = "hello"
    pattern = "hello"
    result = find_pattern(text, pattern)
    
    # Bonus Tests
    print("\n\n" + "=" * 60)
    print("BONUS FEATURES")
    print("=" * 60)
    
    # Test 9: Case-insensitive search
    print("\n\n🧪 TEST 9: Case-Insensitive Search")
    print("=" * 60)
    text = "Hello World"
    pattern = "world"
    result = find_pattern_case_insensitive(text, pattern)
    
    # Test 10: Quick summary tests without verbose
    print("\n\n🧪 TEST 10: Quick Summary Tests")
    print("=" * 60)
    test_cases = [
        ("programming", "gram"),
        ("Python is awesome", "is"),
        ("Mississippi", "issi"),
        ("test", "testing"),
    ]
    
    for text, pattern in test_cases:
        matches = find_pattern_simple(text, pattern)
        first = find_first_occurrence(text, pattern)
        count = count_occurrences(text, pattern)
        print(f"\nText: '{text}' | Pattern: '{pattern}'")
        print(f"  Positions: {matches}")
        print(f"  First at: {first}, Count: {count}")


def interactive_search():
    """
    Interactive mode for user to test their own strings.
    """
    print("\n\n" + "=" * 60)
    print("INTERACTIVE PATTERN SEARCH")
    print("=" * 60)
    print("Try your own examples! (or press Enter to skip)")
    
    text = input("\nEnter text to search in: ").strip()
    if text:
        pattern = input("Enter pattern to search for: ").strip()
        if pattern:
            print("\n")
            result = find_pattern(text, pattern)
            
            # Show additional info
            print(f"\n📊 Summary:")
            print(f"   First occurrence: {result[0] if result else 'Not found'}")
            print(f"   Last occurrence: {result[-1] if result else 'Not found'}")
            print(f"   Total count: {len(result)}")


# ============================================================
# EDUCATIONAL EXPLANATION
# ============================================================

def explain_algorithm():
    """
    Print an explanation of how the algorithm works.
    """
    explanation = """
    
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

    """
    print(explanation)


# ============================================================
# MAIN PROGRAM
# ============================================================

if __name__ == "__main__":
    # Show the explanation first
    explain_algorithm()
    
    # Run all automated tests
    run_all_tests()
    
    # Optional: Interactive mode
    interactive_search()
    
    print("\n\n" + "=" * 60)
    print("✅ Pattern Search Tutorial Complete!")
    print("=" * 60)
    print("\n💡 Next Steps:")
    print("   - Try modifying the code to skip positions intelligently")
    print("   - Research more efficient algorithms (KMP, Boyer-Moore)")
    print("   - Use this as a base for understanding regular expressions")
    print("\n")

