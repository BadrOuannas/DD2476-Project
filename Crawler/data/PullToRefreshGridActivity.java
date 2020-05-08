74
https://raw.githubusercontent.com/harshalbenake/hbworkspace1-100/master/pulltorefresh%20and%20dragndrop%20to%20gridview/LauncherActivity/src/com/handmark/pulltorefresh/samples/PullToRefreshGridActivity.java
/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.samples;

import java.util.Arrays;
import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

public final class PullToRefreshGridActivity extends Activity {

	static final int MENU_SET_MODE = 0;

	private LinkedList<String> mListItems;
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	private ArrayAdapter<String> mAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_grid);

		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		mGridView = mPullRefreshGridView.getRefreshableView();

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
				Toast.makeText(PullToRefreshGridActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
				Toast.makeText(PullToRefreshGridActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}

		});

		mListItems = new LinkedList<String>();

		TextView tv = new TextView(this);
		tv.setGravity(Gravity.CENTER);
		tv.setText("Empty View, Pull Down/Up to Add Items");
		mPullRefreshGridView.setEmptyView(tv);

		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
		mGridView.setAdapter(mAdapter);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addFirst("Added after refresh...");
			mListItems.addAll(Arrays.asList(result));
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshGridView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SET_MODE, 0,
				mPullRefreshGridView.getMode() == Mode.BOTH ? "Change to MODE_PULL_DOWN"
						: "Change to MODE_PULL_BOTH");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem setModeItem = menu.findItem(MENU_SET_MODE);
		setModeItem.setTitle(mPullRefreshGridView.getMode() == Mode.BOTH ? "Change to MODE_PULL_FROM_START"
				: "Change to MODE_PULL_BOTH");

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_SET_MODE:
				mPullRefreshGridView
						.setMode(mPullRefreshGridView.getMode() == Mode.BOTH ? Mode.PULL_FROM_START
								: Mode.BOTH);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
	
	
	
	
	
	public class DragableLinearLayout extends LinearLayout {


	    @SuppressLint("NewApi")
		public DragableLinearLayout(Context context, AttributeSet attrs,
	            int defStyle) {
	        super(context, attrs, defStyle);

	    }

	    public DragableLinearLayout(Context context, AttributeSet attrs) {
	        super(context, attrs);

	    }

	    public DragableLinearLayout(Context context) {
	        super(context);

	    }

	    @SuppressLint("NewApi")
		@Override
	    public boolean onDragEvent(DragEvent event) {
	        //in wich grid item am I?
	        //GridView parent = (GridView) getParent();
	        Object item = mGridView.getAdapter().getItem(
	        		mGridView.getPositionForView(this));
	            //if you need the database id of your item...
	        Cursor cur = (Cursor) item;
	        long l_id = cur.getLong(cur.getColumnIndex("youritemid"));

	        switch (event.getAction()) {
	        case DragEvent.ACTION_DRAG_STARTED:

	            return true;
	        case DragEvent.ACTION_DRAG_ENTERED:

	            setBackgroundColor(Color.GREEN);
	            invalidate();
	            return true;
	        case DragEvent.ACTION_DRAG_EXITED:
	            setBackgroundColor(Color.WHITE);
	            invalidate();
	            return false;
	        case DragEvent.ACTION_DROP:
	ClipData cd = event.getClipData();
	            long l_id_start = Long.valueOf(cd.getItemAt(0).getText()
	                    .toString());
	            //
	            Toast.makeText(getContext(), "DROP FROM " + l_id_start
	                    + " TO " + l_id, Toast.LENGTH_LONG);
	            //do your stuff  
	                    //the db requery will be on the onDragEvent.drop of the container
	                    //see the listener


	            return false;
	        case DragEvent.ACTION_DRAG_ENDED:
	            setBackgroundColor(Color.WHITE);
	            invalidate();
	            //
	            return false;

	        }

	        return true;

	    }


	}


/*
	public View.OnDragListener listenerOnDragEvent = new View.OnDragListener() {

	    @SuppressLint("NewApi")
		public boolean onDrag(View v, DragEvent event) {
	        // Defines a variable to store the action type for the incoming
	        // event
	        final int action = event.getAction();
	        switch (action) {

	        case DragEvent.ACTION_DROP:

	            // REQUERY
	            //updateDbView();
	            return false;
	            // break;

	        }
	        return true;
	    }
	};*/
}
