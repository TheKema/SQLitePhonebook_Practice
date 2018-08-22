package ainullov.kamil.com.sqlitephonebookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePerson extends AppCompatActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etNumber;
    private EditText etDesc;
    private Button btnSave;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);

        etName = (EditText) findViewById(R.id.etNameDialog);
        etNumber = (EditText) findViewById(R.id.etNumberDialog);
        etDesc = (EditText) findViewById(R.id.etDescDialog);

        btnSave = (Button) findViewById(R.id.btnSaveDialog);
        btnBack = (Button) findViewById(R.id.btnDeleteDialog);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveDialog:
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
            case R.id.btnDeleteDialog:
                finish();
                break;
        }
    }
}
