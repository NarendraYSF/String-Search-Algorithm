# 🔍 String Pattern Search Algorithm - Educational Tool

A comprehensive educational implementation of the naive string pattern matching algorithm, available in multiple programming languages and formats.

## 📚 What You'll Learn

- **How computers search for patterns in text**
- **Character-by-character comparison logic**
- **The sliding window technique**
- **Time complexity analysis (O(n×m))**
- **Edge case handling**
- **Algorithm visualization**

## 🎯 Available Versions

### 1. Python Terminal Version
**File:** `pattern_search.py`

A detailed terminal-based implementation with colorful output and comprehensive explanations.

**Features:**
- ✅ Step-by-step character comparisons
- ✅ Visual representation with ASCII art
- ✅ 10 automated test cases
- ✅ Interactive mode
- ✅ Educational explanations
- ✅ Bonus features (case-insensitive, count occurrences, etc.)

**Usage:**
```bash
python pattern_search.py
```

---

### 2. Python GUI Version
**File:** `pattern_search_gui.py`

Beautiful interactive GUI with animated visualizations using tkinter.

**Features:**
- ✅ Visual canvas with color-coded character boxes
- ✅ Real-time animated search process
- ✅ Adjustable animation speed (0.1x to 2.0x)
- ✅ Case-sensitive/insensitive toggle
- ✅ Pre-loaded example test cases
- ✅ Live output with syntax highlighting
- ✅ Status bar with progress updates

**Usage:**
```bash
python pattern_search_gui.py
```

**Requirements:**
- Python 3.x
- tkinter (usually included with Python)

---

### 3. Java Terminal Version
**File:** `PatternSearch.java`

A comprehensive Java implementation with ANSI color support for terminals.

**Features:**
- ✅ Object-oriented design
- ✅ Colorful terminal output
- ✅ Multiple helper methods
- ✅ 10 comprehensive test cases
- ✅ Interactive mode
- ✅ Case-insensitive search
- ✅ Modern Java features (text blocks, ArrayList, etc.)

**Usage:**
```bash
# Compile
javac PatternSearch.java

# Run
java PatternSearch
```

**Requirements:**
- Java 11 or higher (for text blocks)

---

### 4. JavaFX GUI Version
**File:** `PatternSearchGUI.java`

Professional GUI application with JavaFX featuring smooth animations and modern design.

**Features:**
- ✅ Modern GUI with JavaFX
- ✅ Canvas-based character visualization
- ✅ Color-coded boxes (green=match, red=no match, blue=checking)
- ✅ Background threading for smooth performance
- ✅ Adjustable animation speed slider
- ✅ Pre-loaded examples in popup window
- ✅ Status bar with live updates
- ✅ Start/Stop/Clear controls

**Setup Required:** See [SETUP_JAVAFX.md](SETUP_JAVAFX.md) for detailed installation instructions.

**Quick Start (if JavaFX is installed):**
```bash
# Compile
javac --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.graphics PatternSearchGUI.java

# Run
java --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.graphics PatternSearchGUI
```

**Requirements:**
- Java 11 or higher
- JavaFX SDK 11 or higher

---

## 🚀 Quick Start Guide

### Choose Your Version:

1. **Want quick results?** → Use Python Terminal Version (`pattern_search.py`)
2. **Want visual learning?** → Use Python GUI Version (`pattern_search_gui.py`)
3. **Learning Java?** → Use Java Terminal Version (`PatternSearch.java`)
4. **Want professional GUI?** → Use JavaFX GUI Version (needs setup)

### Example Usage:

All versions support searching for patterns like:

```
Text: "banana"
Pattern: "ana"
Result: Found at positions [1, 3] (overlapping matches!)
```

```
Text: "hello world"
Pattern: "wor"
Result: Found at position [6]
```

```
Text: "Mississippi"
Pattern: "issi"
Result: Found at positions [1, 4]
```

## 📖 How the Algorithm Works

### The Naive/Brute-Force Approach:

1. **Start** at position 0 of the text
2. **Compare** pattern with text at current position
   - Check each character one by one
   - If all match → Record this position!
   - If any don't match → Move to next position
3. **Slide** to the next position (move one character right)
4. **Repeat** until all positions are checked
5. **Return** all positions where matches were found

### Visual Example:

```
Text:    b a n a n a
Pattern: a n a

Position 0: [b a n] a n a  → No match (b ≠ a)
Position 1:  b [a n a] n a → Match! ✓
Position 2:  b a [n a n] a → No match (n ≠ a)
Position 3:  b a n [a n a] → Match! ✓

Result: Matches at positions [1, 3]
```

### Time Complexity:

- **Best Case:** O(n) - pattern doesn't match first character anywhere
- **Worst Case:** O(n × m) - pattern almost matches everywhere
- **Space Complexity:** O(1) - only stores match positions

Where:
- n = length of text
- m = length of pattern

## 🎓 Educational Features

### Test Cases Included:

1. ✅ Basic successful search
2. ✅ Overlapping matches
3. ✅ Pattern not found
4. ✅ Repeated characters
5. ✅ Empty text/pattern
6. ✅ Pattern longer than text
7. ✅ Single character search
8. ✅ Exact match
9. ✅ Case-insensitive search
10. ✅ Multiple occurrences

### Bonus Features:

- 🔤 **Case-insensitive search** - Find matches regardless of case
- 🔢 **Count occurrences** - How many times pattern appears
- 📍 **First/Last occurrence** - Find specific positions
- 🎨 **Visual highlighting** - See matches in real-time
- ⏱️ **Animation speed control** - Learn at your own pace

## 🌟 Key Concepts Explained

### 1. Index/Position
Where in the string we're currently looking (0-based indexing).

### 2. Character Comparison
Checking if two characters are equal: `text[i] == pattern[j]`

### 3. Sliding Window
Moving the pattern one position at a time across the text.

### 4. Nested Loops
Outer loop: positions in text  
Inner loop: characters in pattern

### 5. Early Termination
Stop comparing when a mismatch is found (optimization).

## 💡 Next Steps

After mastering this naive approach, explore more efficient algorithms:

- **KMP (Knuth-Morris-Pratt)** - O(n + m) time complexity
- **Boyer-Moore** - Often faster in practice, works backwards
- **Rabin-Karp** - Uses hashing for pattern matching
- **Aho-Corasick** - For multiple pattern matching
- **Regular Expressions** - Powerful pattern matching with special syntax

## 🐛 Troubleshooting

### Python GUI won't start
- Make sure tkinter is installed: `python -m tkinter`
- Try reinstalling Python with tkinter support

### Java colors not showing
- Your terminal might not support ANSI colors
- Try a different terminal (Windows Terminal, iTerm2, etc.)

### JavaFX errors
- See [SETUP_JAVAFX.md](SETUP_JAVAFX.md) for detailed setup
- Consider using the terminal version or Python GUI as alternatives

## 📁 Project Structure

```
Strin/
│
├── pattern_search.py          # Python terminal version
├── pattern_search_gui.py      # Python GUI version (tkinter)
├── PatternSearch.java         # Java terminal version
├── PatternSearchGUI.java      # JavaFX GUI version
├── README.md                  # This file
└── SETUP_JAVAFX.md           # JavaFX setup instructions
```

## 🤝 Contributing

This is an educational project. Feel free to:
- Add more test cases
- Implement more efficient algorithms
- Create versions in other languages
- Improve visualizations
- Add more educational features

## 📝 License

This project is created for educational purposes. Feel free to use and modify for learning!

## 🎯 Learning Outcomes

After completing this tutorial, you should be able to:

✅ Understand how pattern matching works fundamentally  
✅ Write your own pattern search algorithm from scratch  
✅ Analyze time and space complexity  
✅ Handle edge cases in algorithms  
✅ Visualize algorithm execution  
✅ Compare different algorithmic approaches  
✅ Apply the sliding window technique to other problems  

---

**Happy Learning! 🚀**

*Start with the version that matches your skill level and gradually explore the others!*

