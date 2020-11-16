package kr.ac.konkuk.cookingmamma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mainmenu extends Activity {
    private Button mypocket;
    private Button addrecipe;
    private Button searchrecipe;

    @Override
    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.mainmenu);
    }

        public void toLogin(View target){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        }

        public void myPocket(View target){
            Intent intent = new Intent(getApplicationContext(),myPocket.class);
            startActivity(intent);
        }

        public void searchRecipe(View target){
            Intent intent = new Intent(getApplicationContext(),searchRecipe.class);
            startActivity(intent);

        }

        public void addRecipe(View target){
            Intent intent = new Intent(getApplicationContext(),addRecipe.class);
            startActivity(intent);

        }



    }


