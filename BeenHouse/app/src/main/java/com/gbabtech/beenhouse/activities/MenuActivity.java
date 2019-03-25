package com.gbabtech.beenhouse.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.gbabtech.beenhouse.R;
import com.gbabtech.beenhouse.activities.fragments.MenuFragment;
import com.gbabtech.beenhouse.activities.fragments.MenuViewPagerAdapter;
import java.util.Objects;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        ViewPager menuPager = findViewById(R.id.menuPager);
        TabLayout menuTabLayout = findViewById(R.id.menuTabLayout);
        initUpViewPager(menuPager, menuTabLayout);
    }

    private void initUpViewPager(final ViewPager menuPager,
                                 final TabLayout menuTabLayout) {
        MenuViewPagerAdapter viewPagerAdapter = new MenuViewPagerAdapter(getSupportFragmentManager());

        Fragment drinksFragment = new MenuFragment();
        Fragment snacksFragment = new MenuFragment();
        viewPagerAdapter.addFragment(drinksFragment, "Drinks");
        viewPagerAdapter.addFragment(snacksFragment, "Snacks");
        menuPager.setAdapter(viewPagerAdapter);
        menuTabLayout.setupWithViewPager(menuPager);
        Objects.requireNonNull(menuTabLayout.getTabAt(0)).setIcon(R.drawable.coffee1);
        Objects.requireNonNull(menuTabLayout.getTabAt(1)).setIcon(R.drawable.coffee3);
    }
}
