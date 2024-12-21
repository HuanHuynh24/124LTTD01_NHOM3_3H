package com.example.a124lttd01_nhom3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.a124lttd01_nhom3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_hh) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.history_hh) {
                replaceFragment(new HistoryFragment());
            }else if (item.getItemId() == R.id.introduce_hh) {
                replaceFragment(new IntroduceFragment());
            } else if (item.getItemId() == R.id.group_hh) {
                replaceFragment(new GroupFragment());
            } else if (item.getItemId() == R.id.profile_hh) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_hh, fragment);
        fragmentTransaction.commit();
    }
}