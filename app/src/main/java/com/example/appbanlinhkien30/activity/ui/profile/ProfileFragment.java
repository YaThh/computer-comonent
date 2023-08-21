package com.example.appbanlinhkien30.activity.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.appbanlinhkien30.R;
import com.example.appbanlinhkien30.databinding.FragmentProfileBinding;
import com.example.appbanlinhkien30.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ProfileUpdateListener profileUpdateListener;
    ActivityResultLauncher<String> imgPickerLauncher;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        db.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user.getProfileImg() != null) {
                            Glide.with(getContext()).load(user.getProfileImg()).into(binding.imgProfile);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        imgPickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    Uri profileUri = result;
                    binding.imgProfile.setImageURI(profileUri);

                    final StorageReference storageRef = storage.getReference().child("profile_picture")
                        .child(FirebaseAuth.getInstance().getUid());
                    storageRef.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Đăng tải thành công", Toast.LENGTH_SHORT);

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                db.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                                        .child("profileImg").setValue(uri.toString());

                                profileUpdateListener.onProfieImageUpdated(uri.toString());
                            }
                        });
                    }
                    });
                }
            }
        });
        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPickerLauncher.launch("image/*");
            }
        });
        binding.btnProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });
        return root;
    }

    private void updateUserProfile() {
        String uid = FirebaseAuth.getInstance().getUid();

        String newName = binding.edtProfileName.getText().toString();
        String newPhone = binding.edtProfilePhone.getText().toString();
        String newAddress = binding.edtProfileAddress.getText().toString();

        DatabaseReference userRef = db.getReference().child("User").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                Map<String, Object> profileUpdate = new HashMap<>();

                if (!newName.isEmpty()) {
                    profileUpdate.put("userName", newName);
                } else {
                    profileUpdate.put("username", user.getUserName());
                }

                if (!newPhone.isEmpty()) {
                    profileUpdate.put("phone", newPhone);
                } else {
                    profileUpdate.put("phone", user.getPhone());
                }

                if (!newAddress.isEmpty()) {
                    profileUpdate.put("address", newAddress);
                } else {
                    profileUpdate.put("address", user.getAddress());
                }


                userRef.updateChildren(profileUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        if (profileUpdateListener != null) {
                            profileUpdateListener.onProfileNameUpdated(newName);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                        }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProfileUpdateListener) {
            profileUpdateListener = (ProfileUpdateListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement ProfileUpdate Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        profileUpdateListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}