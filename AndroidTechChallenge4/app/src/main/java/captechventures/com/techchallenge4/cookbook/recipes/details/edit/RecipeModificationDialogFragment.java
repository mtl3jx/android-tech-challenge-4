package captechventures.com.techchallenge4.cookbook.recipes.details.edit;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import captechventures.com.techchallenge4.R;
import captechventures.com.techchallenge4.database.CookbookDatabase;
import captechventures.com.techchallenge4.model.Category;
import captechventures.com.techchallenge4.model.Recipe;

/**
 * Created by mluansing on 11/7/17.
 */

public class RecipeModificationDialogFragment extends DialogFragment {

    Recipe recipe;

    private final Integer OFFSET_CATEGORY = 1;              // offset for category indices

    // layout vars
    EditText name, cooktime, ingredients, instructions;
    Spinner categorySpinner;
    Button button_submit, button_cancel;

    // listener
    RecipeModificationListener listener;

    private static final String KEY_ARG_RECIPE_MODIFY = "KEY_ARG_RECIPE_MODIFY";

    // tag for logging purposes
    private static final String TAG = RecipeModificationDialogFragment.class.getSimpleName();

    public RecipeModificationDialogFragment() {
        // required empty public constructor
    }

    public static RecipeModificationDialogFragment newInstance(Recipe recipe) {
        RecipeModificationDialogFragment fragment = new RecipeModificationDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG_RECIPE_MODIFY, Parcels.wrap(recipe));
//        Log.d(TAG, "Putting " + recipe + " in global var recipe");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up listener
        Context context = getActivity();
        if (context instanceof RecipeModificationListener) {
            listener = (RecipeModificationListener) context;
        }

        // grab Recipe to edit from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = Parcels.unwrap(bundle.getParcelable(KEY_ARG_RECIPE_MODIFY));
//            Log.d(TAG, "Setting recipe to: " + recipe);
        } else {
            recipe = null;
//            Log.e(TAG, "no recipe found, setting to null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_modification, container,
                false);

        // init layout vars
        name = rootView.findViewById(R.id.edit_recipe_name);
        categorySpinner = rootView.findViewById(R.id.edit_recipe_category);
        cooktime = rootView.findViewById(R.id.edit_recipe_minutes);
        ingredients = rootView.findViewById(R.id.edit_recipe_ingredients);
        instructions = rootView.findViewById(R.id.edit_recipe_instructions);
        button_cancel = rootView.findViewById(R.id.recipe_modification_cancel);
        button_submit = rootView.findViewById(R.id.recipe_modification_submit);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (recipe == null) Log.e(TAG, "No recipe found");

        // populate up all fields based on Recipe
        name.setText(recipe.getName());
        cooktime.setText(recipe.getMinutes().toString());
        ingredients.setText(recipe.getIngredients());
        instructions.setText(recipe.getInstructions());

        // get list of Categories from DB
        List<Category> categoryList = CookbookDatabase.getInstance(getActivity()).categoryDao().getAll();

        // list of category names for Spinner
        List<String> spinnerCategories = new ArrayList<>();

        // init spinner to no selection
        Integer categoryId = -1;

        for (Category item : categoryList) {
            // populate category dropdown menu
            spinnerCategories.add(item.getCategory());

            // grab index of existing category
            if (recipe.getCategory() == item.getId()) categoryId = item.getId();
        }

        // error handling -- if category has ID of 0?
        if (categoryId < 0) Log.e(TAG, "Error getting category ID from spinner");

        // render drop down selection
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerCategories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        // set spinner to recipe's current category
        categorySpinner.setSelection(categoryId - OFFSET_CATEGORY);

        // cancel button with onClickListener
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // submit button with onClickListener
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = name.getText().toString().trim();
                String newCategory = categorySpinner.getSelectedItem().toString().trim();
                String newCooktime = cooktime.getText().toString().trim();
                String newIngredients = ingredients.getText().toString().trim();
                String newInstructions = instructions.getText().toString().trim();

                // null check
                if (TextUtils.isEmpty(newName) ||
                        TextUtils.isEmpty(newCategory) ||
                        TextUtils.isEmpty(newCooktime) ||
                        TextUtils.isEmpty(newIngredients) ||
                        TextUtils.isEmpty(newInstructions)) {
                    // one of the fields is not filled out
                    Toast.makeText(getActivity(), "You must fill out all the fields", Toast.LENGTH_SHORT).show();

                } else {
                    // grab values of EditTexts
                    Recipe recipeNew = new Recipe();
                    recipeNew.setId(recipe.getId());
                    recipeNew.setName(newName);
                    recipeNew.setMinutes(Integer.parseInt(newCooktime));
                    recipeNew.setIngredients(newIngredients);
                    recipeNew.setInstructions(newInstructions);

                    // grab Category ID from spinner
                    Integer categoryId = -1;
                    for (Category item : categoryList) {
                        if (newCategory.equals(item.getCategory())) categoryId = item.getId();
                    }
                    if (categoryId < 0) Log.e(TAG, "Error getting category ID from spinner");
                    recipeNew.setCategory(categoryId);

                    Log.d(TAG, "Updating recipe in DB: " + recipeNew);

                    // insert recipe into DB
                    int updatedRows = CookbookDatabase.getInstance(getActivity()).recipeDao().edit(recipeNew);
                    if (updatedRows < 0) Log.e(TAG, "Error updating Recipe in DB");

                    // once created in DB show details page
                    listener.openRecipeDetails(recipeNew.getId());

                    // dismiss recipe creation dialog
                    dismiss();

                    // go back so user does not have to hit back to refresh page
                    listener.back();
                }

            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setLayout(width, height);
    }

    public interface RecipeModificationListener {
        // methods that this fragment may want to call from CookbookActivity
        void openRecipeDetails(int recipeId);
        void back();
    }
}
