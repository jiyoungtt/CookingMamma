package kr.ac.konkuk.cookingmamma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class writeRecipe extends Activity {

    TextView ingredient_textview;
    EditText recipe_ingredient;
    EditText recipe_titile;
    EditText recipe_content;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.writerecipe);
        recipe_ingredient = (EditText)findViewById(R.id.add_ingredient);
        recipe_titile = (EditText)findViewById(R.id.recipe_titile);
        recipe_content = (EditText)findViewById(R.id.recipe_content);
        ingredient_textview = (TextView)findViewById(R.id.ingredient_textview);

    }

    public void toAddrecipe(View target){
        Intent intent = new Intent(getApplicationContext(),addRecipe.class);
        startActivity(intent);
    }


    public void saveRecipe(View view){


        //2, Firebase 실시간 디비 관리 객체 얻어오기
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        //3, 저장시킬 노드 참조객체 가지고오기
        DatabaseReference rootRef = firebaseDatabase.getReference();

        //1, EditText에 있는 글자들 얻어오기
        String title =  recipe_titile.getText().toString();
        String content = recipe_content.getText().toString();
        String ingredient = ingredient_textview.getText().toString();

        Recipe recipe = new Recipe(ingredient, title, content);

        DatabaseReference recipeRef = rootRef.child("recipe");
        recipeRef.push().setValue(recipe);
        //itemRef.child("title").setValue(title).toString();
        //itemRef.child("content").setValue(content).toString();

        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuffer buffer = new StringBuffer();

                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Recipe recipe = snapshot1.getValue(Recipe.class);
                    String ingredient = recipe.getIngredient();
                    String title = recipe.getTitle();
                    String content = recipe.getContent();
                    /*
                    for (DataSnapshot ds:snapshot.getChildren()){
                        buffer.append(ds.getKey()+":"+ds.getValue()+"\n");
                    }*/

                    buffer.append(ingredient+"\n"+title+"\n"+content+"\n");
                }
                //ingredient_textview.setText(buffer);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }


    public void addIngredient(View view){
        String rein = recipe_ingredient.getText().toString();
        //rein == recipe ingredient
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();//root를 가지고 온다
        DatabaseReference rootRef = firebaseDatabase1.getReference();

        DatabaseReference dataRef = rootRef.child("recipe_ingredient");

        dataRef.push().setValue(rein);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuffer buffer = new StringBuffer();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String rein = snapshot.getValue(String.class);
                    buffer.append(rein+"\t");
                }
                ingredient_textview.setText(buffer.toString()+"\t");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}