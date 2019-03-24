package ng.emedic.emedic_mobile.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.List;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.adapters.ServiceAdapter;
import ng.emedic.emedic_mobile.networking.generators.ServiceGenerator;
import ng.emedic.emedic_mobile.networking.models.Service;
import ng.emedic.emedic_mobile.networking.services.DataService;
import retrofit2.Call;
import retrofit2.Response;

public class ServicesFragment extends Fragment {
    private ServicesActivity activity;
    private View currentView;
    private RecyclerView servicesRecyclerView;
    private ServiceAdapter serviceAdapter;
    private SwipeRefreshLayout swipeContainer;
    private RelativeLayout noService;
    private ServiceFetcherTask serviceFetcherTask;
    private boolean flag;
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_services, null);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentView = getView();
        if (currentView == null) {
            return;
        }
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_services));
        }
        swipeContainer = currentView.findViewById(R.id.swipeContainer);
        noService = currentView.findViewById(R.id.noService);
        servicesRecyclerView = currentView.findViewById(R.id.servicesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        servicesRecyclerView.setLayoutManager(layoutManager);
        servicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        serviceAdapter = new ServiceAdapter(activity);
        servicesRecyclerView.setAdapter(serviceAdapter);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveServices(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        retrieveServices(true);
    }

    private void retrieveServices(boolean showProgress) {
        flag = showProgress;
        serviceFetcherTask = new ServiceFetcherTask();
        serviceFetcherTask.execute();
    }

    class ServiceFetcherTask extends AsyncTask<Void, Void, List<Service>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (flag) {
                activity.showProgress();
            }
        }

        @Override
        protected List<Service> doInBackground(Void... voids) {
            DataService service = ServiceGenerator.createService(DataService.class, activity);
            Call<List<Service>> call = service.getServices();
            Response<List<Service>> result = null;
            try {
                result = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result != null) {
                return result.body();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Service> services) {
            swipeContainer.setRefreshing(false);
            if (services == null) {
                servicesRecyclerView.setVisibility(View.INVISIBLE);
                noService.setVisibility(View.VISIBLE);
            } else {
                servicesRecyclerView.setVisibility(View.VISIBLE);
                noService.setVisibility(View.INVISIBLE);
                serviceAdapter.setServiceData(services);
            }
            if (flag) {
                activity.hideProgress();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ServicesActivity) getActivity();
        assert activity != null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (serviceFetcherTask != null && !serviceFetcherTask.isCancelled()) {
            serviceFetcherTask.cancel(true);
        }
    }
}
