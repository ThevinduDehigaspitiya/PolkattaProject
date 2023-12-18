package lk.javainstitute.polkattaproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import lk.javainstitute.polkattaproject.R;
import lk.javainstitute.polkattaproject.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    Toolbar toolbar;

    FirebaseAuth auth;

    private FrameLayout overlayLayout;
    private boolean isOverlayVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.home_toolbar);

        overlayLayout = findViewById(R.id.overlay_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOverlay();
            }
        });


        homeFragment = new HomeFragment();
        loadFragment(homeFragment);

    }
    private void toggleOverlay() {
        if (isOverlayVisible) {
            hideOverlay();
        } else {
            showOverlay();
        }
    }

    private void showOverlay() {
        overlayLayout.setVisibility(View.VISIBLE);
        isOverlayVisible = true;
    }

    private void hideOverlay() {
        overlayLayout.setVisibility(View.INVISIBLE);
        isOverlayVisible = false;
    }

    public void loadFragment(Fragment homeFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_logout) {

            auth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();

        }else if(id == R.id.menu_my_cart) {
            startActivity(new Intent(MainActivity.this,CartActivity.class));
        }
        return true;
    }
}