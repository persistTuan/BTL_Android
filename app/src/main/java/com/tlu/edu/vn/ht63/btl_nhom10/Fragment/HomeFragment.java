package com.tlu.edu.vn.ht63.btl_nhom10.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Activity.CartActivity;
import com.tlu.edu.vn.ht63.btl_nhom10.Adapter.ProductAdapter;
import com.tlu.edu.vn.ht63.btl_nhom10.HomeViewModel;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Product;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private List<SlideModel> listImage;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ProductAdapter adapter;
    private ProductAdapter adapterTop5Product;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initBindinhAndViewModel();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        observeHomeViewModel();

        GridLayoutManager layoutForProducts = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager layoutForTop5Product = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.top5Seller.setLayoutManager(layoutForTop5Product);
        binding.products.setLayoutManager(layoutForProducts);

        adapter = new ProductAdapter(getContext(), homeViewModel.productList.getValue(), homeViewModel);
        adapterTop5Product = new ProductAdapter(getContext(), homeViewModel.top5Product.getValue(), homeViewModel);
        binding.top5Seller.setAdapter(adapterTop5Product);
        binding.products.setAdapter(adapter);

        homeViewModel.loadProduct();

        binding.iconCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        });



        initImage();
        binding.imageSlider.setImageList(listImage, ScaleTypes.CENTER_CROP);

        return binding.getRoot();
    }

    void initImage(){
        listImage = new ArrayList<>();
        listImage.add(new SlideModel(R.drawable.bg2, ScaleTypes.CENTER_CROP));
        listImage.add(new SlideModel(R.drawable.bg3, ScaleTypes.CENTER_CROP));
        listImage.add(new SlideModel(R.drawable.bg2, ScaleTypes.CENTER_CROP));
    }

    private void observeHomeViewModel(){
        homeViewModel.top5Product.observe(requireActivity(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapterTop5Product.setProductList(products);
//                adapter.notifyDataSetChanged();
            }
        });
        homeViewModel.productList.observe(requireActivity(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProductList(products);
//                adapter.notifyDataSetChanged();
            }
        });
//
        homeViewModel.existCart.observe(requireActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            Toast.makeText(getContext(), "Đã có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void initBindinhAndViewModel(){
        homeViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel(getContext());
            }
        }).get(HomeViewModel.class);
    }
}