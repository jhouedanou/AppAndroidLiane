package com.houedanou.liane;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.houedanou.liane.data.RssItem;
import com.houedanou.liane.listeners.ListListener;
import com.houedanou.liane.util.RssReader;

/**
 * Each category tab activity.
 *
 */
public class RssChannelActivity extends Activity {
	
	// A reference to this activity
    private RssChannelActivity local;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_rss_channel);
		
		// Get the RSS URL that was set in the RssTabActivity
		String rssUrl = (String)getIntent().getExtras().get("rss-url");
		
		// Set reference to this activity
        local = this;
         
        GetRSSDataTask task = new GetRSSDataTask();
         
        // Start process RSS task
        task.execute(rssUrl);
		
	}
	
	/**
	 * This class downloads and parses RSS Channel feed.
	 * 
	 * @author webmaster
	 *
	 */
	private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem> > {
        //progressBar
		@Override
        protected List<RssItem> doInBackground(String... urls) {
            try {
                // Create RSS reader
                RssReader rssReader = new RssReader(urls[0]);
             
                // Parse RSS, get items
                return rssReader.getItems();
             
            } catch (Exception e) {
                Log.e("RssChannelActivity", e.getMessage());
            }
             
            return null;
        }
         
        @Override
        protected void onPostExecute(List<RssItem> result) {
             
            // Get a ListView from the RSS Channel view
            ListView itcItems = (ListView) findViewById(R.id.rssChannelListView);
                         
            // Create a list adapter
            ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local,android.R.layout.simple_list_item_1, result);
            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);
             //remove the progressBar
            // Set list view item click listener
            itcItems.setOnItemClickListener(new ListListener(result, local));
        }
    }
	
}
