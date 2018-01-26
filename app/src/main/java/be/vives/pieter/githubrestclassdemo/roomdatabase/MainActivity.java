package be.vives.pieter.githubrestclassdemo.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Update;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Entity
    public class Contact {

        @PrimaryKey(autoGenerate = true)
        private int uid;

        @ColumnInfo(name = "name")
        private String name;

        @ColumnInfo(name = "phone_number")
        private String phoneNumber;

        // getters and setters
    }

    @Dao
    public interface ContactDao {

        @Query("SELECT * FROM contact")
        List<Contact> getAll();

        @Query("SELECT * FROM contact WHERE name LIKE :name LIMIT 1")
        Contact findByName(String name);

        @Insert
        void insertAll(List<Contact> contacts);

        @Update
        void update(Contact contact);

        @Delete
        void delete(Contact contact);
    }

    @Database(entities = {Contact.class}, version = 1)
    public abstract class MyDatabase extends RoomDatabase {
        public abstract ContactDao contactDao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, DATABASE_NAME).build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
