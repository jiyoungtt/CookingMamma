package kr.ac.konkuk.cookingmamma;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    String ings = "";
    String title = "";
    String [] mypocket11;// = new String[];
    String [] recipein;
    Button btn1;
    Button btn2;


    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle saveInstanceState){
        //layout = (LinearLayout)findViewById(R.id.ll);
        context = this;
        super.onCreate(saveInstanceState);
        setContentView(R.layout.searchrecipe);
        String mylist = "";
        tv = (TextView)findViewById(R.id.searchrecipe_textview);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mypocket = firebaseDatabase.getReference("mypocket/"+currentID);//"mypocket/jiyoungtt"


        //DatabaseReference dataRef = rootRef.chi
        //참조할 경로 설정 mypocket/jiyoungtt

        //딱 한번만 불러온다.

        mypocket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                StringBuffer buffer = new StringBuffer();

                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value = snapshot.getValue(String.class);
                    buffer.append(value+"   ");
                }tv.setText(buffer.toString());





/*
                //이러면 키 값까지 나옴
                Object value = snapshot.getValue(Object.class);
                tv.setText(value.toString());

*/              ingred = buffer.toString();
                String [] ingredient =ingred.split("\t");
                int leng = ingredient.length;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //DatabaseReference dataRef = rootRef.chi
        //참조할 경로 설정 mypocket/jiyoungtt

        //딱 한번만 불러온다.



        //showing();

       // ingred = show();
        //mypocket11  = ingred.split("\t");
        //final int leng = mypocket.length();


//여기부터 추가중
/*
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = firebaseDatabase1.getReference();
        DatabaseReference dataRef = rootRef.child("recipe");
        DatabaseReference recipeRef = dataRef.child(currentID);
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuffer buffer = new StringBuffer();
                StringBuffer buff = new StringBuffer();
                StringBuffer titlebuff = new StringBuffer();

                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Recipe recipe = snapshot1.getValue(Recipe.class);
                    String ingredient = recipe.getIngredient();
                    String title = recipe.getTitle();
                    String content = recipe.getContent();
                    buffer.append(ingredient+"\n"+title+"\n"+content+"\n");
                    buff.append(ingredient);
                    titlebuff.append(title);
                }
                ings = buffer.toString();
                recipein = ings.split("\t");
                title = titlebuff.toString();
                for(String me:mypocket11)
                {
                    for(String wo:recipein){
                        if(wo.equals(me)){
                            Button button = new Button(context);
                            button.setText(title);
                            button.setBackgroundColor
                                    (button.getResources().getColor(R.color.background));
                            break;

                        }//하나라도 같은게 있으면 버튼으로 출력
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/
//일단 여기까지



    }//onCreate()*************************************************************oncreate
    public void btn1(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("황금계란볶음밥 "+"\n"+"계란"+"\n"+"1, 밥과 계란을1 섞는다"+"\n"+"2,기름에 볶는다");
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void btn2(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("꼬막비빔밥"+"\n"+"꼬막  새싹"+"\n"+"1, 배부르다"+"\n"+"2,이것은 오늘저녁메뉴엤다");
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void callRecipe(View v){
       //final String ingredient1 = "";

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //3, 저장시킬 노드 참조객체 가지고오기
        DatabaseReference rootRef = firebaseDatabase.getReference();

        DatabaseReference dataRef = rootRef.child("recipe");
        //여기가 사용자가 아이디
        DatabaseReference recipeRef = dataRef.child(currentID);//jiyoungtt인 부분
        //DatabaseReference recipeRef = recipeRef1.child(title);
        //recipeRef.push().setValue(recipe);
        //itemRef.child("title").setValue(title).toString();
        //itemRef.child("content").setValue(content).toString();

        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuffer buffer = new StringBuffer();
                //StringBuffer buff = new StringBuffer();

                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Recipe recipe = snapshot1.getValue(Recipe.class);
                    String ingredient = recipe.getIngredient();
                    String title = recipe.getTitle();
                    String content = recipe.getContent();
                    buffer.append(ingredient+"\n"+title+"\n"+content+"\n");

                }
                //ingredient_textview.setText(buffer);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }
    //화면에 마이포켓 뜨게
    public String show(){
        String mylist = "";
        tv = (TextView)findViewById(R.id.searchrecipe_textview);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mypocket = firebaseDatabase.getReference("mypocket/jiyoungtt");//"mypocket/jiyoungtt"

        //DatabaseReference dataRef = rootRef.chi
        //참조할 경로 설정 mypocket/jiyoungtt

        //딱 한번만 불러온다.

        mypocket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                StringBuffer buffer = new StringBuffer();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value = snapshot.getValue(String.class);
                    buffer.append(value+"\t");
                }tv.setText(buffer.toString());
/*
                //이러면 키 값까지 나옴
                Object value = snapshot.getValue(Object.class);
                tv.setText(value.toString());

*/              ingred = buffer.toString();
                String [] ingredient =ingred.split("\t");
                int leng = ingredient.length;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mylist;

    }

    public void showing(){
        String mylist = "";
        tv = (TextView)findViewById(R.id.searchrecipe_textview);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mypocket = firebaseDatabase.getReference("mypocket/jiyoungtt");//"mypocket/jiyoungtt"

        //DatabaseReference dataRef = rootRef.chi
        //참조할 경로 설정 mypocket/jiyoungtt

        //딱 한번만 불러온다.

        mypocket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                StringBuffer buffer = new StringBuffer();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value = snapshot.getValue(String.class);
                    buffer.append(value+"\t");
                }tv.setText(buffer.toString());
/*
                //이러면 키 값까지 나옴
                Object value = snapshot.getValue(Object.class);
                tv.setText(value.toString());

*/              ingred = buffer.toString();
                String [] ingredient =ingred.split("\t");
                int leng = ingredient.length;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    public void toMainmenu(View target){
        Intent intent = new Intent(getApplicationContext(),Mainmenu.class);
        startActivity(intent);

    }
    //public void btn1(){}
    //public void btn2(){}
}
