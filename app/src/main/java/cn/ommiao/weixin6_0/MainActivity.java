package cn.ommiao.weixin6_0;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_one)
    MyIcon tabOne;
    @BindView(R.id.tab_two)
    MyIcon tabTwo;
    @BindView(R.id.tab_three)
    MyIcon tabThree;
    @BindView(R.id.tab_four)
    MyIcon tabFour;

    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{
            "First",
            "Second",
            "Third",
            "Fourth"
    };

    private FragmentPagerAdapter adapter;

    private ArrayList<MyIcon> myIcons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews(){
        initFragments();
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(adapter);
        initPagerEvent();
        initTabs();
    }

    private void initFragments(){
        for(String title:titles){
            BaseFragment fragment = new BaseFragment();
            Bundle bundle = new Bundle();
            bundle.putString(BaseFragment.TITLE, title);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
    }

    private void initPagerEvent(){
        viewPager.addOnPageChangeListener(this);
    }

    private void initTabs(){
        myIcons.add(tabOne);
        myIcons.add(tabTwo);
        myIcons.add(tabThree);
        myIcons.add(tabFour);
        tabOne.setIconAlpha(1.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @OnClick(R.id.tab_one)
    public void onTabOneClick(){
        resetTabs();
        myIcons.get(0).setIconAlpha(1.0f);
        viewPager.setCurrentItem(0, false);
    }

    @OnClick(R.id.tab_two)
    public void onTabTwoClick(){
        resetTabs();
        myIcons.get(1).setIconAlpha(1.0f);
        viewPager.setCurrentItem(1, false);
    }

    @OnClick(R.id.tab_three)
    public void onTabThreeClick(){
        resetTabs();
        myIcons.get(2).setIconAlpha(1.0f);
        viewPager.setCurrentItem(2, false);
    }

    @OnClick(R.id.tab_four)
    public void onTabFourClick(){
        resetTabs();
        myIcons.get(3).setIconAlpha(1.0f);
        viewPager.setCurrentItem(3, false);
    }

    private void resetTabs(){
        for (MyIcon icon : myIcons){
            icon.setIconAlpha(0f);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        if(v > 0){
            MyIcon left = myIcons.get(i);
            MyIcon right = myIcons.get(i + 1);
            left.setIconAlpha(1 - v);
            right.setIconAlpha(v);
        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
