package gruposd.sdfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton         btAddDisciplina;

    private GoogleSignInClient  mGoogleSignInClient;

    private static final int    RC_SIGN_IN = 123;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager     fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btAddDisciplina = (ImageButton) findViewById(R.id.btAddDisciplina);

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.containerPrincipal, new FragmentInicial(), "FragmentInicial");
        fragmentTransaction.commit();

        btAddDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.containerPrincipal, new FragmentCadastro(), "FragmentCadastro");
                fragmentTransaction.commit();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itemLogOut) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Logout...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        }
                    });
            return true;
        }

        if(id == R.id.itemFechar){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        btAddDisciplina.setVisibility(View.VISIBLE);
        btAddDisciplina.setClickable(true);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerPrincipal, new FragmentInicial(), "FragmentInicial");
        fragmentTransaction.commit();

    }
}
