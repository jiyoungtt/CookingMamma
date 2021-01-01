package kr.ac.konkuk.cookingmamma;




import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.ArrayList;

public class DBHelper_add extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyRecipe1.db";
    public static final String RECIPE_TABLE_NAME = "recipe";
    public static final String RECIPE_COLUMN_ID = "id";
    public static final String RECIPE_COLUMN_TITILE = "title";
    public static final String RECIPE_COLUMN_CONTENT = "content";
    public static final String RECIPE_COLUMN_INGREDIENT = "ingredient";
    public static final String RECIPE_COLUMN_WRITER = "writer";

    public DBHelper_add(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table recipe" +
                        "(id integer primary key,title text, content text, ingredient text, writer text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe");
        onCreate(db);
    }

    public boolean insertRecipe(String title, String content, String ingredient, String writer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("ingredient", ingredient);
        contentValues.put("writer", writer);


        db.insert("recipe", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from recipe where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, RECIPE_TABLE_NAME);
        return numRows;
    }

    public boolean updateRecipe(Integer id, String title, String content, String ingredient, String writer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("ingredient", ingredient);
        contentValues.put("writer", writer);

        db.update("recipe", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteRecipe(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("recipe",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList getAllRecipe() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from recipe", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(RECIPE_COLUMN_ID))+" "+
                    res.getString(res.getColumnIndex(RECIPE_COLUMN_TITILE)));
            res.moveToNext();
        }
        return array_list;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }
}