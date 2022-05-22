package com.foth.petinder.Fragments.MainPet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.foth.petinder.Classes.ExampleObjectsProvider;
import com.foth.petinder.Classes.Pet.Pet;
import com.foth.petinder.Classes.Pet.PetListProviderForAdoption;
import com.foth.petinder.Classes.Pet.PetOptions;
import com.foth.petinder.Fragments.Pet.PetAdaptionFragment;
import com.foth.petinder.Fragments.Pet.PetEmptyMessageFragment;
import com.foth.petinder.R;
import com.foth.petinder.databinding.FragmentMainpetAdoptionBinding;

import java.util.ArrayList;

public class MainPetAdoptionFragment extends Fragment {

    private FragmentMainpetAdoptionBinding binding;
    private ArrayList<String> options;
    private ArrayAdapter<String> adapter;
    private int petListIndex = 0;
    private ArrayList<Pet> currentPetList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentMainpetAdoptionBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        arrangeSpinner();
        ExampleObjectsProvider.provideRandomUserAndPetObjectsForAdoption();


        binding.spinnerPetOptionsForadoption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentPetList = PetListProviderForAdoption.getTheChosenAdoptionPetList(binding.spinnerPetOptionsForadoption.getSelectedItem().toString());
                new PetOptions().setIcons(binding.optionImageAdopting,binding.spinnerPetOptionsForadoption.getSelectedItem().toString());
                if(currentPetList.size()==0){
                    loadFragmentForEmptyMessage();
                }
                else {
                    petListIndex = 0;
                    loadPage(currentPetList.get(petListIndex));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentPetList = PetListProviderForAdoption.getTheChosenAdoptionPetList("Köpek");
                petListIndex = 0;
                loadPage(currentPetList.get(petListIndex));
            }
        });


        binding.buttonseeAllProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new PetAdaptionFragment());
            }
        });

        binding.imageNextPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(petListIndex<currentPetList.size()){
                    loadPage(currentPetList.get(petListIndex++));
                }
            }
        });

        binding.imagePrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(petListIndex>0){
                    loadPage(currentPetList.get(--petListIndex));
                }
            }
        });

        binding.imageReportUserAdoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


        return view;
    }

    public void arrangeSpinner(){
        options = new PetOptions().getOptions();
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,options);
        binding.spinnerPetOptionsForadoption.setAdapter(adapter);

    }
    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentCVf, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    public boolean loadFragmentForEmptyMessage() {
        Fragment fragment = new PetEmptyMessageFragment();

        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentCVf, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void loadPage(Pet pet){
        binding.uuserName.setText(pet.getUser().getName());
        binding.petPhoto.setImageResource(getResources().getIdentifier(pet.getPhoto(),"drawable",getContext().getPackageName()));
        binding.petName.setText(pet.getName());
    }

}