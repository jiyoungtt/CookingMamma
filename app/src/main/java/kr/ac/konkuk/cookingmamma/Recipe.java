package kr.ac.konkuk.cookingmamma;

public class Recipe {

    String ingredient;
    String title;
    String content;
    String photo;

    public  Recipe(){

    }

    public Recipe(String ingredient,String title, String content, String photo){
        this.ingredient = ingredient;
        this.content = content;
        this.title = title;
        this.photo = photo;
    }

    public String getIngredient() {return ingredient;}

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public String getPhoto(){return photo;}

}
