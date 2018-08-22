package ainullov.kamil.com.sqlitephonebookapplication;

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
import android.widget.TextView;

import java.util.List;

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
                MenuItem Edit = contextMenu.add(Menu.NONE, 1, 1, "Delete");
                Edit.setOnMenuItemClickListener(onEditMenu);
            }

            private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case 1:
                            DataBaseHelper dbHelper = new DataBaseHelper(mContext);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            int delCount = db.delete("mytable", "id = " + position, null);
                            people.remove(position);

                            adapter.notifyDataSetChanged();
                            break;
                    }
                    return true;
                }
            };
        });

        holder.llAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String tes = "tel:" + person.getPhoneNumber();
                intent.setData(Uri.parse(tes));
                mContext.startActivity(intent);
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
