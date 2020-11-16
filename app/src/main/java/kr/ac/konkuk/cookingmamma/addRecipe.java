package kr.ac.konkuk.cookingmamma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class addRecipe extends Activity {
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.addrecipe);
    }

    public void toMainmenu(View target){
        Intent intent = new Intent(getApplicationContext(),Mainmenu.class);
        startActivity(intent);
    }

    public void toWriterecipe(View target){
        Intent intent = new Intent(getApplicationContext(),writeRecipe.class);
        startActivity(intent);
    }

    /*
    public void seeWriterecipe(View target){
        Intent intent = new Intent(getApplicationContext(),)
    }
*/

}
