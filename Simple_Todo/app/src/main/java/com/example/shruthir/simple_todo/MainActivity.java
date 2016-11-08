package com.example.shruthir.simple_todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<String> items;
ArrayAdapter<String> itemsAdapter;
ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,items);

        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        setUpListViewListerner();
        setUpListClickViewListerner();


    }

    public void onAddItem(View v)
    {
        EditText txtView = (EditText)findViewById(R.id.txtAddTodo);
        String itemTxt = txtView.getText().toString();
        //itemsAdapter.add(itemTxt);
        txtView.setText("");
        items.add(itemTxt);
        itemsAdapter.notifyDataSetChanged();
    }

    private void setUpListClickViewListerner()
    {
//        lvItems.setOnItemClickListener(OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {
//                Intent intent = new Intent(MainActivity.this, SendMessage.class);
//                String message = "abc";
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
//            }
//        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                String message = items.get(position);
                intent.putExtra("clickedItemText", message);
                intent.putExtra("clickedItemPos", position);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            int index = data.getIntExtra("clickedItemPos", -1);
            if(index > -1){
                String editedText = data.getStringExtra("editedText");
                items.set(index,editedText);
                itemsAdapter.notifyDataSetChanged();
                saveItems();
            }

        }
    }

    private void setUpListViewListerner()
    {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent ,
                                           View view, int position, long rowId){
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                saveItems();
                return true;
            }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir , "todo1.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e)
        {
            items = new ArrayList<String>();
        }

    }

    private void saveItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir , "todo1.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}
