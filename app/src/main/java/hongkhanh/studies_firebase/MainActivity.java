package hongkhanh.studies_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
DatabaseReference mData;
    Song student;
    EditText editText1, editText2, editText3, editText4;
    TextView info_song;
    Button button;
    String email;
    String Id_user;
    ListView listView;
    ArrayList<Song> infoSongList;
    ArrayAdapter adapter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControl();
        initData();
        initEvent();
        initDisplay();



    }

    private void initDisplay() {
    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String song = editText2.getText().toString();
                String singer = editText1.getText().toString();
                String lyrics = editText3.getText().toString();
                String image = editText4.getText().toString();
                Song update_student = new Song(image,lyrics, singer ,song);
                mData.child("USER").child(Id_user).push().setValue(update_student);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String song = infoSongList.get(i).getSong();
                String image = infoSongList.get(i).getImage();
                Toast.makeText(MainActivity.this, song , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Id_user = intent.getStringExtra("ID_user");
        infoSongList = new ArrayList<Song>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1, infoSongList);
        listView.setAdapter(adapter);

        mData = FirebaseDatabase.getInstance().getReference();


        // lay du lieu ve
        mData.child("USER").child(Id_user).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String a = dataSnapshot.getChildren().toString();
                String b = dataSnapshot.getKey();
                int c = (int) dataSnapshot.getChildrenCount();

                String e = dataSnapshot.getClass().toString();
                Song song = dataSnapshot.getValue(Song.class);
                Song songData = new Song();
                songData.setImage(song.image);
                songData.setLyrics(song.lyrics);
                songData.setSinger(song.singer);
                songData.setSong(song.song);
                infoSongList.add(songData);
//                infoSongList.add(song.song+" - "+song.singer);
                adapter.notifyDataSetChanged();
                System.out.println(song.song + "khanh ne");



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initControl() {
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        listView  = (ListView) findViewById(R.id.lv_info_song);
        button = (Button) findViewById(R.id.btn_set);
    }
}
