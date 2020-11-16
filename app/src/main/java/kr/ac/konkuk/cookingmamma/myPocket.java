package kr.ac.konkuk.cookingmamma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//mypocket.xml
public class myPocket extends Activity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentID = mAuth.getCurrentUser().getUid();//현사용자 id

   // TextView mypocket_textview;
    EditText search_ingredient;
    LinearLayout layout;
    Context context;
    // protected String userID;
    // final static int GET_STRING =1;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mypocket);
        // mypocket_textview = (TextView) findViewById(R.id.mypocket_textview);
        search_ingredient = (EditText)findViewById(R.id.search_ingredient);
        layout = (LinearLayout)findViewById(R.id.newlinear);
        context = this;
    }
    /* 일단은 아이디를 가지고 오는 코드를 작성해 보았다. --> FAIL
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == GET_STRING) {
                if (resultCode == RESULT_OK) {
                    userID = data.getStringExtra("USER_ID");
                    mypocket_textview.setText(data.getStringExtra("USER_ID"));
                    finish();
                }
            }
        }
    */
    public void clicksave(View view){

        final Button button = new Button(context);

        //Edit Text에 있는 글 가지고 오기
        String data = search_ingredient.getText().toString();

        button.setText(data);
        layout.addView(button);
        //button.on
        //Firebase에 실시간 저장

        //1,firebase실시간 DB 관리 객체 얻어오기
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //2, 저장시킬 노드 참조객체 가져오기
        DatabaseReference rootRef = firebaseDatabase.getReference();// ()안에 아무것도 안쓰면 최상위 노드
        DatabaseReference mypocketRef = rootRef.child("mypocket");
        //요기 추가한 부분
        //final이 앞에 추가되어ㅣ 있었음!! 왜??
        DatabaseReference userpocketRef = mypocketRef.child(currentID); //이름에 @불가능!!

        userpocketRef.push().setValue(data);

        //DatabaseReference childRef = rootRef.push();//자식노드 추가
        //childRef.setValue(data);
        //각 노드에 값 대입하기
        //rootRef.setValue(data);
        //저장된 값 불러오기, 별도의 읽어오기 버튼이 없음, 실시간 db인만큼
        //디비 변경이 발생하면 이에 반응하는 리스너를 통해 실시간 디비를 읽어옴
        userpocketRef.addValueEventListener(new ValueEventListener() {
            @Override // mypocket추가하는 모습이다.
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // String data = dataSnapshot.getValue(String.class);

                StringBuffer buffer = new StringBuffer();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String data = snapshot.getValue(String.class);
                    //butthon.setText(data);
                    // layout.addView(butthon);
                    buffer.append(data+"\t");

                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            snapshot.getRef().removeValue();
                            layout.removeView(button);
                        }
                    });

                }
                //mypocket_textview.setText(buffer.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//버튼이 클릭되면 데이터를 지운다!!



    }


    //mainmenu로 돌아가는 버튼
    public void toMainmenu(View target){
        Intent intent = new Intent(getApplicationContext(),Mainmenu.class);
        startActivity(intent);

    }


}
