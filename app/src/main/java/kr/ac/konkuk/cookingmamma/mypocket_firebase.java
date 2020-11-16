package kr.ac.konkuk.cookingmamma;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class mypocket_firebase {

    public String ingredient_name;

    public mypocket_firebase(){

    }

    public mypocket_firebase(String ingredient_name){
        this.ingredient_name = ingredient_name;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ingredient_name", ingredient_name);
        return result;
    }

}
