package ng.emedic.emedic_mobile.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.fragments.AppointmentFragment;
import ng.emedic.emedic_mobile.fragments.RequestFragment;
import ng.emedic.emedic_mobile.networking.models.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private List<Service> services;
    private ServicesActivity activity;

    public ServiceAdapter(ServicesActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.service_list_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        if (services != null && services.size() > position) {
            final Service service = services.get(position);
            holder.serviceTitle.setText(service.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = service.getTitle();
                    activity.setSelectedService(service);
                    activity.setFragmentTitle(title);
                    Fragment fragment;
                    if (title.toLowerCase().contains("appointment")) {
                        fragment = new AppointmentFragment();
                    } else {
                        fragment = new RequestFragment();
                    }
                    activity.loadFragment(fragment);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (services == null) return 0;
        return services.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        final ImageView serviceIcon;
        final TextView serviceTitle;
        ServiceViewHolder(View itemView) {
            super(itemView);
            serviceIcon = itemView.findViewById(R.id.serviceIcon);
            serviceTitle = itemView.findViewById(R.id.serviceTitle);
        }
    }

    public void setServiceData(List<Service> services) {
        this.services = services;
        notifyDataSetChanged();
    }
}
