package com.example.a124lttd01_nhom3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_hoctap_nhom3h.FakeData.User;
import com.example.app_hoctap_nhom3h.Retrofit.RetrofitClient;
import com.example.app_hoctap_nhom3h.Retrofit.UploadCallback;
import com.example.app_hoctap_nhom3h.User.User_Gson;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;

public class ProfileFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_CODE_PICK_IMAGE = 1000;
    LinearLayout page_Thongtin;
    LinearLayout clickClose;
    private ShapeableImageView imgAvtUser;
    private TextView tvUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        page_Thongtin = view.findViewById(R.id.page_Thongtin);
        imgAvtUser = view.findViewById(R.id.imgAvtUser);
        tvUsername = view.findViewById(R.id.tvUsername);
        clickClose = view.findViewById(R.id.clickClose);

        tvUsername.setText(new User_Gson(getContext()).getUser().getUsername());
        Log.d("user", new User_Gson(getContext()).getUser().toString());
        // Thiết lập sự kiện cho LinearLayout để chuyển đến trang thông tin cá nhân
        page_Thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        clickClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentClose = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentClose);
            }
        });

        // Kiểm tra quyền truy cập bộ nhớ tùy vào phiên bản Android
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // Đối với Android 13 trở lên
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
            }
        } else {
            // Đối với Android dưới 13
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }

        // Thiết lập sự kiện cho ảnh đại diện
        imgAvtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
                    } else {
                        chooseAvatarImage();
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    } else {
                        chooseAvatarImage();
                    }
                }
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        User_Gson userGson = new User_Gson(getContext());
        User user = userGson.getUser(); // Lấy đối tượng người dùng

        if (user != null) {
            tvUsername.setText(user.getUsername());
            String avatarUrl = user.getAvatar_img();
            Log.d("ProfileFragment", "Avatar URI: " + avatarUrl); // Kiểm tra URI

            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                Uri avatarUri = Uri.parse(avatarUrl);
                Glide.with(this)
                        .load(avatarUri) // Sử dụng Uri.parse() để nạp ảnh từ URI
                        .placeholder(R.drawable.avt_profile_hh) // Ảnh mặc định
                        .error(R.drawable.ic_person_outline_hh) // Ảnh khi tải không thành công
                        .into(imgAvtUser);
            } else {
                // Nếu không có ảnh đại diện, sử dụng ảnh mặc định
                Glide.with(this).load(R.drawable.avt_profile_hh).into(imgAvtUser);
            }
        } else {
            // Nếu json là null, cũng hiển thị ảnh mặc định
            Glide.with(this).load(R.drawable.avt_profile_hh).into(imgAvtUser);
        }
    }


    // Hàm mở thư viện ảnh
    private void chooseAvatarImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // Sử dụng ACTION_OPEN_DOCUMENT
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE); // Đảm bảo rằng chỉ các file có thể mở được chọn
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false); // Không cho phép chọn nhiều file
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseAvatarImage();
            } else {
                Toast.makeText(getActivity(), "Bạn cần cấp quyền để chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Lưu quyền truy cập lâu dài vào URI
                final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                getActivity().getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);

                // Hiển thị ảnh trên ShapeableImageView bằng Glide
                Glide.with(this).load(selectedImageUri).into(imgAvtUser);

                // Cập nhật ảnh đại diện cho người dùng
                User_Gson userGson = new User_Gson(getContext());
                User user = userGson.getUser(); // Lấy đối tượng người dùng
                if (user != null) {
                    user.setAvatar_img(selectedImageUri.toString()); // Cập nhật ảnh đại diện
                    userGson.saveUser(user); // Lưu thông tin người dùng đã cập nhật

                    // Lưu URI của ảnh vào SharedPreferences
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("avatar_img", selectedImageUri.toString());
                    editor.apply();
                } else {
                    Toast.makeText(getActivity(), "Lỗi: Không thể lấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Không thể chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
        }
    }

}