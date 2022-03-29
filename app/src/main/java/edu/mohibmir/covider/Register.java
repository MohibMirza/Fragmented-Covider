package edu.mohibmir.covider;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.EditText;

import edu.mohibmir.covider.redis.RClass.User;
import edu.mohibmir.covider.redis.RedisDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = Register.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private EditText textInputEditTextFirstName;
    private EditText textInputEditTextLastName;
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private EditText textInputEditTextConfirmPassword;
    private Button buttonRegister;
    private CheckBox cb;
    private AppCompatTextView appCompatTextViewLoginLink;
    private Login lg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        getSupportActionBar().hide();
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        textInputLayoutFirstName = (TextInputLayout) findViewById(R.id.textInputLayoutFirstName);
        textInputLayoutLastName = (TextInputLayout) findViewById(R.id.textInputLayoutLastName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        textInputEditTextFirstName = (EditText) findViewById(R.id.textInputEditTextFirstName);
        textInputEditTextLastName = (EditText) findViewById(R.id.textInputEditTextLastName);
        textInputEditTextEmail = (EditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (EditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (EditText) findViewById(R.id.textInputEditTextConfirmPassword);
        buttonRegister = (Button) findViewById(R.id.appCompatButtonRegister);
        cb = (CheckBox) findViewById(R.id.checkbox_instructor);
        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
        buttonRegister.setOnClickListener(this);
        cb.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegister:
                addToRedis();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void addToRedis() {
        if (!lg.isInputEditTextFilled(textInputEditTextFirstName, textInputLayoutFirstName, "Enter First Name")) {
            return;
        }
        if (!lg.isInputEditTextFilled(textInputEditTextLastName, textInputLayoutLastName, "Enter Last Name")) {
            return;
        }
        if (!lg.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Enter Valid Email")) {
            return;
        }
        if (!lg.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "Enter Valid Email")) {
            return;
        }
        if (!lg.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Enter Password")) {
            return;
        }
        if (!lg.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, "Passwords don't match")) {
            return;
        }
        User u = RedisDatabase.getOrCreateUser(textInputEditTextEmail.getText().toString().trim());
        if (textInputEditTextPassword.getText().toString().trim() == u.getPassword()) {
            Snackbar.make(nestedScrollView, "Email already exists", Snackbar.LENGTH_LONG).show();
        }
        else {
            u.setFirstName(textInputEditTextFirstName.getText().toString().trim());
            u.setLastName(textInputEditTextLastName.getText().toString().trim());
            u.setPassword(textInputEditTextPassword.getText().toString().trim());
            if (cb.isChecked()) {
                u.setIsInstructor(true);
            }
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, "Registration successful", Snackbar.LENGTH_LONG).show();
            textInputEditTextFirstName.setText(null);
            textInputEditTextLastName.setText(null);
            textInputEditTextEmail.setText(null);
            textInputEditTextPassword.setText(null);
            textInputEditTextConfirmPassword.setText(null);
            cb.setChecked(false);
        }
    }
}
