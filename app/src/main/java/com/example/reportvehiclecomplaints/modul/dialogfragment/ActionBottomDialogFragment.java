package com.example.reportvehiclecomplaints.modul.dialogfragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.reportvehiclecomplaints.R;
import com.example.reportvehiclecomplaints.databinding.FormReportComplaintBinding;
import com.example.reportvehiclecomplaints.model.VehicleModel;
import com.example.reportvehiclecomplaints.modul.dialogfragment.adapter.VehicleListAdapter;
import com.example.reportvehiclecomplaints.modul.dialogfragment.adapter.VehicleListAdapterViewContract;
import com.example.reportvehiclecomplaints.modul.dialogfragment.contract.ActionBottomDialogFragmentPresenterContract;
import com.example.reportvehiclecomplaints.modul.dialogfragment.contract.ActionBottomDialogFragmentViewContract;
import com.example.reportvehiclecomplaints.modul.home.HomeReportDataActivity;
import com.example.reportvehiclecomplaints.util.BuildTempFileAsync;
import com.example.reportvehiclecomplaints.util.Const;
import com.example.reportvehiclecomplaints.util.Injection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ActionBottomDialogFragment extends DialogFragment implements
        ActionBottomDialogFragmentViewContract, VehicleListAdapterViewContract {
    private FormReportComplaintBinding binding;
    private ActionBottomDialogFragmentPresenterContract presenterContract;
    private File imageFile;
    private VehicleModel vehicleModel;
    private DialogListener listener;


    public ActionBottomDialogFragment() {
    }


    public static ActionBottomDialogFragment newInstance() {
        ActionBottomDialogFragment frag = new ActionBottomDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "");
        frag.setArguments(args);
        return frag;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FormReportComplaintBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        params.width = ActionBar.LayoutParams.MATCH_PARENT;
        params.height = ActionBar.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener = (HomeReportDataActivity) getActivity();
        new ActionBottomDialogFragmentPresenter(Injection.provideRepository(requireContext()), this);
        String date = Const.createDate();
        binding.createDateTV.setText(date);
        binding.selectAVehicleTv.setOnClickListener(view1 -> {
            if (binding.vehicleRecycler.getVisibility() == View.GONE) {
                binding.vehicleRecycler.setVisibility(View.VISIBLE);
            } else {
                binding.vehicleRecycler.setVisibility(View.GONE);
            }
        });

        binding.buttonTakeImage.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            String[] mimetypes = {"image/*"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
            launchSomeActivity.launch(intent);
        });

        binding.conditionImageView.setOnClickListener(view1 -> {
            if (imageFile != null) {
                binding.layoutFormReportComplaint.setVisibility(View.GONE);
                binding.previewLayout.setVisibility(View.VISIBLE);
            }
        });

        binding.closePreviewBtn.setOnClickListener(view1 -> {
            binding.previewLayout.setVisibility(View.GONE);
            binding.layoutFormReportComplaint.setVisibility(View.VISIBLE);
        });

        binding.buttonCreateNewComplaint.setOnClickListener(view1 -> {
            if (vehicleModel != null && !binding.noteValeTV.getText().toString().trim().isEmpty() && imageFile != null) {
                presenterContract.doAddReport(
                        vehicleModel.getVehicleId(),
                        binding.noteValeTV.getText().toString().trim(),
                        "ewUeTxmVNi",
                        imageFile);
            } else {
                Toast.makeText(requireContext(), "Error, Pastikan semua data keluhan telah di isi", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void doGetListVehicleSuccessfully(List<VehicleModel> var1) {
        binding.vehicleRecycler.setHasFixedSize(true);
        binding.vehicleRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        VehicleListAdapter adapter;
        if (var1.size() > 0) {
            adapter = new VehicleListAdapter(var1, this);
        } else {
            adapter = new VehicleListAdapter(new ArrayList<>(), this);
        }
        binding.vehicleRecycler.setAdapter(adapter);

    }

    @Override
    public void doGetListVehicleFailed(String toString) {
        Log.i(TAG, "doGetListVehicleFailed: " + toString);
    }

    @Override
    public void doAddReportSuccessfully() {
        listener.onFinishDialog(true);
        dismiss();
    }

    @Override
    public void doAddReportFailed(String string) {
        listener.onFinishDialog(false);
        dismiss();
        Toast.makeText(requireContext(), "Gagal, " + string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void doSelectVehicleType(VehicleModel data) {
        vehicleModel = data;
        binding.selectAVehicleTv.setText(data.getType());
        binding.vehicleRecycler.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(ActionBottomDialogFragmentPresenterContract presenter) {
        this.presenterContract = presenter;
        presenterContract.doGetListVehicle();

    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();

                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        checkDataFile(selectedImageUri);
                    }
                }
            });

    private void checkDataFile(Uri fileUri) {
        if (fileUri.getScheme().equals("content")) {

            String[] proj;
            proj = new String[]{MediaStore.Files.FileColumns.DISPLAY_NAME};
            Cursor cursor = requireActivity().getContentResolver().query(fileUri, proj,
                    null, null, null);
            cursor.moveToFirst();

            int column_index_name = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
            String fileName = cursor.getString(column_index_name);

            try {
                new BuildTempFileAsync(requireContext(), fileName, (path, name) -> {
                    if (path == null) {
                        onFileSelected(RESULT_CANCELED, null, null);
                    } else {
                        onFileSelected(RESULT_OK, name, path);
                    }
                }).execute(requireActivity().getContentResolver().openInputStream(fileUri));
                cursor.close();
                return;
            } catch (FileNotFoundException ignored) {
                Log.e("FileNotFoundException", "checkDataFile: ", ignored);
            }

            cursor.close();

        }

    }

    private void onFileSelected(int resultOk, String fileName, File filePath) {
        if (resultOk == RESULT_OK) {
            imageFile = filePath;
            Glide.with(requireContext())
                    .asBitmap()
                    .load(filePath)
                    .centerCrop()
                    .placeholder(R.drawable.image_none)
                    .apply(RequestOptions.centerCropTransform().error(R.drawable.image_none))
                    .into(binding.conditionImageView);

            Glide.with(requireContext())
                    .asBitmap()
                    .load(imageFile)
                    .centerCrop()
                    .placeholder(R.drawable.image_none)
                    .apply(RequestOptions.centerCropTransform().error(R.drawable.image_none))
                    .into(binding.previewImageView);
            binding.layoutFormReportComplaint.setVisibility(View.GONE);
            binding.previewLayout.setVisibility(View.VISIBLE);
        } else {
            imageFile = null;
        }
    }


    @Override
    public void showConnectionError() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showContentLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideContentLoading() {
        binding.progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showConnectionFailed() {
        binding.progressBar.setVisibility(View.GONE);
    }
}