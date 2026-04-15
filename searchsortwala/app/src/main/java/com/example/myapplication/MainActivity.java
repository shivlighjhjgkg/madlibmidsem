package com.example.myapplication;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.OnBackPressedCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContentAdapter adapter;
    private List<String> originalContent;
    private List<String> displayContent;
    private String currentSearchKeyword = "";
    private String currentHighlightKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initContent();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContentAdapter(displayContent);
        recyclerView.setAdapter(adapter);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (!currentSearchKeyword.isEmpty()) {
                    currentSearchKeyword = "";
                    filterContent("");
                } else {
                    setEnabled(false);
                    onBackPressed();
                }
            }
        });
    }

    private void initContent() {
        originalContent = new ArrayList<>();
        originalContent.add("Digital transformation is the integration of digital technology into all areas of a business.");
        originalContent.add("It fundamentally changes how businesses operate and deliver value to customers.");
        originalContent.add("It's also a cultural change that requires organizations to continually challenge the status quo.");
        originalContent.add("Digital transformation involves experimentation and getting comfortable with failure.");
        originalContent.add("Key technologies include cloud computing, artificial intelligence, and big data.");
        originalContent.add("The goal is to improve efficiency, agility, and customer engagement.");
        originalContent.add("Many companies are adopting digital-first strategies to stay competitive.");
        originalContent.add("It is not just about technology but also about people and processes.");
        
        displayContent = new ArrayList<>(originalContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            showInputDialog("Search Keywords", "Enter keyword to search:", (input) -> {
                currentSearchKeyword = input;
                filterContent(input);
            });
            return true;
        } else if (id == R.id.action_highlight) {
            showInputDialog("Highlight", "Enter keyword to highlight:", (input) -> {
                currentHighlightKeyword = input;
                adapter.setHighlightKeyword(input);
            });
            return true;
        } else if (id == R.id.sort_alphabetical) {
            sortAlphabetically();
            return true;
        } else if (id == R.id.sort_relevance) {
            sortByRelevance(currentSearchKeyword);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInputDialog(String title, String message, final InputCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> callback.onInput(input.getText().toString()));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void filterContent(String keyword) {
        displayContent.clear();
        if (keyword.isEmpty()) {
            displayContent.addAll(originalContent);
        } else {
            for (String s : originalContent) {
                if (s.toLowerCase().contains(keyword.toLowerCase())) {
                    displayContent.add(s);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void sortAlphabetically() {
        Collections.sort(displayContent);
        adapter.notifyDataSetChanged();
    }

    private void sortByRelevance(final String keyword) {
        if (keyword.isEmpty()) return;
        Collections.sort(displayContent, (o1, o2) -> {
            int count1 = countOccurrences(o1.toLowerCase(), keyword.toLowerCase());
            int count2 = countOccurrences(o2.toLowerCase(), keyword.toLowerCase());
            return Integer.compare(count2, count1); // Descending
        });
        adapter.notifyDataSetChanged();
    }

    private int countOccurrences(String text, String keyword) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(keyword, index)) != -1) {
            count++;
            index += keyword.length();
        }
        return count;
    }

    interface InputCallback {
        void onInput(String input);
    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
        private List<String> items;
        private String highlightKeyword = "";

        ContentAdapter(List<String> items) {
            this.items = items;
        }

        void setHighlightKeyword(String keyword) {
            this.highlightKeyword = keyword;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String text = items.get(position);
            if (highlightKeyword != null && !highlightKeyword.isEmpty() && text.toLowerCase().contains(highlightKeyword.toLowerCase())) {
                SpannableString spannable = new SpannableString(text);
                int start = text.toLowerCase().indexOf(highlightKeyword.toLowerCase());
                while (start != -1) {
                    int end = start + highlightKeyword.length();
                    spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start = text.toLowerCase().indexOf(highlightKeyword.toLowerCase(), end);
                }
                holder.textView.setText(spannable);
            } else {
                holder.textView.setText(text);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textViewContent);
            }
        }
    }
}