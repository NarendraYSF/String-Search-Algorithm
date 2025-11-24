"""
String Pattern Search Algorithm - GUI Version
==============================================
Interactive graphical interface for learning pattern matching
"""

import tkinter as tk
from tkinter import ttk, scrolledtext, messagebox
import time
from threading import Thread


class PatternSearchGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("🔍 Pattern Search Algorithm - Interactive Learning Tool")
        self.root.geometry("1000x800")
        self.root.configure(bg="#f0f0f0")
        
        # Variables
        self.is_searching = False
        self.search_speed = tk.DoubleVar(value=0.5)
        self.case_sensitive = tk.BooleanVar(value=True)
        self.highlight_matches = tk.BooleanVar(value=True)
        
        # Create the GUI
        self.create_widgets()
        
    def create_widgets(self):
        """Create all GUI elements"""
        
        # Title Frame
        title_frame = tk.Frame(self.root, bg="#2c3e50", pady=15)
        title_frame.pack(fill=tk.X)
        
        title_label = tk.Label(
            title_frame,
            text="🔍 String Pattern Search Algorithm",
            font=("Arial", 20, "bold"),
            bg="#2c3e50",
            fg="white"
        )
        title_label.pack()
        
        subtitle_label = tk.Label(
            title_frame,
            text="Learn how computers search for patterns in text",
            font=("Arial", 10),
            bg="#2c3e50",
            fg="#ecf0f1"
        )
        subtitle_label.pack()
        
        # Main Container
        main_container = tk.Frame(self.root, bg="#f0f0f0", padx=20, pady=20)
        main_container.pack(fill=tk.BOTH, expand=True)
        
        # ===== INPUT SECTION =====
        input_frame = tk.LabelFrame(
            main_container,
            text="📝 Input",
            font=("Arial", 12, "bold"),
            bg="white",
            padx=15,
            pady=15
        )
        input_frame.pack(fill=tk.X, pady=(0, 10))
        
        # Text Input
        tk.Label(
            input_frame,
            text="Text to search in:",
            font=("Arial", 10, "bold"),
            bg="white"
        ).grid(row=0, column=0, sticky="w", pady=(0, 5))
        
        self.text_entry = tk.Entry(
            input_frame,
            font=("Courier New", 11),
            width=70,
            bd=2,
            relief=tk.GROOVE
        )
        self.text_entry.grid(row=1, column=0, columnspan=2, pady=(0, 10), sticky="ew")
        self.text_entry.insert(0, "hello world")
        
        # Pattern Input
        tk.Label(
            input_frame,
            text="Pattern to find:",
            font=("Arial", 10, "bold"),
            bg="white"
        ).grid(row=2, column=0, sticky="w", pady=(0, 5))
        
        self.pattern_entry = tk.Entry(
            input_frame,
            font=("Courier New", 11),
            width=70,
            bd=2,
            relief=tk.GROOVE
        )
        self.pattern_entry.grid(row=3, column=0, columnspan=2, pady=(0, 10), sticky="ew")
        self.pattern_entry.insert(0, "wor")
        
        # Options Frame
        options_frame = tk.Frame(input_frame, bg="white")
        options_frame.grid(row=4, column=0, columnspan=2, pady=(5, 0), sticky="w")
        
        tk.Checkbutton(
            options_frame,
            text="Case Sensitive",
            variable=self.case_sensitive,
            font=("Arial", 9),
            bg="white"
        ).pack(side=tk.LEFT, padx=(0, 15))
        
        tk.Checkbutton(
            options_frame,
            text="Highlight Matches",
            variable=self.highlight_matches,
            font=("Arial", 9),
            bg="white"
        ).pack(side=tk.LEFT, padx=(0, 15))
        
        # Speed Control
        speed_frame = tk.Frame(options_frame, bg="white")
        speed_frame.pack(side=tk.LEFT, padx=(15, 0))
        
        tk.Label(
            speed_frame,
            text="Animation Speed:",
            font=("Arial", 9),
            bg="white"
        ).pack(side=tk.LEFT, padx=(0, 5))
        
        speed_slider = tk.Scale(
            speed_frame,
            from_=0.1,
            to=2.0,
            resolution=0.1,
            orient=tk.HORIZONTAL,
            variable=self.search_speed,
            length=150,
            bg="white",
            font=("Arial", 8)
        )
        speed_slider.pack(side=tk.LEFT)
        
        # Buttons Frame
        button_frame = tk.Frame(input_frame, bg="white")
        button_frame.grid(row=5, column=0, columnspan=2, pady=(15, 0))
        
        self.search_button = tk.Button(
            button_frame,
            text="🔍 Start Search",
            command=self.start_search,
            font=("Arial", 11, "bold"),
            bg="#27ae60",
            fg="white",
            padx=20,
            pady=10,
            relief=tk.RAISED,
            cursor="hand2"
        )
        self.search_button.pack(side=tk.LEFT, padx=5)
        
        self.stop_button = tk.Button(
            button_frame,
            text="⏹ Stop",
            command=self.stop_search,
            font=("Arial", 11, "bold"),
            bg="#e74c3c",
            fg="white",
            padx=20,
            pady=10,
            relief=tk.RAISED,
            cursor="hand2",
            state=tk.DISABLED
        )
        self.stop_button.pack(side=tk.LEFT, padx=5)
        
        tk.Button(
            button_frame,
            text="🗑 Clear",
            command=self.clear_output,
            font=("Arial", 11, "bold"),
            bg="#95a5a6",
            fg="white",
            padx=20,
            pady=10,
            relief=tk.RAISED,
            cursor="hand2"
        ).pack(side=tk.LEFT, padx=5)
        
        tk.Button(
            button_frame,
            text="📚 Examples",
            command=self.show_examples,
            font=("Arial", 11, "bold"),
            bg="#3498db",
            fg="white",
            padx=20,
            pady=10,
            relief=tk.RAISED,
            cursor="hand2"
        ).pack(side=tk.LEFT, padx=5)
        
        # ===== VISUALIZATION SECTION =====
        viz_frame = tk.LabelFrame(
            main_container,
            text="👁 Visual Representation",
            font=("Arial", 12, "bold"),
            bg="white",
            padx=15,
            pady=15
        )
        viz_frame.pack(fill=tk.BOTH, pady=(0, 10))
        
        # Canvas for visual representation
        self.canvas = tk.Canvas(
            viz_frame,
            height=120,
            bg="white",
            highlightthickness=1,
            highlightbackground="#bdc3c7"
        )
        self.canvas.pack(fill=tk.BOTH, expand=True)
        
        # ===== OUTPUT SECTION =====
        output_frame = tk.LabelFrame(
            main_container,
            text="📊 Search Process & Results",
            font=("Arial", 12, "bold"),
            bg="white",
            padx=15,
            pady=15
        )
        output_frame.pack(fill=tk.BOTH, expand=True)
        
        # Output Text Area
        self.output_text = scrolledtext.ScrolledText(
            output_frame,
            font=("Courier New", 9),
            wrap=tk.WORD,
            height=15,
            bd=2,
            relief=tk.GROOVE
        )
        self.output_text.pack(fill=tk.BOTH, expand=True)
        
        # Configure text tags for colored output
        self.output_text.tag_config("header", foreground="#2c3e50", font=("Arial", 10, "bold"))
        self.output_text.tag_config("success", foreground="#27ae60", font=("Courier New", 9, "bold"))
        self.output_text.tag_config("error", foreground="#e74c3c", font=("Courier New", 9, "bold"))
        self.output_text.tag_config("info", foreground="#3498db")
        self.output_text.tag_config("position", foreground="#9b59b6", font=("Courier New", 9, "bold"))
        self.output_text.tag_config("match", foreground="#27ae60", background="#d5f4e6")
        self.output_text.tag_config("nomatch", foreground="#e74c3c")
        
        # Status Bar
        status_frame = tk.Frame(self.root, bg="#34495e", pady=8)
        status_frame.pack(fill=tk.X, side=tk.BOTTOM)
        
        self.status_label = tk.Label(
            status_frame,
            text="Ready to search",
            font=("Arial", 9),
            bg="#34495e",
            fg="white"
        )
        self.status_label.pack(side=tk.LEFT, padx=10)
        
    def write_output(self, text, tag=None):
        """Write text to output area with optional tag"""
        self.output_text.insert(tk.END, text, tag)
        self.output_text.see(tk.END)
        self.root.update()
        
    def clear_output(self):
        """Clear all output"""
        self.output_text.delete(1.0, tk.END)
        self.canvas.delete("all")
        self.status_label.config(text="Cleared")
        
    def update_status(self, text):
        """Update status bar"""
        self.status_label.config(text=text)
        self.root.update()
        
    def visualize_step(self, text, pattern, position, comparing_index=-1, is_match=None):
        """Visualize the current search step on canvas"""
        # Clear all canvas elements
        self.canvas.delete("all")
        self.canvas.update_idletasks()
        
        if not text or not pattern:
            return
        
        # Calculate dimensions
        char_width = 35
        char_height = 35
        start_x = 50
        start_y = 30
        
        # Draw text characters
        for i, char in enumerate(text):
            x = start_x + i * char_width
            
            # Determine color
            if position <= i < position + len(pattern):
                # This character is under the pattern window
                if comparing_index >= 0 and i == position + comparing_index:
                    # Currently comparing this character
                    if is_match:
                        color = "#27ae60"  # Green for match
                    elif is_match is False:
                        color = "#e74c3c"  # Red for no match
                    else:
                        color = "#f39c12"  # Orange for comparing
                else:
                    color = "#3498db"  # Blue for in window
            else:
                color = "#95a5a6"  # Gray for not in window
            
            # Draw character box
            self.canvas.create_rectangle(
                x, start_y, x + char_width - 2, start_y + char_height,
                fill=color, outline="#2c3e50", width=2
            )
            
            # Draw character
            self.canvas.create_text(
                x + char_width // 2 - 1, start_y + char_height // 2,
                text=char if char != ' ' else '␣',
                font=("Courier New", 14, "bold"),
                fill="white"
            )
            
            # Draw index below
            self.canvas.create_text(
                x + char_width // 2 - 1, start_y + char_height + 15,
                text=str(i),
                font=("Arial", 8),
                fill="#7f8c8d"
            )
        
        # Draw pattern window indicator
        if 0 <= position < len(text):
            window_x = start_x + position * char_width
            window_y = start_y - 15
            
            # Draw pattern label with background for better visibility
            pattern_text = f"Pattern: '{pattern}'"
            self.canvas.create_rectangle(
                window_x - 2, window_y - 12,
                window_x + len(pattern_text) * 6 + 2, window_y + 2,
                fill="white", outline="#e74c3c", width=1
            )
            self.canvas.create_text(
                window_x, window_y - 5,
                text=pattern_text,
                font=("Arial", 9, "bold"),
                fill="#e74c3c",
                anchor="w"
            )
            
            # Draw arrow
            arrow_x = window_x + len(pattern) * char_width // 2 - 1
            self.canvas.create_line(
                arrow_x, window_y + 2, arrow_x, start_y - 3,
                arrow=tk.LAST, fill="#e74c3c", width=2
            )
        
    def find_pattern_animated(self, text, pattern):
        """Find pattern with animated visualization"""
        matches = []
        
        # Handle edge cases
        if not pattern:
            self.write_output("⚠️ Pattern is empty!\n", "error")
            return matches
        
        if not text:
            self.write_output("⚠️ Text is empty!\n", "error")
            return matches
        
        if len(pattern) > len(text):
            self.write_output(f"⚠️ Pattern (length {len(pattern)}) is longer than text (length {len(text)})\n", "error")
            return matches
        
        # Apply case sensitivity
        if not self.case_sensitive.get():
            text = text.lower()
            pattern = pattern.lower()
            self.write_output("🔤 Case-insensitive mode enabled\n", "info")
        
        self.write_output("\n" + "="*60 + "\n", "header")
        self.write_output(f"🔍 Searching for '{pattern}' in '{text}'\n", "header")
        self.write_output(f"📏 Text length: {len(text)}, Pattern length: {len(pattern)}\n", "info")
        self.write_output("="*60 + "\n\n", "header")
        
        search_limit = len(text) - len(pattern) + 1
        
        # Main search loop
        for text_position in range(search_limit):
            if not self.is_searching:
                self.write_output("\n⏹ Search stopped by user\n", "error")
                break
            
            self.write_output(f"\n📍 Position {text_position}:\n", "position")
            self.visualize_step(text, pattern, text_position)
            self.root.update()
            time.sleep(self.search_speed.get() * 0.3)
            
            is_match = True
            
            # Compare characters
            for pattern_position in range(len(pattern)):
                if not self.is_searching:
                    break
                
                current_text_index = text_position + pattern_position
                text_char = text[current_text_index]
                pattern_char = pattern[pattern_position]
                
                matches_char = text_char == pattern_char
                
                # Visualize comparison
                self.visualize_step(text, pattern, text_position, pattern_position, matches_char)
                self.root.update()
                
                symbol = "✓" if matches_char else "✗"
                tag = "match" if matches_char else "nomatch"
                
                self.write_output(
                    f"   Comparing: text[{current_text_index}]='{text_char}' ↔ pattern[{pattern_position}]='{pattern_char}' {symbol}\n",
                    tag
                )
                
                time.sleep(self.search_speed.get() * 0.5)
                
                if not matches_char:
                    is_match = False
                    self.write_output(f"   ❌ No match at position {text_position}\n", "nomatch")
                    break
            
            if is_match and self.is_searching:
                matches.append(text_position)
                self.write_output(f"   ✅ MATCH FOUND at position {text_position}!\n", "success")
                self.visualize_step(text, pattern, text_position, len(pattern)-1, True)
                self.root.update()
                time.sleep(self.search_speed.get() * 1.0)
        
        return matches
    
    def search_thread(self):
        """Run search in separate thread"""
        try:
            text = self.text_entry.get()
            pattern = self.pattern_entry.get()
            
            self.is_searching = True
            self.search_button.config(state=tk.DISABLED)
            self.stop_button.config(state=tk.NORMAL)
            
            matches = self.find_pattern_animated(text, pattern)
            
            # Show results
            self.write_output("\n" + "="*60 + "\n", "header")
            if matches and self.is_searching:
                self.write_output(f"🎉 Found {len(matches)} match(es) at position(s): {matches}\n", "success")
                self.update_status(f"Search complete! Found {len(matches)} match(es)")
            elif self.is_searching:
                self.write_output(f"❌ Pattern '{pattern}' not found in '{text}'\n", "error")
                self.update_status("Search complete - No matches found")
            self.write_output("="*60 + "\n", "header")
            
        except Exception as e:
            messagebox.showerror("Error", f"An error occurred: {str(e)}")
        finally:
            self.is_searching = False
            self.search_button.config(state=tk.NORMAL)
            self.stop_button.config(state=tk.DISABLED)
    
    def start_search(self):
        """Start the search process"""
        text = self.text_entry.get()
        pattern = self.pattern_entry.get()
        
        if not text and not pattern:
            messagebox.showwarning("Input Required", "Please enter both text and pattern!")
            return
        
        self.clear_output()
        self.update_status("Searching...")
        
        # Run in separate thread to keep GUI responsive
        search_thread = Thread(target=self.search_thread, daemon=True)
        search_thread.start()
    
    def stop_search(self):
        """Stop the search process"""
        self.is_searching = False
        self.update_status("Search stopped")
    
    def show_examples(self):
        """Show example inputs"""
        examples_window = tk.Toplevel(self.root)
        examples_window.title("📚 Example Test Cases")
        examples_window.geometry("600x500")
        examples_window.configure(bg="white")
        
        tk.Label(
            examples_window,
            text="📚 Click an example to load it",
            font=("Arial", 14, "bold"),
            bg="white",
            pady=15
        ).pack()
        
        examples = [
            ("Basic Search", "hello world", "wor"),
            ("Overlapping Matches", "banana", "ana"),
            ("Pattern Not Found", "hello", "world"),
            ("Repeated Characters", "aaaa", "aa"),
            ("Single Character", "abcabc", "a"),
            ("Multiple Words", "the quick brown fox", "quick"),
            ("Mississippi Test", "Mississippi", "issi"),
            ("Programming", "programming is fun", "gram"),
        ]
        
        frame = tk.Frame(examples_window, bg="white", padx=20, pady=10)
        frame.pack(fill=tk.BOTH, expand=True)
        
        for name, text, pattern in examples:
            btn_frame = tk.Frame(frame, bg="#ecf0f1", relief=tk.RAISED, bd=2)
            btn_frame.pack(fill=tk.X, pady=5)
            
            btn = tk.Button(
                btn_frame,
                text=f"{name}\nText: '{text}' | Pattern: '{pattern}'",
                command=lambda t=text, p=pattern, w=examples_window: self.load_example(t, p, w),
                font=("Arial", 10),
                bg="#ecf0f1",
                relief=tk.FLAT,
                cursor="hand2",
                pady=10,
                anchor="w",
                justify=tk.LEFT
            )
            btn.pack(fill=tk.X, padx=5, pady=5)
    
    def load_example(self, text, pattern, window):
        """Load an example into the input fields"""
        self.text_entry.delete(0, tk.END)
        self.text_entry.insert(0, text)
        self.pattern_entry.delete(0, tk.END)
        self.pattern_entry.insert(0, pattern)
        window.destroy()
        self.update_status(f"Loaded example - Ready to search")


def main():
    """Main function to run the GUI"""
    root = tk.Tk()
    app = PatternSearchGUI(root)
    
    # Center window on screen
    root.update_idletasks()
    width = root.winfo_width()
    height = root.winfo_height()
    x = (root.winfo_screenwidth() // 2) - (width // 2)
    y = (root.winfo_screenheight() // 2) - (height // 2)
    root.geometry(f'{width}x{height}+{x}+{y}')
    
    root.mainloop()


if __name__ == "__main__":
    main()

