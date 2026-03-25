package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    private ImageButton btnFilter;
    private ScrollView scrollView;

    // The original paragraphs of the article
    private final String[] PARAGRAPHS = {
            "Digital Transformation is the process of integrating digital technology into all areas of a business, fundamentally changing how it operates and delivers value to customers.",
            "Automation plays a key role in digital transformation. By automating repetitive tasks, organizations can reduce costs, minimize errors, and free up employees to focus on higher-value activities.",
            "Cloud computing is a cornerstone of modern digital transformation strategies. It enables businesses to scale rapidly, collaborate globally, and access powerful computing resources without heavy infrastructure investment.",
            "Artificial Intelligence and Machine Learning are driving the next wave of digital transformation. From predictive analytics to intelligent chatbots, AI is reshaping how companies interact with data and customers.",
            "Customer experience is at the heart of digital transformation. Businesses leverage digital tools to personalize interactions, respond faster, and deliver seamless experiences across web, mobile, and in-store channels.",
            "Cybersecurity is a critical concern during digital transformation. As organizations adopt more digital systems, they must also strengthen their defenses against increasingly sophisticated cyber threats.",
            "Data analytics empowers organizations undergoing digital transformation to make better decisions. Real-time dashboards, business intelligence tools, and big data platforms turn raw information into actionable insights.",
            "Agile methodology supports digital transformation by enabling teams to adapt quickly. Short development cycles, continuous feedback, and cross-functional collaboration help businesses innovate faster.",
            "The Internet of Things (IoT) connects physical devices to digital systems, enabling smarter operations. In manufacturing, healthcare, and logistics, IoT drives efficiency and enables predictive maintenance.",
            "Leadership and culture are essential ingredients for successful digital transformation. Without executive buy-in and a culture that embraces change, even the best technology initiatives can fail."
    };

    private List<String> currentParagraphs;
    private String lastSearchKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent  = findViewById(R.id.tv_content);
        btnFilter  = findViewById(R.id.btn_filter);
        scrollView = findViewById(R.id.scroll_view);

        currentParagraphs = new ArrayList<>(Arrays.asList(PARAGRAPHS));
        renderContent(currentParagraphs, "");

        btnFilter.setOnClickListener(v -> showFilterMenu(v));
    }

    // ── Show PopupMenu with 3 submenus ────────────────────────────────────────
    private void showFilterMenu(View anchor) {
        android.widget.PopupMenu popup = new android.widget.PopupMenu(this, anchor);
        popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_search) {
                showSearchDialog();
                return true;
            } else if (id == R.id.menu_highlight) {
                showHighlightDialog();
                return true;
            } else if (id == R.id.menu_sort_alpha) {
                sortAlphabetically();
                return true;
            } else if (id == R.id.menu_sort_relevance) {
                sortByRelevance();
                return true;
            }
            return false;
        });
        popup.show();
    }

    // ── a. Search Keywords ────────────────────────────────────────────────────
    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("🔍 Search Keywords");

        final EditText input = new EditText(this);
        input.setHint("Enter keyword to search...");
        input.setPadding(48, 24, 48, 24);
        input.setText(lastSearchKeyword);
        builder.setView(input);

        builder.setPositiveButton("Search", (dialog, which) -> {
            String keyword = input.getText().toString().trim();
            if (keyword.isEmpty()) {
                Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
                return;
            }
            lastSearchKeyword = keyword;
            // Filter paragraphs that contain the keyword (case insensitive)
            List<String> results = new ArrayList<>();
            for (String p : PARAGRAPHS) {
                if (p.toLowerCase().contains(keyword.toLowerCase())) {
                    results.add(p);
                }
            }
            currentParagraphs = results.isEmpty()
                    ? new ArrayList<>(Arrays.asList(PARAGRAPHS))
                    : results;

            renderContent(currentParagraphs, keyword);

            String msg = results.isEmpty()
                    ? "No results found for \"" + keyword + "\""
                    : results.size() + " paragraph(s) found for \"" + keyword + "\"";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Clear", (dialog, which) -> {
            lastSearchKeyword = "";
            currentParagraphs = new ArrayList<>(Arrays.asList(PARAGRAPHS));
            renderContent(currentParagraphs, "");
            Toast.makeText(this, "Search cleared", Toast.LENGTH_SHORT).show();
        });

        builder.show();
    }

    // ── b. Highlight ──────────────────────────────────────────────────────────
    private void showHighlightDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("🖊 Highlight Words");

        final EditText input = new EditText(this);
        input.setHint("Enter word/phrase to highlight...");
        input.setPadding(48, 24, 48, 24);
        builder.setView(input);

        builder.setPositiveButton("Highlight", (dialog, which) -> {
            String word = input.getText().toString().trim();
            if (word.isEmpty()) {
                Toast.makeText(this, "Please enter a word to highlight", Toast.LENGTH_SHORT).show();
                return;
            }
            renderContent(currentParagraphs, word);
            Toast.makeText(this, "Highlighted: \"" + word + "\"", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Clear Highlight", (dialog, which) -> {
            renderContent(currentParagraphs, "");
        });

        builder.show();
    }

    // ── c. Sort Alphabetically ────────────────────────────────────────────────
    private void sortAlphabetically() {
        Collections.sort(currentParagraphs, String::compareToIgnoreCase);
        renderContent(currentParagraphs, lastSearchKeyword);
        Toast.makeText(this, "Sorted alphabetically", Toast.LENGTH_SHORT).show();
    }

    // ── c. Sort by Relevance to last search keyword ───────────────────────────
    private void sortByRelevance() {
        if (lastSearchKeyword.isEmpty()) {
            Toast.makeText(this, "Please search a keyword first, then sort by relevance", Toast.LENGTH_LONG).show();
            return;
        }
        final String kw = lastSearchKeyword.toLowerCase();
        // Count occurrences of keyword in each paragraph; higher count = more relevant
        currentParagraphs.sort((a, b) -> {
            int countA = countOccurrences(a.toLowerCase(), kw);
            int countB = countOccurrences(b.toLowerCase(), kw);
            return Integer.compare(countB, countA); // descending
        });
        renderContent(currentParagraphs, lastSearchKeyword);
        Toast.makeText(this, "Sorted by relevance to \"" + lastSearchKeyword + "\"", Toast.LENGTH_SHORT).show();
    }

    // ── Render paragraphs with optional highlight ─────────────────────────────
    private void renderContent(List<String> paragraphs, String highlightWord) {
        if (paragraphs.isEmpty()) {
            tvContent.setText("No results found.");
            return;
        }

        // Build full text with paragraph separators
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paragraphs.size(); i++) {
            sb.append(paragraphs.get(i));
            if (i < paragraphs.size() - 1) sb.append("\n\n");
        }

        String fullText = sb.toString();

        if (highlightWord == null || highlightWord.isEmpty()) {
            tvContent.setText(fullText);
            return;
        }

        // Apply yellow highlight spans on every occurrence of the word
        SpannableString spannable = new SpannableString(fullText);
        String lowerFull = fullText.toLowerCase();
        String lowerWord = highlightWord.toLowerCase();
        int start = 0;
        while ((start = lowerFull.indexOf(lowerWord, start)) != -1) {
            int end = start + highlightWord.length();
            spannable.setSpan(
                    new BackgroundColorSpan(Color.parseColor("#FFE066")),
                    start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            start = end;
        }
        tvContent.setText(spannable);
    }

    // Helper: count non-overlapping occurrences of sub in text
    private int countOccurrences(String text, String sub) {
        int count = 0, idx = 0;
        while ((idx = text.indexOf(sub, idx)) != -1) { count++; idx += sub.length(); }
        return count;
    }
}