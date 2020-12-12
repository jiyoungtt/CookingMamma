package kr.ac.konkuk.cookingmamma;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class writeRecipe extends Activity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentID = mAuth.getCurrentUser().getUid();//현사용자 id

    TextView ingredient_textview;
    EditText recipe_ingredient;
    EditText recipe_titile;
    EditText recipe_content;
    LinearLayout layout;
    Context context;
    Button camera;
    //String recipeingre = " ";
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private File tempFile;
    private static final String TAG = "뻐키이이ㅣ이이이이이이이";
    private boolean isPermission = true;


    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.writerecipe);
        recipe_ingredient = (EditText)findViewById(R.id.add_ingredient);
        recipe_titile = (EditText)findViewById(R.id.recipe_titile);
        recipe_content = (EditText)findViewById(R.id.recipe_content);
        layout = (LinearLayout)findViewById(R.id.writelinear);
        context = this;
        ingredient_textview = (TextView)findViewById(R.id.ingredient_textview);
        camera = (Button)findViewById(R.id.btn_camera);

        tedPermission();

        /*
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermission)goToAlbum();
                else Toast.makeText(v.getContext(), getResources().getString(R.string.permission1), Toast.LENGTH_LONG).show();
            }
        });
        */
        registerForContextMenu(camera);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.add(0,1,0,"CAMERA");
        menu.add(0,2,0,"GALLERY");
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case 1:
                //여기에는 카메라실행
                if (isPermission)takePhoto();
                return true;

            case 2:
                //여기에는 수정
                if (isPermission)goToAlbum();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }



    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //권한요청성공

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                //권항요청실패

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission1))
                .setDeniedMessage(getResources().getString(R.string.permission2))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

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

    private void goToAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        //Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }


        if (requestCode == PICK_FROM_ALBUM){
            Uri photoUri = data.getData();
            Cursor cursor = null;

            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
            }finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            setImage();
        } else if (requestCode==PICK_FROM_CAMERA){setImage();}



       // super.onActivityResult(requestCode, resultCode, data);
    }

    private void setImage() {
        ImageView imageView = findViewById(R.id.photo);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(originalBm);
    }

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                Uri photoUri = FileProvider.getUriForFile(this,
                        "{package name}.provider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            } else {

                Uri photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            }
        }
    }


    private File createImageFile() throws IOException{

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }


//그만
}
