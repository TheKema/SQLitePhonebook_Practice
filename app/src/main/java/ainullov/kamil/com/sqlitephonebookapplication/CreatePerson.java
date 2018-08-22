package ainullov.kamil.com.sqlitephonebookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePerson extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    EditText etNumber;
    EditText etDesc;
    Button btnSave;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);

        etName = (EditText) findViewById(R.id.etName);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etDesc = (EditText) findViewById(R.id.etDesc);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                Intent intent = new Intent();
                String name = etName.getText().toString();
                String phoneNumber = etNumber.getText().toString();
                String desc = etDesc.getText().toString();
                intent.putExtra("name", name);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("desc", desc);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
