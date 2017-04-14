package br.com.fabiomiyasato.divideconta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.fabiomiyasato.divideconta.adapter.ItemAdapter;
import br.com.fabiomiyasato.divideconta.dao.ItemDAO;
import br.com.fabiomiyasato.divideconta.listener.OnItemClickListener;
import br.com.fabiomiyasato.divideconta.model.Item;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NovoItemActivity.class),
                        NovoItemActivity.CODE_NOVO_ITEM);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        carregaItems();
    }

    private void carregaItems() {
        ItemDAO itemDAO = new ItemDAO(this);
        StringBuilder sb= new StringBuilder();
        List<Item> itemList = itemDAO.getAll();

        RecyclerView rcLista = (RecyclerView) findViewById(R.id.rcLista);
        mAdapter = new ItemAdapter(MainActivity.this, itemList);
        rcLista.setAdapter(mAdapter);
        rcLista.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Item gasto = mAdapter.getItem(position);
                Intent i = new Intent(MainActivity.this, DetalheActivity.class);
                i.putExtra("id", gasto.getId());

                startActivityForResult(i,
                        DetalheActivity.CODE_ATUALIZAR_ITEM);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fecha) {
            startActivity(new Intent(this, FechaContaActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, SobreActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode) {
            case RESULT_CANCELED:
                Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_LONG).show();
                break;
            case NovoItemActivity.CODE_NOVO_ITEM:
                Toast.makeText(MainActivity.this, "Item Cadastrado", Toast.LENGTH_LONG).show();
                carregaItems();
                break;
            case DetalheActivity.CODE_ATUALIZAR_ITEM:
                Toast.makeText(MainActivity.this, "Item Atualizado", Toast.LENGTH_LONG).show();
                carregaItems();
                break;

            case DetalheActivity.CODE_DELETE_ITEM:
                Toast.makeText(MainActivity.this, "Item Deletado", Toast.LENGTH_LONG).show();
                carregaItems();
                break;
        }

    }

}
