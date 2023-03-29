package com.example.appcamera.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appcamera.MainActivity;
import com.example.appcamera.databinding.FragmentHomeBinding;
import com.example.appcamera.utils.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class HomeFragment extends Fragment implements ZXingScannerView.ResultHandler{
    ZXingScannerView scannerView;
    private FragmentHomeBinding binding;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        scannerView = new ZXingScannerView(getActivity());
        List<BarcodeFormat> formats = new ArrayList<>();
        scannerView.setFormats(formats);

        FrameLayout contentFrame = binding.frame;
        contentFrame.addView(scannerView);
        Intent i = getActivity().getIntent();

        user = (User) i.getSerializableExtra("user");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
//        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();// Prints the scan format (qrcode, pdf417 etc.)
        Log.d("AttendingFragment", rawResult.getText());

        String url = "http://localhost/lpro/api/qrcode-sign.php?key=iot1235&token="+user.getToken()+"&qrcode="+rawResult.getText();
        use_ion(getActivity(),url);
        // If you would like to resume scanning, call this method below:
        scannerView.resumeCameraPreview(this);
    }
    public void setUser(User user){
        this.user = user;
    }
    private void use_ion(Context ctx, String url) {
        Ion.with(ctx).load(url).
                asString()
                .withResponse()
                .setCallback((e, result) -> {
                    if (result == null) {
                        Toast.makeText(ctx, "RIEN\n"+url, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ctx, "Présence", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}