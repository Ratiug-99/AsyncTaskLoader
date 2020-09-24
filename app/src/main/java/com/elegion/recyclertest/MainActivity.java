package com.elegion.recyclertest;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnItemClickListener,
        LoaderManager.LoaderCallbacks<String > {

    // добавить фрагмент с recyclerView ---
    // добавить адаптер, холдер и генератор заглушечных данных ---
    // добавить обновление данных и состояние ошибки ---
    // добавить загрузку данных с телефонной книги ---
    // добавить обработку нажатий ---
    // добавить декораторы ---

    Loader<String > mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecyclerFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onItemClick(String id) {
        getSupportLoaderManager().restartLoader(Integer.parseInt(id),new Bundle(),this).forceLoad();
        mLoader = getSupportLoaderManager().getLoader(Integer.parseInt(id));

    }

    @Override
    public Loader<String > onCreateLoader(int id, Bundle args) {
        mLoader = new MyAsyncTaskLoader(this, id);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null){
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + data)));
        }
        else {
            Toast.makeText(this,"Number not found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cancleAction) {
            mLoader.cancelLoad();
            Toast.makeText(this,"Request canceled",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }
}
