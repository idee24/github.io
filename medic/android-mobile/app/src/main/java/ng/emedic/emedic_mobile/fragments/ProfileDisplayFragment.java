package ng.emedic.emedic_mobile.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.adapters.ViewPagerAdapter;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.utils.EmedicUtils;

public class ProfileDisplayFragment extends Fragment {
    private ServicesActivity activity;
    private View currentView;
    private CircleImageView profileImage;
    private CircleImageView profileImageDefault;
    private RelativeLayout imageLoader;
    private FloatingActionButton editButton;
    private TextView nameTextView;
    private TextView addressTextView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_display, null);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentView = getView();
        if (currentView == null) {
            return;
        }
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_profile));
        }

        profileImage = currentView.findViewById(R.id.profile_image);
        profileImageDefault = currentView.findViewById(R.id.profile_image_default);
        imageLoader = currentView.findViewById(R.id.imageLoader);
        editButton = currentView.findViewById(R.id.editButton);

        nameTextView = currentView.findViewById(R.id.nameTextView);
        addressTextView = currentView.findViewById(R.id.addressTextView);

        viewPager = currentView.findViewById(R.id.viewPager);
        tabLayout = currentView.findViewById(R.id.tabs);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.loadFragment(new ProfileFragment());
            }
        });

        loadProfile();
    }

    private void loadProfile() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ProfileDetailsFragment(), getString(R.string.details_text));
        adapter.addFragment(new ProfileBiodataFragment(), getString(R.string.title_biodata));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        User user = activity.getUser();
        if (user != null) {
            EmedicUtils.displayImage(user.getPictureUrl(), imageLoader, profileImage, profileImageDefault);
            nameTextView.setText(user.getFullName());
            addressTextView.setText(user.getAddress() != null ? user.getAddress() : "N/A");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ServicesActivity) getActivity();
        assert activity != null;
    }
}
