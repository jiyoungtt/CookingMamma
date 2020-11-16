package kr.ac.konkuk.cookingmamma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class searchRecipe extends Activity {

    TextView tv;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.searchrecipe);
        tv = (TextView)findViewById(R.id.searchrecipe_textview);

        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
        DatabaseReference mypocket = rootRef.getReference("mypocket/jiyoungtt");
        //참조할 경로 설정 mypocket/jiyoungtt

        //딱 한번만 불러온다.

        mypocket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue(Object.class);
                tv.setText(value.toString());
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
}
