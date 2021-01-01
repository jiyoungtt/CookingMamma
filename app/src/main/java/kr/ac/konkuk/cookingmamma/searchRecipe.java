package kr.ac.konkuk.cookingmamma;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class searchRecipe extends Activity {

    LinearLayout layout;
    Context context;
    TextView tv;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentID = mAuth.getCurrentUser().getUid();//현사용자 id
    String ingred = "";
    String recipeingedi = "";
    String mypocket2= "";
    String title = "";
    String [] mypocket11;// = new String[];
    String [] recipein;
    Button btn1;
    Button btn2;
    StringBuffer pocketbuffer;
    StringBuffer ingredientbuffer;

    private ListView listView;
    List fileList = new ArrayList<>();
    ArrayAdapter adapter;

    List ingrelist = new ArrayList<>();
    List pocketlist = new ArrayList<>();

    TextView tv1;



    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle saveInstanceState){
        //layout = (LinearLayout)findViewById(R.id.ll);
        context = this;
        super.onCreate(saveInstanceState);
        setContentView(R.layout.searchrecipe);
        final String mylist = "";
        tv = (TextView)findViewById(R.id.searchrecipe_textview);
        tv1 = (TextView)findViewById(R.id.tv);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mypocket = firebaseDatabase.getReference("mypocket/"+currentID);//"mypocket/jiyoungtt"

        //딱 한번만 불러온다.
        mypocket.addValueEventListener(new ValueEventListener() { //addValueEventListener
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                pocketbuffer = new StringBuffer();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value = snapshot.getValue(String.class);
                    pocketbuffer.append(value+"   ");
                    //mypocket11 = pocketbuffer.toString().split("   ");
                }tv.setText(pocketbuffer.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fileList);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference recipeingredient = firebaseDatabase1.getReference("Recipe");

        recipeingredient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (final DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String str = dataSnapshot.child("ingredient").getValue(String.class);
                    recipein = str.split(",");
                    for (int i = 1; i< recipein.length;i++){
                        ingrelist.add(recipein[i]);
                        Log.i("레시피의 재료들: ",recipein[i]);
                    }
                    String mypocket2 = tv.getText().toString();
                    mypocket11 = mypocket2.split("   ");
                    for (int i = 0; i< mypocket11.length;i++){
                        pocketlist.add(mypocket11[i]);
                        Log.i("마이포켓에는 뭐가 있는가 ",mypocket11[i]);

                    }

                    for (int i = 0;i<mypocket11.length;i++){
                        for (int j = 0; j<recipein.length;j++){
                            if (mypocket11[i].equals(recipein[j])){
                                //일치하는게 있다
                                Log.i("일치하면 소리질러:","일치해일치해해ㅐ해해해해해해해햏일치한다고");
                                //TODO:제목을 가지고 와서 리스트에 넣는다.
                                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                                String title1 = recipe.getTitle();
                                fileList.add(title1);
                                Log.i("일치하는 것이 제목:",title1);
                                Log.i("일치하는 것이 제목2:",fileList.get(i).toString());

                                //리스트를 클릭하면 다이얼로그로 레시피의 내용이 나온다 --> 제목, 식재료 , 내용. 사진링크
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        AlertDialog.Builder alertdb = new AlertDialog.Builder(searchRecipe.this);
                                        alertdb.setMessage(dataSnapshot.child("title").getValue().toString()+"\n"
                                        +dataSnapshot.child("ingredient").getValue().toString()+"\n"+
                                                dataSnapshot.child("content").getValue().toString()+"\n"+
                                                dataSnapshot.child("photo").getValue().toString()+"\n");
                                    }
                                });
                            }
                        }
                    }//비교문


                }//snapshot for문
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }//onCreate()*************************************************************oncreate




    public void toMainmenu(View target){
        Intent intent = new Intent(getApplicationContext(),Mainmenu.class);
        startActivity(intent);

    }

}
