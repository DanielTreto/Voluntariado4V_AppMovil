package cuatrovientos.voluntariado.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import cuatrovientos.voluntariado.R;
import cuatrovientos.voluntariado.fragments.BlankFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. CAMBIO IMPORTANTE: Cargar el layout del Dashboard que contiene el Drawer
        setContentView(R.layout.activity_dashboard);

        // 2. Configurar la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Ocultar el título por defecto ya que tienes un TextView personalizado en el XML
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // 3. Configurar el DrawerLayout y el Toggle (botón hamburguesa)
        drawerLayout = findViewById(R.id.main); // ID definido en activity_dashboard.xml
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 4. Cargar el fragmento por defecto (Dashboard) al iniciar
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, BlankFragment.newInstance("Dashboard", ""))
                    .commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        String title = item.getTitle().toString();

        int id = item.getItemId();


        if (id == R.id.nav_dashboard) {
            selectedFragment = BlankFragment.newInstance("Dashboard", "Vista Principal");
        } else if (id == R.id.nav_volunteers) {
            selectedFragment = new cuatrovientos.voluntariado.fragments.VolunteersFragment();
        } else if (id == R.id.nav_organizations) {
            selectedFragment = new cuatrovientos.voluntariado.fragments.OrganizationsFragment();
        } else if (id == R.id.nav_activities) {
            selectedFragment = new cuatrovientos.voluntariado.fragments.ActivitiesFragment();
        } else if (id == R.id.nav_events) {
            selectedFragment = new cuatrovientos.voluntariado.fragments.EventsFragment();
        } else if (id == R.id.nav_reports) {
            selectedFragment = new cuatrovientos.voluntariado.fragments.ReportsFragment();
        } else if (id == R.id.nav_settings) {
            selectedFragment = BlankFragment.newInstance("Settings", "Configuración de Usuario");
        } else if (id == R.id.nav_logout) {
            selectedFragment = BlankFragment.newInstance("Logout", "Cerrando sesión...");
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, selectedFragment)
                    .commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}