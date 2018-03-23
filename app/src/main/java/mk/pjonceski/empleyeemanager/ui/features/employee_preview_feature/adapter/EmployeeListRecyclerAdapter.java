package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mk.pjonceski.empleyeemanager.R;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature.EmployeePreviewContract;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;
import mk.pjonceski.empleyeemanager.utils.helpers.PicassoHelper;

/**
 * The {@link android.support.v7.widget.RecyclerView.Adapter} for the list of employees.
 */
@SuppressWarnings("WeakerAccess")
public class EmployeeListRecyclerAdapter extends RecyclerView.Adapter<EmployeeListRecyclerAdapter.EmployeeHolder> {
    private List<Employee> employeeList = null;
    private EmployeePreviewContract.OnRowItemClickListener onRowItemClickListener;
    private Helpers helpers;

    public EmployeeListRecyclerAdapter(EmployeePreviewContract.OnRowItemClickListener onRowItemClickListener,
                                       Helpers helpers) {
        this.onRowItemClickListener = onRowItemClickListener;
        this.helpers = helpers;
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
        loadAvatarImage(holder, employee);
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

        private Target avatarImageTarget;

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

    private void loadAvatarImage(EmployeeHolder holder, Employee employee) {
        holder.avatar.setImageBitmap(null);
        if (holder.avatarImageTarget != null) {
            Picasso.get().cancelRequest(holder.avatarImageTarget);
        }
        /**Check if image exists in image cache dir*/
        File imageFile = helpers.getFileHelper().getImageFromAvatarsImageCache(String.valueOf(employee.getId()));
        if (imageFile != null) {
            Picasso.get().load(imageFile).into(holder.avatar);
        } else {
            holder.avatarImageTarget = helpers.getPicassoHelper().createPicassoImageTarget(
                    String.valueOf(employee.getId()),
                    new PicassoHelper.ImageLoadingListener() {
                        @Override
                        public void onSuccess(Bitmap bitmapImage) {
                            holder.avatar.setImageBitmap(bitmapImage);
                        }

                        @Override
                        public void onError(IOException ex) {

                        }

                        @Override
                        public void onBitmapFailed(Exception ex, Drawable errorDrawable) {

                        }
                    });
            Picasso.get().load(employee.getAvatar()).into(holder.avatarImageTarget);
        }
    }

}
