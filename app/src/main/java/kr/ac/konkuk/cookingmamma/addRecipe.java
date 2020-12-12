package kr.ac.konkuk.cookingmamma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class addRecipe extends Activity {

    static final int GET_STRING =1;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentID = mAuth.getCurrentUser().getUid();//현사용자 id

    // ListView listview;
    String titles = "";
    Context context;
    LinearLayout layout;
    TextView tv;
    Button bt;
    LinearLayout layout2;
    int delete = 1;
    int change = 1;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.addrecipe);
        tv = (TextView)findViewById(R.id.somenail);
        layout = (LinearLayout)findViewById(R.id.somenaillayout);
        context = this;
        registerForContextMenu(tv);


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference recipeRef = database.child("recipe/"+currentID);

        recipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Button button = new Button(context);
                //String str = "";
                StringBuffer buffer = new StringBuffer();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    String ingredient = recipe.getIngredient();
                    String title = recipe.getTitle();
                    String content = recipe.getContent();

                    buffer.append(title.toString());
                    //  button.setText(title);
                    //layout.addView(button);
                    //str = title;
                    if (delete == 0){
                        dataSnapshot.getRef().removeValue();
                    }
                    if (change == 0)
                    {
                        dataSnapshot.getRef().removeValue();

                    }


                }

                //이부분이 화면에 뜨는 부분 이를 버튼으로 생성
                tv.setText(buffer);
               // tv.setText("\n");
                //button.setText(str);
                //layout.addView(button);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }//onCreate

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.add(0,1,0,"DELETE");
        menu.add(0,2,0,"MODIFY");
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case 1:
                //여기에 삭제
                //TODO: db에서 삭제한다.
                modify();
                return true;

            case 2:
                //여기에는 수정
                //layout.removeView(tv);
                onBackPressed();
                modify();
                return true;

            default:
                return super.onContextItemSelected(item);

        }
    }




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

                } layout.removeView(tv);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }//modify



}

