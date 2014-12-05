package com.example.recyclerviewbug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void grid(View v){
    	Intent intent = new Intent(this, RecyclerViewActivity.class);
    	intent.putExtra("layoutExtra", RecyclerViewActivity.LayoutExtra.GRID);
    	startActivity(intent);
    }
    
    public void staggered(View v){
    	Intent intent = new Intent(this, RecyclerViewActivity.class);
    	intent.putExtra("layoutExtra", RecyclerViewActivity.LayoutExtra.STAGGERED);
    	startActivity(intent);
    }
    
    public void linear(View v){
    	Intent intent = new Intent(this, RecyclerViewActivity.class);
    	intent.putExtra("layoutExtra", RecyclerViewActivity.LayoutExtra.LINEAR);
    	startActivity(intent);
    }
    
    public void gridImage(View v){
    	Intent intent = new Intent(this, RecyclerViewActivity.class);
    	intent.putExtra("layoutExtra", RecyclerViewActivity.LayoutExtra.GRIDIMAGE);
    	startActivity(intent);
    }
    
    public void gridSubView(View v){
    	Intent intent = new Intent(this, RecyclerViewActivity.class);
    	intent.putExtra("layoutExtra", RecyclerViewActivity.LayoutExtra.GRIDSUBVIEW);
    	startActivity(intent);
    }
}
