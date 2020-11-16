package kr.ac.konkuk.cookingmamma;

public class Recipe {
    String ingredient;
    String title;
    String content;
    //String writer;

    public  Recipe(){

    }

    public Recipe(String ingredient, String title, String content){
        this.ingredient = ingredient;
        this.content = content;
        this.title = title;
        //this.writer = writer;
    }

    public String getIngredient() {return ingredient;}

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

  //  public String getWriter(){return writer;}

}
