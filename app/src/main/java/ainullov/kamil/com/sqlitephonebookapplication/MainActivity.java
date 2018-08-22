package ainullov.kamil.com.sqlitephonebookapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.sqlitephonebookapplication.db.DataBaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static int REQUEST_CODE_ADD = 1;
    private String name;
    private String phoneNumber;
    private String desc;

    List<Person> people = new ArrayList<>();
    RecyclerView recyclerView;
    DataAdapter adapter;
    Button btnAdd;

    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // объект для создания и управления версиями БД
        dbHelper = new DataBaseHelper(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(this, people);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                Intent intentAdd = new Intent(this, CreatePerson.class);
                startActivityForResult(intentAdd, REQUEST_CODE_ADD);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD:
                    name = data.getStringExtra("name");
                    phoneNumber = data.getStringExtra("phoneNumber");
                    desc = data.getStringExtra("desc");
                    insertAndRead();
                    break;
            }
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertAndRead() {
        ContentValues cv = new ContentValues();
        // подключение к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("name", name);
        cv.put("number", phoneNumber);
        cv.put("description", desc);



        // вставляем запись и получаем ее ID
        long rowID = db.insert("mytable", null, cv);
        // Чтение, делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        if (c.moveToLast()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int phoneNumberColIndex = c.getColumnIndex("number");
            int descColIndex = c.getColumnIndex("description");
            if (!c.getString(nameColIndex).isEmpty())// получаем значения по номерам столбцов
                people.add(new Person(c.getString(nameColIndex), c.getString(phoneNumberColIndex), c.getString(descColIndex)));

        }

//        if (c.moveToFirst()) {
//            int idColIndex = c.getColumnIndex("id");
//            int nameColIndex = c.getColumnIndex("name");
//            int phoneNumberColIndex = c.getColumnIndex("number");
//            int descColIndex = c.getColumnIndex("description");
//            do {
//                if (!c.getString(nameColIndex).isEmpty())
//                    значения по номерам столбцов
//                    people.add(new Person(c.getString(nameColIndex), c.getString(phoneNumberColIndex), c.getString(descColIndex)));
//            } while (c.moveToNext());
//        }

        c.close();
        dbHelper.close();
        adapter.notifyDataSetChanged();
    }


}
