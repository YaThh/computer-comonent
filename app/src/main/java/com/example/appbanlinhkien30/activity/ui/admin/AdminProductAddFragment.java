package com.example.appbanlinhkien30.activity.ui.admin;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.databinding.FragmentAdminProductAddBinding;
import com.example.appbanlinhkien30.databinding.FragmentAdminProductBinding;
import com.example.appbanlinhkien30.model.Product;
import com.example.appbanlinhkien30.util.Convert;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.units.qual.A;

import java.util.UUID;


public class AdminProductAddFragment extends Fragment {

    FragmentAdminProductAddBinding binding;
    ActivityResultLauncher<String> imgPickerLauncher;
    FirebaseStorage storage;
    FirebaseFirestore db;


    public AdminProductAddFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminProductAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        String imgUrl[] = {null};

        imgPickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    Uri productUri = result;
                    binding.imgAdminProductAdd.setImageURI(productUri);

                    final StorageReference storageRef = storage.getReference().child("product_picture")
                            .child(UUID.randomUUID().toString());

                    storageRef.putFile(productUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgUrl[0] = uri.toString();
                                }
                            });
                        }
                    });
                }
            }
        });

        binding.imgAdminProductAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPickerLauncher.launch("image/*");
            }
        });

        binding.btnAdminProductUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.edtAdminProductName.getText().toString();
                String priceText = binding.edtAdminProductPrice.getText().toString();
                Double price = priceText.isEmpty() ? 0 : Double.parseDouble(priceText);
                String description = binding.edtAdminProductDescription.getText().toString();
                String type = binding.edtAdminProductType.getText().toString();

                if (!name.isEmpty() && !description.isEmpty() && !type.isEmpty() && !String.valueOf(price).isEmpty()) {
                    Product product = new Product();
                    product.setName(name);
                    product.setPrice(Convert.convertNumberToCurrencyString(price));
                    product.setDescription(description);
                    product.setType(type);
                    product.setImgUrl(imgUrl[0]);
                    product.setRating("0");

                    db.collection("Product").add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(requireContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            binding.edtAdminProductName.setText("");
                            binding.edtAdminProductPrice.setText("");
                            binding.edtAdminProductType.setText("");
                            binding.edtAdminProductDescription.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), "Thêm sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(requireContext(), "Vui lòng nhập đủ các field trống", Toast.LENGTH_SHORT).show();
                }

            }
        });


        binding.btnAdminProductAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return root;
    }
}