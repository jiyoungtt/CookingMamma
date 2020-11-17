package kr.ac.konkuk.cookingmamma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class writeRecipe extends Activity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentID = mAuth.getCurrentUser().getUid();//현사용자 id

    TextView ingredient_textview;
    EditText recipe_ingredient;
    EditText recipe_titile;
    EditText recipe_content;
    LinearLayout layout;
    Context context;
    //String recipeingre = " ";


    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.writerecipe);
        recipe_ingredient = (EditText)findViewById(R.id.add_ingredient);
        recipe_titile = (EditText)findViewById(R.id.recipe_titile);
        recipe_content = (EditText)findViewById(R.id.recipe_content);
        layout = (LinearLayout)findViewById(R.id.writelinear);
        context = this;
        ingredient_textview = (TextView)findViewById(R.id.ingredient_textview);



    }

    public void toAddrecipe(View target){
        Intent intent = new Intent(getApplicationContext(),addRecipe.class);
        startActivity(intent);
    }//뒤로가기 버튼



   //레시피에 들어갈 식재료 추가
    public void addIngredient(View view){
        String rein = recipe_ingredient.getText().toString();

        final Button button = new Button(context);
        button.setText(rein.toString());
        button.setBackgroundColor(button.getResources().getColor(R.color.text));
        layout.addView(button);

        //rein == recipe ingredient
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();//root를 가지고 온다
        DatabaseReference rootRef = firebaseDatabase1.getReference();

        DatabaseReference dataRef = rootRef.child("recipe_ingredient");
        //DatabaseReference ingrRef = dataRef.child("jiyoungtt-1");

        dataRef.push().setValue(rein);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuffer buffer = new StringBuffer();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String rein = snapshot.getValue(String.class);
                    buffer.append(rein+"\t");

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snapshot.getRef().removeValue();
                            layout.removeView(button);
                        }
                    });


                }//recipeingre = buffer.toString();
                ingredient_textview.setText(buffer.toString()+"\t");
                buffer.delete(0,buffer.length());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addTitle(View view){

        String rein = recipe_titile.getText().toString();

        //rein == recipe ingredient
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();//root를 가지고 온다
        DatabaseReference rootRef = firebaseDatabase1.getReference();

        DatabaseReference dataRef = rootRef.child("recipe_title");
        //DatabaseReference ingrRef = dataRef.child("jiyoungtt-1");

        dataRef.push().setValue(rein);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuffer buffer = new StringBuffer();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String rein = snapshot.getValue(String.class);
                    buffer.append(rein+"\t");

                }//recipeingre = buffer.toString();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toAddrecipe(view);
    }

    public void addContent(View view){

        String rein = recipe_content.getText().toString();

        //rein == recipe ingredient
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();//root를 가지고 온다
        DatabaseReference rootRef = firebaseDatabase1.getReference();

        DatabaseReference dataRef = rootRef.child("recipe_content");
        //DatabaseReference ingrRef = dataRef.child("jiyoungtt-1");

        dataRef.push().setValue(rein);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuffer buffer = new StringBuffer();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String rein = snapshot.getValue(String.class);
                    buffer.append(rein+"\t");

                }//recipeingre = buffer.toString();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saveRecipe(View view){//저장버튼을 클릭했을때

        //2, Firebase 실시간 디비 관리 객체 얻어오기
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        //3, 저장시킬 노드 참조객체 가지고오기
        DatabaseReference rootRef = firebaseDatabase.getReference();

        //1, EditText에 있는 글자들 얻어오기
        String title =  recipe_titile.getText().toString();
        String content = recipe_content.getText().toString();
        String ingredient = ingredient_textview.getText().toString();

        Recipe recipe = new Recipe(ingredient, title, content);

        DatabaseReference dataRef = rootRef.child("recipe");
        //여기가 사용자가 아이디
        DatabaseReference recipeRef = dataRef.child(currentID);//jiyoungtt인 부분
        recipeRef.push().setValue(recipe);

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
                    } */

                    buffer.append(ingredient+"\n"+title+"\n"+content+"\n");
                }
                //ingredient_textview.setText(buffer);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
      /*
        Intent in = new Intent();
        in.putExtra("TITLE",recipe_titile.getText().toString());
        setResult(RESULT_OK,in);
        finish();
       */
        toAddrecipe(view);

    }



}
