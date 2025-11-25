def search(text, pattern):
    """Find all occurrences of pattern in text"""
    matches = []
    for i in range(len(text) - len(pattern) + 1):
        if text[i:i + len(pattern)] == pattern:
            matches.append(i)
    return matches

if __name__ == "__main__":
    text = "Hello World"
    pattern = "World"
    
    result = search(text, pattern)
    print(f"Text: {text}")
    print(f"Pattern: {pattern}")
    print(f"Found at: {result}")
    
    # More examples
    print(f"\n'hello' in 'hello world hello': {search('hello world hello', 'hello')}")
    print(f"'aaa' in 'aaaaaaaa': {search('aaaaaaaa', 'aaa')}")
    print(f"'xyz' in 'abcdefgh': {search('abcdefgh', 'xyz')}")
