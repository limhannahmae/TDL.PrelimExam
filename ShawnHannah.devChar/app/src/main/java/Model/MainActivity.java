package Model;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import  com.lim.user.shawnhannahdevchar.lrucachepracticewithdatabase.Adapters.Adapter;
import  com.lim.user.shawnhannahdevchar.lrucachepracticewithdatabase.Model.ToDoModel;
import  com.lim.user.shawnhannahdevchar.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{



    ToDoModel models;
    Adapter adapter;
    EditText inp;
    EditText desc;
    ListView list;
    Button butt;
    ArrayAdapter<String> mAdapter;
    ArrayList<ToDoModel> inputss;
    LruCache <String, String>mMemoryCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(com.lim.user.shawnhannahdevchar.R.layout.activity_main);
        inp = (EditText)findViewById(com.lim.user.shawnhannahdevchar.R.id.input);
        list = (ListView)findViewById(com.lim.user.shawnhannahdevchar.R.id.lista);
        desc = (EditText)findViewById(com.lim.user.shawnhannahdevchar.R.id.descInput);
        butt = (Button)findViewById(com.lim.user.shawnhannahdevchar.R.id.but);
        adapter = new Adapter(this, com.lim.user.shawnhannahdevchar.R.layout.listviewlayout, new ArrayList<ToDoModel>());

        inputss = new ArrayList<>();
        final int memClass = ((ActivityManager) this.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();

        final int cacheSize = 1024 * 1024 * memClass / 8;
        mMemoryCache = new LruCache<String,String>(cacheSize){

        };


        SharedPreferences sharedPreferences = getSharedPreferences("bernard",0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("charlie",null);
        Type type = new TypeToken<ArrayList<ToDoModel>>(){}.getType();
        ArrayList<ToDoModel> arrayList = gson.fromJson(json, type);
        if (arrayList!= null) {
            for (int i = 0; i < arrayList.size(); i++) {
                String title = arrayList.get(i).getTitle();
                String description = arrayList.get(i).getDescription();
                models = new ToDoModel(title, description);
                adapter.add(models);
                inputss.add(models);
            }
        }


        Toast.makeText(this, ""+cacheSize, Toast.LENGTH_LONG).show();
        list.setAdapter(adapter);

        String d="";
        String titt="";


        if(mMemoryCache.get("anton")!=null && mMemoryCache.get("kf")!=null){
            Toast.makeText(this, "nay sud", Toast.LENGTH_SHORT).show();
            titt = mMemoryCache.get("anton");
            d = mMemoryCache.get("kf");
            models = new ToDoModel(titt,d);
            adapter.add(models);
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, "way sud", Toast.LENGTH_LONG).show();
        }

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMemoryCache.put("anton",inp.getText().toString());
                mMemoryCache.put("kf",desc.getText().toString());

                String titt = mMemoryCache.get("anton");
                String d = mMemoryCache.get("kf");
                Toast.makeText(MainActivity.this, titt+" "+d, Toast.LENGTH_SHORT).show();

                models = new ToDoModel(inp.getText().toString(),desc.getText().toString());
                adapter.add(models);
                inputss.add(models);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("bernard",0);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(inputss);
        editor.putString("charlie",json);
        editor.commit();
    }
}
