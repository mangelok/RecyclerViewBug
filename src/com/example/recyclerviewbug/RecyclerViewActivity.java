package com.example.recyclerviewbug;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewActivity extends Activity {
	private static final int columnCount = 4;
	private static int addItemDelay;
	private static int counter = 0;
	private int itemCount = 50;

	private Adapter<ViewHolder> mAdapter;
	private LayoutManager mLayoutManager;
	private final Handler mAddHandler = new Handler();
	private Runnable mAddRunnable = new Runnable() {

		@Override
		public void run() {
			itemCount++;
			mAdapter.notifyItemInserted(itemCount - 1);
			if (addItemDelay > 0) {
				mAddHandler.postDelayed(this, addItemDelay);
			}
		}

	};

	public enum LayoutExtra {
		LINEAR, GRID, STAGGERED, GRIDIMAGE, GRIDSUBVIEW;
		public RecyclerView.LayoutManager getLayoutManager(Context context) {
			RecyclerView.LayoutManager layoutManager = null;
			switch (this) {
			case LINEAR: {
				layoutManager = new LinearLayoutManager(context);
				break;
			}
			case GRID: {
				layoutManager = new GridLayoutManager(context, columnCount);
				break;
			}
			case STAGGERED: {
				layoutManager = new StaggeredGridLayoutManager(columnCount,
						StaggeredGridLayoutManager.VERTICAL);
				break;
			}
			case GRIDIMAGE: {
				layoutManager = new GridLayoutManager(context, columnCount);
				break;
			}
			case GRIDSUBVIEW: {
				layoutManager = new GridLayoutManager(context, columnCount);
				break;
			}
			default:
				break;
			}
			return layoutManager;
		}

		public ViewHolder getViewHolder(ViewGroup parent) {
			ViewHolder result;
			switch (this) {
			case GRIDSUBVIEW: {
				result = new SubViewHolder(LayoutInflater.from(
						parent.getContext()).inflate(
						R.layout.item_recyclerview, parent, false));
				break;
			}

			case GRIDIMAGE: {
				result = new ImageViewHolder(LayoutInflater.from(
						parent.getContext()).inflate(R.layout.item_gridimage,
						parent, false));
				break;
			}
			default:
				result = new TextViewHolder(LayoutInflater.from(
						parent.getContext()).inflate(
						R.layout.item_recyclerview, parent, false));
				break;
			}
			return result;
		}

		public void setupEverythingElse(TextView header) {
			switch (this) {
			case STAGGERED: {
				addItemDelay = 300;
				header.setText("Hello :) You should be able to scroll down infinitely by holding DPAD down here - but if you try this, you will notice that it gets stuck.");
				break;
			}
			case GRIDSUBVIEW:{
				addItemDelay = -1;
				header.setText("Hello :) This view should only receive focus when you are at the top of the RecyclerView, but if you try scrolling up from the bottom of the list, you will observe that this view receives focus in many other cases.");
				break;
			}
			default: {
				addItemDelay = 300;
				header.setText("Hello :) This view should only receive focus when you are at the top of the RecyclerView, but if you try scrolling down and up in this list using a controller, you will see that it often does.");
				break;
			}
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layoutmanager);
		counter = 0;
		RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerView);
		final LayoutExtra layoutExtra = (LayoutExtra) getIntent()
				.getSerializableExtra("layoutExtra");
		mLayoutManager = layoutExtra.getLayoutManager(this);
		layoutExtra.setupEverythingElse((TextView) findViewById(R.id.header));
		recycler.setLayoutManager(mLayoutManager);
		mAdapter = new Adapter<ViewHolder>() {
			@Override
			public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				return layoutExtra.getViewHolder(parent);
			}

			@Override
			public int getItemCount() {
				return itemCount;
			}

			@Override
			public void onBindViewHolder(final ViewHolder holder,
					final int position) {
				holder.onBind(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

		};
		mAdapter.setHasStableIds(true);
		recycler.setHasFixedSize(true);
		recycler.setAdapter(mAdapter);
		mAddRunnable.run();
	}

	private abstract static class ViewHolder extends RecyclerView.ViewHolder {
		public int originalId;
		
		public ViewHolder(View view) {
			super(view);
			originalId = counter;
			counter++;
		}

		public abstract void onBind(int position);
	}

	private static class TextViewHolder extends ViewHolder {
		private TextView text;

		public TextViewHolder(View view) {
			super(view);
			text = (TextView) view.findViewById(R.id.textview);
		}

		@Override
		public void onBind(int position) {
			text.setText(originalId + " - " + position);
		}
	}

	private static class ImageViewHolder extends ViewHolder {
		private ImageView imageView;
		private Handler imageHandler = new Handler();
		private final Runnable imageChanger = new Runnable() {
			@Override
			public void run() {
				imageView.setImageResource(R.drawable.ready);
			}
		};

		public ImageViewHolder(View view) {
			super(view);
			imageView = (ImageView) view.findViewById(R.id.imageview);
		}

		@Override
		public void onBind(int position) {

			imageView.setImageResource(R.drawable.notready);
			imageHandler.removeCallbacks(imageChanger);
			imageHandler.postDelayed(imageChanger, originalId * 100);
		}
	}

	private static class SubViewHolder extends TextViewHolder {

		public SubViewHolder(View view) {
			super(view);
		}

		@Override
		public void onBind(int position) {
			super.onBind(position);
			if ((position / columnCount) % 5 != 0) {
				((ViewGroup) itemView)
						.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			} else {
				((ViewGroup) itemView)
						.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
			}
		}
	}
}
