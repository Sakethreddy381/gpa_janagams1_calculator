package com.example.gpa_janagams1_calculator;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText[] gradeEditTexts = new EditText[5];
    Button computeButton;
    TextView gpaTextView;

    boolean isComputed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        gradeEditTexts[0] = findViewById(R.id.course1GradeEditText);
        gradeEditTexts[1] = findViewById(R.id.course2GradeEditText);
        gradeEditTexts[2] = findViewById(R.id.course3GradeEditText);
        gradeEditTexts[3] = findViewById(R.id.course4GradeEditText);
        gradeEditTexts[4] = findViewById(R.id.course5GradeEditText);
        computeButton = findViewById(R.id.computeButton);
        gpaTextView = findViewById(R.id.gpaTextView);

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isComputed) {
                    clearForm();
                } else {
                    computeGPA();
                }
            }
        });

        for (final EditText editText : gradeEditTexts) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus && isComputed) {
                        computeButton.setText("Compute GPA");
                        isComputed = false;
                    }
                }
            });
        }
    }

    private void computeGPA() {
        double totalGradePoints = 0;
        double totalCredits = 0;

        // Calculate total grade points and credits
        for (int i = 0; i < gradeEditTexts.length; i++) {
            String gradeStr = gradeEditTexts[i].getText().toString().trim();
            if (TextUtils.isEmpty(gradeStr)) {
                gradeEditTexts[i].setError("Enter grade");
                return;
            }

            try {
                double grade = Double.parseDouble(gradeStr);
                if (grade < 0 || grade > 100) {
                    gradeEditTexts[i].setError("Enter valid grade (0-100)");
                    return;
                }
                totalGradePoints += grade;
                totalCredits += 1; // Assuming each course has 1 credit
            } catch (NumberFormatException e) {
                gradeEditTexts[i].setError("Enter valid number");
                return;
            }
        }

        // Calculate GPA
        double gpa = totalGradePoints / totalCredits;

        // Display GPA
        gpaTextView.setText("GPA: " + gpa);

        // Change background color based on GPA
        int color;
        if (gpa < 60) {
            color = Color.RED;
        } else if (gpa <= 79) {
            color = Color.YELLOW;
        } else {
            color = Color.GREEN;
        }
        gpaTextView.setBackgroundColor(color);

        // Change button text to "Clear Form"
        computeButton.setText("Clear Form");
        isComputed = true;
    }

    private void clearForm() {
        gpaTextView.setText("");
        for (EditText editText : gradeEditTexts) {
            editText.setText("");
            editText.setError(null);
        }
        findViewById(android.R.id.content).setBackgroundColor(Color.WHITE);
        computeButton.setText("Compute GPA");
        isComputed = false;
    }
}
