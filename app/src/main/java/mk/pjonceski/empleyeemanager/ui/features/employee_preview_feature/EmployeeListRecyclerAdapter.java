package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mk.pjonceski.empleyeemanager.R;
import mk.pjonceski.empleyeemanager.data.models.Employee;

/**
 * The {@link android.support.v7.widget.RecyclerView.Adapter} for the list of employees.
 */
@SuppressWarnings("WeakerAccess")
public class EmployeeListRecyclerAdapter extends RecyclerView.Adapter<EmployeeListRecyclerAdapter.EmployeeHolder> {
    private List<Employee> employeeList = null;
    private EmployeePreviewContract.OnRowItemClickListener onRowItemClickListener;

    public EmployeeListRecyclerAdapter(EmployeePreviewContract.OnRowItemClickListener onRowItemClickListener) {
        this.onRowItemClickListener = onRowItemClickListener;
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.name.setText(employee.getName());
        holder.companyName.setText(employee.getCompanyName());
    }

    /**
     * This method replaces the data inside the RecylerView.
     *
     * @param employees the list to be replaced.
     */
    public void setData(@Nullable List<Employee> employees) {
        if (employees != null) {
            employeeList = new ArrayList<>(employees);
        } else {
            employeeList = null;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return employeeList == null ? 0 : employeeList.size();
    }

    class EmployeeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.employee_list_item_avatar)
        ImageView avatar;
        @BindView(R.id.employee_list_item_name)
        AppCompatTextView name;
        @BindView(R.id.employee_list_item_company_name)
        AppCompatTextView companyName;

        public EmployeeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onClick() {
            if (onRowItemClickListener != null) {
                onRowItemClickListener.itemClicked(employeeList.get(getAdapterPosition()));
            }


        }
    }
}
