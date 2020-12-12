package kr.ac.konkuk.cookingmamma;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;
import android.os.AsyncTask;

public class MainActivity extends AppCompatActivity {

    String html = "http://211.237.50.150:7080/openapi/sample/xml/Grid_20150827000000000227_1/1/5&ServiceKey=0a56d3332a0d2f0885ef9ef2a6ba0ced120f0bc2d716c3174b79dfe9acb7be14";

    private static final Pattern PASSSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    private FirebaseAuth mAuth;


    private EditText editTextEmail;
    private EditText editTextPassword;
    //아이디와 비밀번호
    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 파이어베이스 인증 객체 선언
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.et_eamil);
        editTextPassword = findViewById(R.id.et_password);
    }

    public void signUp(View view){
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if (isValidEmail()&&isValidPasswd()){
            createUser(email,password);
        }
    }

    public void signIn(View view){
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if(isValidEmail() && isValidPasswd()) {
            loginUser(email, password);
        }

    }


    //아이디 유효성 검사
    private boolean isValidEmail(){
        if (email.isEmpty()){
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false; //아이디 형식 불일치
        }else{
            return true;
        }
    }
    //비번 유효성 검사
    private boolean isValidPasswd(){
        if (password.isEmpty()){
            return false;
        }else if (!PASSSWORD_PATTERN.matcher(password).matches()){
            return false; //비번 형식 불일치
        }else{
            return true;
        }
    }

    //회원가입
    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, R.string.failed_signup,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //로그인
    private void loginUser(final String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),Mainmenu.class);
                            //여기서부터 추가
                            intent.putExtra("USER_ID", email);
                            setResult(RESULT_OK, intent);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this,R.string.failed_login,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}



