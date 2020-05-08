74
https://raw.githubusercontent.com/harshalbenake/hbworkspace1-100/master/pulltorefresh%20and%20dragndrop%20to%20gridview/ExampleActivity/src/ca/laplanete/mobile/example/ExampleActivity.java
/**
 * Copyright 2012 
 * 
 * Nicolas Desjardins  
 * https://github.com/mrKlar
 * 
 * Facilite solutions
 * http://www.facilitesolutions.com/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ca.laplanete.mobile.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;


import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import ca.laplanete.mobile.pageddragdropgrid.OnPageChangedListener;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;

public class ExampleActivity extends Activity implements OnClickListener {
    
    private String CURRENT_PAGE_KEY = "CURRENT_PAGE_KEY";
    
    private PagedDragDropGrid gridview;
    
    PullToRefreshScrollView mPullRefreshScrollView;
	ScrollView mScrollView;
	//private LinkedList<String> mListItems;
	//private ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.example);
		
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				Toast.makeText(ExampleActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}
		});

		mScrollView = mPullRefreshScrollView.getRefreshableView();
		//mListItems = new LinkedList<String>();
		
		gridview = (PagedDragDropGrid) findViewById(R.id.gridview);	
		
		ExamplePagedDragDropGridAdapter adapter = new ExamplePagedDragDropGridAdapter(this, gridview);
		//mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        gridview.setAdapter(adapter);
		gridview.setClickListener(this);

		gridview.setBackgroundColor(Color.LTGRAY);	
		
		
		gridview.setOnPageChangedListener(new OnPageChangedListener() {            
            @Override
            public void onPageChanged(PagedDragDropGrid sender, int newPageNumber) {
                Toast.makeText(ExampleActivity.this, "Page changed to page " + newPageNumber, Toast.LENGTH_SHORT).show();                
            }
        });
		mScrollView = mPullRefreshScrollView.getRefreshableView();
	}	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
					"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
					"Allgauer Emmentaler" };
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			Toast.makeText(getApplicationContext(), "Added after refresh...", Toast.LENGTH_SHORT).show();                
			//mListItems.addFirst("Added after refresh...");
			//mListItems.addAll(Arrays.asList(result));
			LinearLayout linearLayout = new LinearLayout(ExampleActivity.this);
	        LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			ImageView imageView=new ImageView(ExampleActivity.this);
			imageView.setImageResource(R.drawable.icon);
			
			List<Page> pages = new ArrayList<Page>();
			Page page1 = new Page();
	        ArrayList<Item> items = new ArrayList<Item>();
	        items.add(new Item(100, "Item 100", R.drawable.ic_launcher));
	        page1.setItems(items);
	        pages.add(page1);
	        
	       
            linearLayout.addView(imageView, layoutParams); 
	        if (true) {
	            synchronized  (gridview) {
	                  gridview.invalidate();
	             }
	         }


			//mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshScrollView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
      int savedPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
      gridview.restoreCurrentPage(savedPage);
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {

	    outState.putInt(CURRENT_PAGE_KEY, gridview.currentPage());
	    super.onSaveInstanceState(outState);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Reset").setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                gridview.setAdapter(new ExamplePagedDragDropGridAdapter(ExampleActivity.this, gridview));
                gridview.notifyDataSetChanged();
                
                return true;
            }
        });

        return true;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Clicked View", Toast.LENGTH_SHORT).show();
    }
}
