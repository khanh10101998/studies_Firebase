package hongkhanh.studies_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity implements View.OnClickListener {
EditText edt_username, edt_password, edt_username_register, edt_password_register;
    Button btn_Login, btn_register, btn_logout;
    TextView status, id_user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initControl();
        initData();
        initEvent();
        initDisplay();
        
    }

    private void initDisplay() {
    }

    private void initEvent() {
        btn_Login.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    private void initData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            status.setText(getString("ID",user.getEmail()));
            id_user.setText(getString("google status", user.getUid()));

        } else {
            status.setText("null");
            id_user.setText("null");
        }
    }

    private String getString(String s, String email) {
        String text = s+ ":" + email;
        return text;
    }

    public void registerAccount(){
        String email = edt_username_register.getText().toString();
        String password = edt_password_register.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "dang ky thanh cong", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(login.this, "fail", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void initControl() {
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        status = (EditText) findViewById(R.id.status);
        id_user = (EditText) findViewById(R.id.id_user);
        edt_username_register = (EditText) findViewById(R.id.edt_username_register);
        edt_password_register = (EditText) findViewById(R.id.edt_password_register);
        btn_Login = (Button) findViewById(R.id.btn_login);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_register = (Button) findViewById(R.id.btn_register);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_login:
                loginAccount();
                break;
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
                Toast.makeText(this, "Logout sucessful", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_register:
                registerAccount();
                break;
            default:
                break;
        }
    }

    private void loginAccount() {
        String email = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "login sucessful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(login.this,MainActivity.class);
                            intent.putExtra("email",user.getEmail());
                            intent.putExtra("ID_user",user.getUid());
                            startActivity(intent);
                        } else {
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
