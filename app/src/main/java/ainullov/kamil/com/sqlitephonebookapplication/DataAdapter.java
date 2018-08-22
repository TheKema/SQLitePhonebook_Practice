package ainullov.kamil.com.sqlitephonebookapplication;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ainullov.kamil.com.sqlitephonebookapplication.db.DataBaseHelper;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Person> people;

    private DataAdapter adapter = this;

    private Context mContext;

    DataAdapter(Context context, List<Person> people) {
        this.people = people;
        this.inflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, final int position) {
        final Person person = people.get(position);
        holder.nameView.setText(person.getName());
        holder.phoneNumber.setText(person.getPhoneNumber());
        holder.tvDescription.setText(person.getDesc());


        holder.llAdapter.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuItem edit = contextMenu.add(Menu.NONE, 1, 1, "Call");
                edit.setOnMenuItemClickListener(onEditMenu);
            }

            private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case 1:
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            String tes = "tel:" + person.getPhoneNumber();
                            intent.setData(Uri.parse(tes));
                            mContext.startActivity(intent);
                            break;
                    }
                    return true;
                }
            };
        });

        holder.llAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Settings");

                final EditText etNameDialog = (EditText) dialog.findViewById(R.id.etNameDialog);
                etNameDialog.setText(people.get(position).getName());
                final EditText etNumberDialog = (EditText) dialog.findViewById(R.id.etNumberDialog);
                etNumberDialog.setText(people.get(position).getPhoneNumber());
                final EditText etDescDialog = (EditText) dialog.findViewById(R.id.etDescDialog);
                etDescDialog.setText(people.get(position).getDesc());

                Button btnCallDialog = (Button) dialog.findViewById(R.id.btnCallDialog);
                Button btnDeleteDialog = (Button) dialog.findViewById(R.id.btnDeleteDialog);
                Button btnSaveDialog = (Button) dialog.findViewById(R.id.btnSaveDialog);

                btnCallDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        String tes = "tel:" + person.getPhoneNumber();
                        intent.setData(Uri.parse(tes));
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });

                btnDeleteDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        int delCount = db.delete("mytable", "id = " + position, null);
                        people.remove(position);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                btnSaveDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strNameDialog = etNameDialog.getText().toString();
                        String strNumberDialog = etNumberDialog.getText().toString();
                        String strDescDialog = etDescDialog.getText().toString();
                        String id = position + ""; // Заменяем по id дебильно сделал

                        people.get(position).setName(strNameDialog);
                        people.get(position).setPhoneNumber(strNumberDialog);
                        people.get(position).setDesc(strDescDialog);

                        ContentValues cv = new ContentValues();
                        DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        cv.put("name", strNameDialog);
                        cv.put("number", strNumberDialog);
                        cv.put("description", strDescDialog);
                        //Обновляем
                        int updCount = db.update("mytable", cv, "id = ?",
                                new String[] { id });
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, phoneNumber, tvDescription;
        final ConstraintLayout llAdapter;

        ViewHolder(View view) {
            super(view);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            nameView = (TextView) view.findViewById(R.id.name);
            phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
            llAdapter = (ConstraintLayout) view.findViewById(R.id.llAdapter);
        }
    }


}
