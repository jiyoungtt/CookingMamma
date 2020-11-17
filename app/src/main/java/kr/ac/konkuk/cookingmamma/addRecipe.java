package kr.ac.konkuk.cookingmamma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.addrecipe);
        tv = (TextView)findViewById(R.id.somenail);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
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

                } tv.setText(buffer);

                //button.setText(str);
                //layout.addView(button);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }//onCreate




    public void toMainmenu(View target){
        Intent intent = new Intent(getApplicationContext(),Mainmenu.class);
        startActivity(intent);
    }
    public void toWriteRecipe(View target){
        Intent intent = new Intent(getApplicationContext(),writeRecipe.class);
        startActivity(intent);
    }









}

