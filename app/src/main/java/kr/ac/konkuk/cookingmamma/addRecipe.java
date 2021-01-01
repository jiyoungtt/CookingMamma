package kr.ac.konkuk.cookingmamma;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class addRecipe extends Activity {

    static final int GET_STRING =1;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentID = mAuth.getCurrentUser().getUid();//현사용자 id

   // ListView listview;
   // ArrayAdapter mAdapter;
   // String [] retitle;

    String titles = "";
    Context context;
    LinearLayout layout;
    TextView tv0, tv1, tv2, tv3, tv4;
    Button bt;
    LinearLayout layout2;
    int delete = 1;
    int change = 1;


    private ListView listView;
    List fileList = new ArrayList<>();
    ArrayAdapter adapter;



    public void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.addrecipe);

        context = this;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fileList); //android.R.layout.simple_list_item_1

        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference recipeRef = database.child("recipe/"+currentID);

        recipeRef.addValueEventListener(new ValueEventListener() { //addListenerForSingleValueEvent

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // StringBuffer buffer = new StringBuffer();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    String title = recipe.getTitle();
                    //제목을 레시피에 저장
                    fileList.add(title);

                    if (delete == 0){
                        dataSnapshot.getRef().removeValue();
                    }
                    if (change == 0)
                    {
                        dataSnapshot.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }//onCreate



    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        getMenuInflater().inflate(R.menu.menu_mytitle,menu);
        super.onCreateContextMenu(menu,v,menuInfo);

        //menu.add(0,1,0,"DELETE");
        //menu.add(0,2,0,"MODIFY");
    }



    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int index = info.position;

        switch (item.getItemId() ){
            case R.id.delete:
               //삭제
                modify();
                fileList.remove(item);
                adapter.notifyDataSetChanged();
                break;


            case R.id.modify:
                onBackPressed();
                modify();
                adapter.notifyDataSetChanged();
                break;
                //return true;

            default:
                return super.onContextItemSelected(item);

        }
        return true;
    };

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        }
    };




    public void toMainmenu(View target){
        Intent intent = new Intent(getApplicationContext(),Mainmenu.class);
        startActivity(intent);
    }
    public void toWriteRecipe(View target){
        Intent intent = new Intent(getApplicationContext(),writeRecipe.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        //Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }




    //수정
    public void modify(){

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference recipeRef = database.child("recipe/"+currentID);

        recipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Button button = new Button(context);
                //String str = "";
                StringBuffer buffer = new StringBuffer();
                for (final DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    String ingredient = recipe.getIngredient();
                    String title = recipe.getTitle();
                    String content = recipe.getContent();

                    buffer.append(title.toString());
                    dataSnapshot.getRef().removeValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        final DatabaseReference database1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference recipeRef1 = database1.child("Recipe");

        recipeRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Button button = new Button(context);
                //String str = "";
                StringBuffer buffer = new StringBuffer();
                for (final DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    String ingredient = recipe.getIngredient();
                    String title = recipe.getTitle();
                    String content = recipe.getContent();
                    buffer.append(title.toString());
                    dataSnapshot.getRef().removeValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }//modify



}




