package us.jaaga.demovote;

import us.jaaga.demovote.adapter.TabsPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProjectList extends FragmentActivity implements TabListener {
	
	private ViewPager mViewPager;
	private TabsPagerAdapter mTabsPagerAdapter;
	private ActionBar mActionBar;
	
	private String[] tabs = {"Upcoming","Past"};
	
	
	//ArrayList<ProjectListData> projectdata = new ArrayList<ProjectListData>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_list);
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mActionBar = getActionBar();
		mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		
		/*projectdata = (ArrayList<ProjectListData>) getIntent().getExtras().getSerializable("myProject");*/
		mViewPager.setAdapter(mTabsPagerAdapter);
		
		//mTabsPagerAdapter(projectdata);
		
		//mActionBar.setHomeButtonEnabled(false);
		//Intent mIntent = getIntent();
		
		
		
		  // UpcomingProject(projectdata);
		
		for(String tab_name : tabs){
			
			mActionBar.addTab(mActionBar.newTab().setText(tab_name).setTabListener(this));
		}
		
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				mActionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		mViewPager.setCurrentItem(tab.getPosition());
		
	}



	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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

}
