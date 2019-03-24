package ng.emedic.emedic_mobile.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Date;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.networking.generators.ServiceGenerator;
import ng.emedic.emedic_mobile.networking.models.Request;
import ng.emedic.emedic_mobile.networking.models.RequestStatus;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.networking.services.DataService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment {
    private ServicesActivity activity;
    private View currentView;
    private Button cancelRequestButton;
    private Button sendRequestButton;
    private EditText informationField;
    private RelativeLayout emedicLoader;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request, null);
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
            actionBar.setTitle(activity.getFragmentTitle());
        }

        cancelRequestButton = currentView.findViewById(R.id.cancelRequestButton);
        sendRequestButton = currentView.findViewById(R.id.sendRequestButton);
        emedicLoader = currentView.findViewById(R.id.emedicLoader);
        informationField = currentView.findViewById(R.id.informationField);

        cancelRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.loadFragment(new ServicesFragment());
            }
        });

        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emedicLoader.setVisibility(View.VISIBLE);
                sendRequestButton.setEnabled(false);
                cancelRequestButton.setEnabled(false);
                System.out.println(activity.getSelectedService());
                User user = activity.getUser();
                Request request = new Request();
                request.setAdditionalInformation(informationField.getText().toString().trim());
                // ToDo Modify Address Options
                request.setOptionalAddress(user.getAddress());
                request.setPatientId(user.getPatientId());
                request.setStatus(RequestStatus.getNumberString(RequestStatus.PENDING));
                request.setServiceId(activity.getSelectedService().getId());
                DataService service = ServiceGenerator.createService(DataService.class, activity);
                Call<ResponseBody> call = service.makeRequest(request);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(activity, "Request made successfully!", Toast.LENGTH_SHORT).show();
                            activity.loadFragment(new ServicesFragment());
                        } else {
                            emedicLoader.setVisibility(View.INVISIBLE);
                            sendRequestButton.setEnabled(true);
                            cancelRequestButton.setEnabled(true);
                            Toast.makeText(activity, "Failed to make request!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        emedicLoader.setVisibility(View.INVISIBLE);
                        sendRequestButton.setEnabled(true);
                        cancelRequestButton.setEnabled(true);
                        Toast.makeText(activity, "Failed to make request!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ServicesActivity) getActivity();
        assert activity != null;
    }
}
