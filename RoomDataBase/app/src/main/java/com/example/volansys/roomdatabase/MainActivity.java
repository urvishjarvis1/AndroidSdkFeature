package com.example.volansys.roomdatabase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volansys.roomdatabase.model.Pet;
import com.example.volansys.roomdatabase.model.User;
import com.example.volansys.roomdatabase.model.UserAndPet;
import com.example.volansys.roomdatabase.model.UserDataBase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextName, mEditTextId, mEditTextRead, mEditTextUserId;
    private Button mBtnInsert, mBtnRead, mBtnReadAll, mBtnReadJoin;
    private UserDataBase userDataBase;
    private RadioButton mRadioButtonId, mRadioButtonName;
    private Spinner mSpinner;
    private User user;
    private Pet pet;
    private String res = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(MainActivity.this, "Id already exists", Toast.LENGTH_SHORT).show();
                    mEditTextId.setError("id exists");
                    mEditTextName.setError("name exists");
                    mEditTextUserId.setError("warning");
                    Toast.makeText(MainActivity.this, "Provided Name or Id are already in database!", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    Toast.makeText(MainActivity.this, "User inserted", Toast.LENGTH_SHORT).show();
                    mEditTextId.setText("");
                    mEditTextName.setText("");
                    mEditTextUserId.setText("");
                    break;
                case 3:
                    new AlertDialog.Builder(MainActivity.this).setTitle("Response").setMessage("Data:\n" + res).create().show();
            }
        }
    };
    private String selectedTable = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        mEditTextId = findViewById(R.id.edittxtid);
        mEditTextName = findViewById(R.id.edittxtname);
        mEditTextRead = findViewById(R.id.edittxtread);
        mBtnInsert = findViewById(R.id.btninssubmit);
        mBtnRead = findViewById(R.id.btnreadsubmit);
        mBtnReadAll = findViewById(R.id.btnreadallsubmit);
        mSpinner = findViewById(R.id.tableSpinner);
        mEditTextUserId = findViewById(R.id.edittxtuserid);
        mBtnReadJoin = findViewById(R.id.btnreadjoin);
        mBtnRead.setOnClickListener(this);
        mBtnInsert.setOnClickListener(this);
        mBtnReadAll.setOnClickListener(this);
        mBtnReadJoin.setOnClickListener(this);
        mRadioButtonId = findViewById(R.id.byid);
        mRadioButtonName = findViewById(R.id.byname);

        userDataBase = Room.databaseBuilder(this, UserDataBase.class, "Users.db").addMigrations(UserDataBase.MIGRATION_1_2).build();
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "onSelected: " + parent.getSelectedItem());
                if (parent.getItemAtPosition(position).equals("Pet")) {
                    selectedTable = parent.getSelectedItem().toString();
                    mEditTextUserId.setVisibility(View.VISIBLE);
                    findViewById(R.id.textviewid).setVisibility(View.VISIBLE);
                    mEditTextId.setInputType(InputType.TYPE_CLASS_TEXT);
                    ((TextView) findViewById(R.id.textview)).setText(R.string.enter_type);
                } else {
                    selectedTable = parent.getSelectedItem().toString();
                    mEditTextUserId.setVisibility(View.GONE);
                    findViewById(R.id.textviewid).setVisibility(View.GONE);
                    mEditTextId.setInputType(InputType.TYPE_CLASS_NUMBER);
                    ((TextView) findViewById(R.id.textview)).setText(R.string.enter_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("TAG", "onNothingSelected: " + parent.getSelectedItem());
                if (parent.getSelectedItem().equals("Pet")) {
                    selectedTable = parent.getSelectedItem().toString();
                    mEditTextUserId.setVisibility(View.VISIBLE);
                    findViewById(R.id.textviewid).setVisibility(View.VISIBLE);
                    mEditTextId.setInputType(InputType.TYPE_CLASS_TEXT);
                    ((TextView) findViewById(R.id.textview)).setText(R.string.enter_type);
                } else {
                    selectedTable = parent.getSelectedItem().toString();
                    mEditTextUserId.setVisibility(View.GONE);
                    findViewById(R.id.textviewid).setVisibility(View.GONE);
                    mEditTextId.setInputType(InputType.TYPE_CLASS_NUMBER);
                    ((TextView) findViewById(R.id.textview)).setText(R.string.enter_id);
                }
            }
        });
    }

    void insert(final User user, final Pet pet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (selectedTable.equalsIgnoreCase("user")) {
                        user.setDate(Converter.toDate(System.currentTimeMillis()));
                        userDataBase.userDao().insertToDatabase(user);
                    } else {
                        userDataBase.petDao().insertPet(pet);
                    }

                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } catch (SQLiteConstraintException e) {
                    Log.e("TAG", "run: " + e.getLocalizedMessage());
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                }
            }
        }).start();


    }

    void getById(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (selectedTable.equalsIgnoreCase("user")) {
                    User users = userDataBase.userDao().getUserById(id);
                    res = "";
                    res = res + "\nId:" + users.getUId() + "\nName:" + users.getUserName();
                } else {
                    Pet pet = userDataBase.petDao().getPetById(id);
                    res = "";
                    res = res + "\nName:" + pet.getPetName() + "\nType:" + pet.getPetType();
                }
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);
            }
        }).start();
    }

    void getByName(final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (selectedTable.equalsIgnoreCase("user")) {
                    List<User> users = userDataBase.userDao().getUserByName(name);
                    res = "";
                    for (User u : users) {
                        res = res + "\nId:" + u.getUId() + "\nName:" + u.getUserName();
                        Log.d("datani", u.getUserName() + u.getUId());
                    }
                } else {
                    List<Pet> pets = userDataBase.petDao().getPetByName(name);
                    res = "";
                    for (Pet pet : pets) {
                        res = res + "\nName:" + pet.getPetName() + "\nType:" + pet.getPetType();
                    }
                }
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);

            }
        }).start();
    }

    void getAllUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (selectedTable.equalsIgnoreCase("user")) {
                    List<User> users = userDataBase.userDao().getUsers();
                    res = "";
                    for (User user : users) {
                        res = res + "\nId:" + user.getUId() + "\nName:" + user.getUserName() + "\ndate;" + user.getDate() + "\n";
                        Log.d("datani", user.getUserName() + user.getUId());
                    }
                } else {
                    List<Pet> pets = userDataBase.petDao().getAllPet();
                    res = "";
                    for (Pet pet : pets) {
                        res = res + "\nName:" + pet.getPetName() + "\nType:" + pet.getPetType() + "\n";
                    }
                }
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        }).start();

    }

    private void readJoinQuery() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LiveData<List<UserAndPet>> data = userDataBase.userDao().getJoinData();
                data.observe(MainActivity.this, new Observer<List<UserAndPet>>() {
                    @Override
                    public void onChanged(@Nullable List<UserAndPet> userAndPets) {
                        res = "";
                        for (UserAndPet userAndPet : userAndPets) {
                            res = res + "\nuserName:" + userAndPet.getUser().getUserName() + "\npetName:" + userAndPet.getPet().getPetName() + "\npetType:" + userAndPet.getPet().getPetType() + "\n";
                            Log.d("TAG", userAndPet.getUser().getUserName() + userAndPet.getPet().getPetName());
                        }
                        Message message = new Message();
                        message.what = 3;
                        handler.sendMessage(message);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btninssubmit:
                if (selectedTable.equalsIgnoreCase("user")) {
                    user = new User();
                    if (!mEditTextId.getText().toString().isEmpty())
                        user.setUId(mEditTextId.getText().toString());
                    else mEditTextId.setError("Enter id");
                    if (!mEditTextName.getText().toString().isEmpty())
                        user.setUserName(mEditTextName.getText().toString());
                    else mEditTextName.setError("Enter Name");
                } else if (selectedTable.equalsIgnoreCase("pet")) {
                    pet = new Pet();
                    if (!mEditTextId.getText().toString().isEmpty())
                        pet.setPetType(mEditTextId.getText().toString());
                    else mEditTextId.setError("Enter Type");
                    if (!mEditTextName.getText().toString().isEmpty())
                        pet.setPetName(mEditTextName.getText().toString());
                    else mEditTextName.setError("Enter Name");
                    if (!mEditTextUserId.getText().toString().isEmpty())
                        pet.setUserId(mEditTextUserId.getText().toString());
                    else mEditTextUserId.setError("Enter id");
                }
                insert(user, pet);
                break;
            case R.id.btnreadsubmit:
                if (!mRadioButtonName.isChecked() || !mRadioButtonId.isChecked()) {
                    if (mRadioButtonId.isChecked()) {

                        if (!mEditTextRead.getText().toString().isEmpty()) {
                            String id = mEditTextRead.getText().toString();
                            getById(id);
                        } else {
                            mEditTextRead.setError("Enter Id");
                        }
                    } else if (mRadioButtonName.isChecked()) {

                        if (!mEditTextRead.getText().toString().isEmpty()) {
                            String name = mEditTextRead.getText().toString();
                            getByName(name);
                        } else {
                            mEditTextRead.setError("Enter name");
                        }
                    } else {
                        Toast.makeText(this, "Please select the Method byid or byname", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnreadallsubmit:
                getAllUser();
                break;
            case R.id.btnreadjoin:
                Log.d("TAG", "onClick: getting data");
                readJoinQuery();
                break;
            default:
                //todo

        }
    }


}
