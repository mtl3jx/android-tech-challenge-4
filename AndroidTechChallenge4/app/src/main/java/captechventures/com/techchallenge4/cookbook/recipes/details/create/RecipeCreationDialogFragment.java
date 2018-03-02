package captechventures.com.techchallenge4.cookbook.recipes.details.create;

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

import java.util.ArrayList;
import java.util.List;

import captechventures.com.techchallenge4.R;
import captechventures.com.techchallenge4.database.CookbookDatabase;
import captechventures.com.techchallenge4.model.Category;
import captechventures.com.techchallenge4.model.Recipe;

/**
 * Created by mluansing on 11/6/17.
 */

public class RecipeCreationDialogFragment extends DialogFragment {

    // layout vars
    EditText name, cooktime, ingredients, instructions;
    Spinner categorySpinner;
    Button button_submit;
    Button button_cancel;

    // listener
    RecipeCreationListener listener;

    // tag for logging purposes
    private static final String TAG = RecipeCreationDialogFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getActivity();
        if (context instanceof RecipeCreationListener) {
            listener = (RecipeCreationListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_creation, container,
                false);

        // change toolbar title
        getDialog().setTitle("New Recipe");

        // init layout vars
        name = rootView.findViewById(R.id.new_recipe_name);
        categorySpinner = rootView.findViewById(R.id.new_recipe_category);
        cooktime = rootView.findViewById(R.id.new_recipe_minutes);
        ingredients = rootView.findViewById(R.id.new_recipe_ingredients);
        instructions = rootView.findViewById(R.id.new_recipe_instructions);
        button_cancel = rootView.findViewById(R.id.recipe_creation_cancel);
        button_submit = rootView.findViewById(R.id.recipe_creation_submit);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // populate spinner options
        List<Category> categoryList = CookbookDatabase.getInstance(getActivity()).categoryDao().getAll();
        List<String> spinnerCategories = new ArrayList<>();
        for (Category item : categoryList) {
            spinnerCategories.add(item.getCategory());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerCategories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        // init to no selection
        categorySpinner.setSelection(-1);

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
                    Recipe recipe = new Recipe();
                    recipe.setName(newName);
                    recipe.setMinutes(Integer.parseInt(newCooktime));
                    recipe.setIngredients(newIngredients);
                    recipe.setInstructions(newInstructions);

                    // grab Category ID from spinner
                    Integer categoryId = -1;
                    for (Category item : categoryList) {
                        if (newCategory.equals(item.getCategory())) categoryId = item.getId();
                    }
                    if (categoryId < 0) Log.e(TAG, "Error getting category ID from spinner");
                    recipe.setCategory(categoryId);

                    Log.d(TAG, "Adding to DB: " + recipe);

                    // insert recipe into DB
                    long recipeId = CookbookDatabase.getInstance(getActivity()).recipeDao().add(recipe);

                    // once created in DB show details page
                    listener.openRecipeDetails((int) recipeId);

                    // dismiss recipe creation dialog
                    dismiss();

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

    public interface RecipeCreationListener {
        // methods that this fragment may want to call from CookbookActivity
        void openRecipeDetails(int recipeId);
    }
}
